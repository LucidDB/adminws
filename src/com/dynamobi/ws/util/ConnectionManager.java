/*
Dynamo Web Services is a web service project for administering LucidDB
Copyright (C) 2010 Dynamo Business Intelligence Corporation

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the Free
Software Foundation; either version 2 of the License, or (at your option)
any later version approved by Dynamo Business Intelligence Corporation.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.dynamobi.ws.util;

import java.lang.ClassNotFoundException;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

// Base class
import org.springframework.security.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.Authentication;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionManager extends JdbcDaoImpl {

  private static BasicDataSource data_source = null;

  // conns stores per-session Connections,
  // stored by UUID, then username and the connection.
  private static Map<String, ConnectionInfo> conns;

  private static String jdbc_driver;
  private static String jdbc_url;

  private ConnectionGC GC;

  /**
   * Constructor sets up the dataSource manager for the connections which
   * validate other connections and starts the garbage collector for
   * user connections.
   */
  public ConnectionManager() throws ClassNotFoundException, SQLException, IOException {
    super();

    Properties pro = new Properties();

    InputStream user_props = this.getClass().getResourceAsStream("/luciddb-jdbc.properties");
    if (user_props != null) {
      pro.load(user_props);
    } else {
      pro.load(this.getClass().getResourceAsStream("/luciddb-jdbc-default.properties"));
    }

    jdbc_driver = pro.getProperty("jdbc.driver");
    Class.forName(jdbc_driver);

    String username = pro.getProperty("jdbc.username");
    String password = pro.getProperty("jdbc.password");
    jdbc_url        = pro.getProperty("jdbc.url");

    // this sets up the connection to validate other connections.
    data_source = new BasicDataSource();
    data_source.setDriverClassName(jdbc_driver);
    data_source.setUrl(jdbc_url);
    data_source.setUsername(username);
    data_source.setPassword(password);
    setDataSource(data_source);

    conns = new HashMap<String, ConnectionInfo>();

    GC = new ConnectionGC(60*5, 60*60);
    // check to close stuff every 5 mins, invalidation time is 1 hr
  }

  /**
   * Called when the server shuts down,
   * cleans up the connection resources.
   */
  public void cleanup() throws SQLException {
    data_source.close();

    GC_close_all();
  }

  protected static synchronized void GC_close_old(long invalid) throws SQLException {
    for (Map.Entry<String, ConnectionInfo> conn : conns.entrySet()) {
      if (!conn.getValue().closed && !conn.getValue().busy &&
          conn.getValue().older_than(invalid)) {
        conn.getValue().connection.close();
        conn.getValue().closed = true;
      }
    }
  }

  protected static synchronized void GC_close_all() throws SQLException {
    for (Map.Entry<String, ConnectionInfo> conn : conns.entrySet()) {
      if (!conn.getValue().closed) {
        conn.getValue().connection.close();
        conn.getValue().closed = true;
      }
    }
    conns = new HashMap<String, ConnectionInfo>();
  }

  /**
   * Used by the caller to get their Connection resource.
   * This will block if the connection is not available yet.
   * @param auth - Spring authentication for the caller.
   * @return Either a new SQL Connection or one previously created.
   */
  public static Connection request_connection(Authentication auth)
      throws SQLException, ClassNotFoundException, InterruptedException {
    String uname = auth.getName();
    String pw = null;
    String[] parts = auth.getCredentials().toString().split(":", 4);
    boolean first;
    if (parts.length == 4) { // a first call, possibly
      first = true;
      pw = parts[3];
    } else if (parts.length == 3) { // subsequent calls
      first = false;
    } else { // wtf? (They shouldn't have gotten this far, something is wrong.)
      throw new SQLException("SEVERE: Server received unexpected invalid " +
          "authentication token.");
    }

    String uuid = parts[2];
    if (conns.containsKey(uuid) && !first) {
      ConnectionInfo info = conns.get(uuid);
      if (info.uname.equals(uname)) {
        while (info.get_busy()) {
          Thread.sleep(1000);
        }
        if (info.closed) { // they may have timed out and come back, recreate c
          info.set_connection(stupid_connection(info.uname, info.pw));
        }
        info.update_time();

        info.busy = true;
        return info.connection;
      } else {
        throw new SQLException("SEVERE: UUID collision among different users.");
      }
    } else if (conns.containsKey(uuid) && first) {
      // enforce them not to send the password twice.
      throw new SQLException("Please do not continue to send your password "+
          "raw after authenticating.");
    } else if (first) { // make new connection for them.
      Connection c = stupid_connection(uname, pw);
      ConnectionInfo info = new ConnectionInfo();
      conns.put(uuid, info);

      info.uname = uname;
      info.pw = pw;
      info.set_connection(c);
      info.busy = true;
      return c;
    } else { // !first, no known, we need a raw pass from them to make a conn.
      throw new SQLException("Your raw password is required for the initial " +
          "request to form a connection.");
    }
  }

  /**
   * Actually creates the SQL Connection using the driver manager class.
   */
  private static Connection stupid_connection(String uname, String pw)
      throws SQLException, ClassNotFoundException {
    Class.forName(jdbc_driver);
    Connection c = DriverManager.getConnection(jdbc_url, uname, pw);
    return c;
  }

  /**
   * Used by the caller to signal that they are done with the connection
   * and allow other tasks to use it.
   * @param auth - the caller's Spring authentication.
   * @param c - the connection to release
   */
  public static void release_connection(Authentication auth, Connection c)
      throws SQLException {
    String[] parts = auth.getCredentials().toString().split(":", 4);
    if (parts.length < 3) {
      throw new SQLException("SEVERE: Authentication changed.");
    }
    String uuid = parts[2];
    if (!conns.containsKey(uuid)) {
      throw new SQLException("SEVERE: Missing ConnectionInfo object for UUID " + uuid);
    }

    ConnectionInfo info = conns.get(uuid);
    info.busy = false;
    info.update_time();
  }

  protected static class ConnectionInfo {
    public String uname;
    public String pw;
    public Connection connection;
    public long last_used;
    public boolean closed;
    public boolean busy;

    public ConnectionInfo() {
      uname = "";
      pw = "";
      connection = null;
      update_time();
      closed = true;
      busy = false;
    }

    public void set_connection(Connection c) {
      connection = c;
      closed = false;
      busy = true;
    }

    public void update_time() {
      last_used = System.currentTimeMillis() / 1000L;
    }

    public boolean older_than(long seconds) {
      return System.currentTimeMillis() / 1000L - last_used > seconds;
    }

    public boolean older_than_mins(long minutes) {
      return older_than(60*minutes);
    }

    public boolean get_busy() { return busy; }

  }

  protected class ConnectionGC extends Thread {
    private long sex_to_check; // how many seconds to wait before checking conns
    private long sex_invalidated; // amount of seconds needed to mark a conn
    // for closing.

    public ConnectionGC(long sex_to_check, long sex_invalidated) {
      setDaemon(true);
      this.sex_to_check = sex_to_check;
      this.sex_invalidated = sex_invalidated;
      start();
    }

    public void run() {
      while(true) {
        try {
          // daemon fell asleep!
          sleep(sex_to_check);
          // daemon woke up!
          GC_close_old(sex_invalidated);
        } catch (InterruptedException e) {
          throw new RuntimeException("SEVERE: Garbage collector broke.");
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
