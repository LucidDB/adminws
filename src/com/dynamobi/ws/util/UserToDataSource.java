/*
Dynamo Web Services is a web service project for administering LucidDB
Copyright (C) 2010-2011 Dynamo Business Intelligence Corporation

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

import java.util.Properties;
import java.io.InputStream;
import java.lang.ClassNotFoundException;
import java.io.IOException;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;

public class UserToDataSource extends UserCredentialsDataSourceAdapter {
  
  // Because Mr. Juergen Hoeller wrote a poor parent class.
  private String username;
  private String password;

  public UserToDataSource() throws ClassNotFoundException, IOException {
    super();

    Properties pro = new Properties();

    InputStream user_props = this.getClass().getResourceAsStream("/luciddb-jdbc.properties");
    if (user_props != null) {
      pro.load(user_props);
    } else {
      pro.load(this.getClass().getResourceAsStream("/luciddb-jdbc-default.properties"));
    }

    String jdbc_driver = pro.getProperty("jdbc.driver");
    Class.forName(jdbc_driver);

    String username = pro.getProperty("jdbc.username");
    String password = pro.getProperty("jdbc.password");
    String jdbc_url = pro.getProperty("jdbc.url");

    // this sets up the connection to validate other connections.
    DriverManagerDataSource data_source = new DriverManagerDataSource();
    data_source.setDriverClassName(jdbc_driver);
    data_source.setUrl(jdbc_url);
    data_source.setUsername(username);
    data_source.setPassword(password);
    setTargetDataSource(data_source);
  }

  public void setUsername(String username) {
    super.setUsername(username);
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    super.setPassword(password);
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

} 
