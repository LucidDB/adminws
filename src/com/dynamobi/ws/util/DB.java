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

import java.sql.*;
import java.util.*;

import java.io.FileNotFoundException;

import com.dynamobi.ws.domain.*;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

public class DB {

  private DB() { }

  public static String connection_catalog = "";

  public static Connection getConnection()
    throws ClassNotFoundException, SQLException, FileNotFoundException
  {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Connection conn;

    if (auth != null) {
      try {
        conn = LoginVerify.request_connection(auth);
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new SQLException("Could not authenticate, connection in use.");
      }
    } else {
      throw new SQLException("Could not authenticate.");
    }

    return conn;
  }

  public static void release_connection(Connection c) throws SQLException {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Connection conn;
    LoginVerify.release_connection(auth, c);
  }

  /**
   * @param query
   *        Query represents a basic structure similar in form to
   *        MessageFormat.format's pattern argument. Tokens in query
   *        will be populated by items in data, tokens specified by
   *        {data_index,type}. The caller is responsible for escaping the
   *        opening brace with '/', closing braces do not have to be escaped.
   *        Literals and identifiers will be escaped by doubling up the quote.
   *
   *        Valid types:
   *          literal (will wrap argument in single quotes) AKA lit
   *          identifier (will wrap argument in double quotes) AKA id
   *          string (will simply call toString() and insert it) AKA str
   *          lit_list (expects List of strings and will quote them with lit)
   *          id_list (expects List of strings and will quote them with id)
   *          str_list (expects List of strings and will not quote them)
   *
   *        Example:
   *
   *        DB.populate("SELECT {0,identifier} FROM known WHERE x={1,literal}",
   *                    "x", "/{my_x}");
   *        will return
   *        SELECT "x" FROM known WHERE x='{my_x}'
   *
   *    @param data - Multiple arguments whose length matches the highest
   *                  number in the query pattern.
   */
  @SuppressWarnings(value={"unchecked"})
  public static String populate(String query, Object ...data) {
    List<String> tokens = new ArrayList<String>();
    StringBuffer pop = new StringBuffer();
    int start = 0;
    if (query.charAt(0) != '{') {
      start = query.indexOf("{");
      if (start == -1) return query;
      while (query.charAt(start - 1) == '/') {
        start = query.indexOf("{", start+1);
        if (start == -1) return query;
      }
      pop.append(query.substring(0, start));
    }

    while (start != -1) {
      // next token:
      int start_next = query.indexOf("{", start+1);
      // handle escapes
      while (start != 0 && query.charAt(start - 1) == '/' && start_next != -1) {
        start_next = query.indexOf("{", start_next + 1);
      }
      String token = (start_next != -1) ? query.substring(start+1, start_next)
                                        : query.substring(start+1);
      int rb = token.indexOf("}");
      String[] pattern = token.substring(0, rb).split(",");
      pattern[0] = pattern[0].trim();
      pattern[1] = pattern[1].trim();
      Object obj = data[Integer.parseInt(pattern[0])];
      String value = "";
      if (pattern[1].equals("literal") || pattern[1].equals("lit")) {
        value = surround(obj.toString(), "'");
      } else if (pattern[1].equals("identifier") || pattern[1].equals("id")) {
        value = surround(obj.toString(), "\"");
      } else if (pattern[1].equals("string") || pattern[1].equals("str")) {
        value = obj.toString();
      } else if (pattern[1].equals("lit_list") ||
          pattern[1].equals("id_list") || pattern[1].equals("str_list")) {
        List<String> list = (List<String>)obj;
        boolean is_first = true;
        for (String item : list) {
          if (!is_first) value += ", ";
          else is_first = false;
          if (pattern[1].equals("lit_list"))
            value += surround(item, "'");
          else if (pattern[1].equals("id_list"))
            value += surround(item, "\"");
          else
            value += item;
        }
      }
      value = value.replaceAll("/\\{", "\\{");
      token = token.replaceAll("/\\{", "\\{");
      pop.append(token.replaceAll(token.substring(0, rb+1), value));
      start = start_next;
    }

    return pop.toString();
  }

  public static <T extends DBLoader> void execute(String query, T obj) {
    execute(query, obj, null);
  }

  @SuppressWarnings(value={"unchecked"})
  public static <T extends DBLoader> void execute(
      String query,
      T obj,
      List<T> list) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      if (!connection_catalog.equals("")) {
        ps = conn.prepareStatement("SET CATALOG '" + connection_catalog + "'");
        connection_catalog = "";
      } else {
        ps = conn.prepareStatement("SET CATALOG 'LOCALDB'");
      }
      ps.execute();
      ps = conn.prepareStatement(query);
      ps.setMaxRows(0);
      if (ps.execute()) {
        rs = ps.getResultSet();
      }

      while (rs != null && rs.next()) {
        obj.loadRow(rs);
        if (list != null) {
          list.add((T)obj.copy());
        }
      }
      obj.finalize();
    } catch (SQLException ex) {
      obj.exception(ex);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (ps != null) {
          ps.close();
        }
        if (conn != null) {
          release_connection(conn);
        }
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static String execute_success(String query) {
    SuccessQuery s = new SuccessQuery();
    execute(query, s);
    if (s.error)
      return s.error_msg;
    return "";
  }

  public static String select(String what, String table, String where) {
    String q = "SELECT " + what + " FROM " + table;
    if (!where.equals("")) {
      q += " WHERE " + where;
    }
    return q;
  }

  public static String select(String what, String table) {
    return select(what, table, "");
  }

  public static String surround(String item, String sur) {
    if (sur == "\"" || sur == "'")
      item = item.replaceAll(sur, sur + sur);
    return sur + item + sur;
  }

  // not so much db related but still useful
  public static String join_list(List<String> list, String delim) {
    StringBuilder sb = new StringBuilder();
    boolean f = true;
    for (String s : list) {
      if (!f)
        sb.append(delim);
      else
        f = false;
      sb.append(s);
    }
    return sb.toString();
  }

}
