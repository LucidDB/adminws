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
package com.dynamobi.ws.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.sql.*;
import com.dynamobi.ws.domain.DBLoader;

/**
 * Holds SessionInfo
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="sessioninfo")
public class SessionInfo extends DBLoader<SessionInfo> {

  public int id;
  public String connect_url;
  public String user;
  public String query;

  private SessionInfo copy;

  public SessionInfo() { }

  public String toString() {
    return
     "\nid: " + id +
     "\nconnect_url: " + connect_url +
     "\nuser: " + user +
     "\nquery: " + query;
  }

  public void loadRow(ResultSet rs) throws SQLException {
    int c = 1;
    SessionInfo si = new SessionInfo();
    si.id = rs.getInt(c++);
    si.connect_url = rs.getString(c++);
    si.user = rs.getString(c++);
    si.query = rs.getString(c++);
    copy = si;
  }

  public void finalize() { }

  public SessionInfo copy() { return copy; }

  // Auto-generated for AMF
  @XmlAttribute
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  @XmlAttribute
  public String getConnect_url() { return connect_url; }
  public void setConnect_url(String connect_url) { this.connect_url = connect_url; }

  @XmlAttribute
  public String getUser() { return user; }
  public void setUser(String user) { this.user = user; }

  @XmlAttribute
  public String getQuery() { return query; }
  public void setQuery(String query) { this.query = query; }

}

