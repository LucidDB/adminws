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

import com.dynamobi.ws.domain.DBLoader;
import com.dynamobi.ws.util.DB;
import java.sql.*;

public class SubQuery extends DBLoader<SubQuery> {
  private String sub_query;
  private int count;

  public SubQuery() {
    sub_query = "";
    count = 0;
  }

  public SubQuery(String sub, int results) {
    sub_query = sub;
    count = results;
  }

  public void loadRow(ResultSet rs) throws SQLException {
    Object[] args = new String[count];
    int c = 1;
    while (c <= count)
      args[c-1] = rs.getString(c++);

    String query = DB.populate(sub_query, args);
    String ret = DB.execute_success(query);
    if (!ret.equals("")) {
      throw new SQLException(ret);
    }
  }

  public void finalize() { }
  public SubQuery copy() { return this; }
}

