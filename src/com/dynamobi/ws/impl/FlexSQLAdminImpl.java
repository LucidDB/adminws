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
/**
 * 
 */
package com.dynamobi.ws.impl;

import javax.jws.WebService;

import com.dynamobi.ws.api.FlexSQLAdmin;
import com.dynamobi.ws.util.DBAccess;

import java.sql.ResultSet;

/**
 * Implementation of the interface FlexSQLAdmin
 * 
 * @author Ray Zhang
 * @since Feb 04,2010
 *
 * @author Kevin Secretan
 * @since June 15, 2010
 */
@WebService(

endpointInterface = "com.dynamobi.ws.api.FlexSQLAdmin"

)
public class FlexSQLAdminImpl
    implements FlexSQLAdmin
{

    /*
     * (non-Javadoc)
     * 
     * @see com.flexsqladmin.api.FlexSQLAdmin#getDBMetaData()
     */
    public String getDBMetaData(String connection, String catalog)
    {
        String ret = "";
        try {
            ret = DBAccess.getDBMetaData(connection, catalog);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.flexsqladmin.api.FlexSQLAdmin#execSQL(java.lang.String,
     *      java.lang.String, java.lang.String, int)
     */
    public String execSQL(
        String connection,
        String sqlquerytype,
        String sql,
        int toomany)
    {

        String ret = DBAccess.execSQL(connection, sqlquerytype, sql, toomany);
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.flexsqladmin.api.FlexSQLAdmin#handleUpdate(java.lang.String,
     *      java.lang.String, java.lang.String, int)
     */
    public String handleUpdate(
        String connection,
        String testsql,
        String sql,
        int toomany)
    {
        String ret = DBAccess.handleUpdate(connection, testsql, sql, toomany);
        return ret;
    }

    private String getAuthIDs(String class_name) {
      StringBuffer ids = new StringBuffer();
      String lower = class_name.toLowerCase();
      try {
        final String query = "SELECT name FROM sys_root.dba_auth_ids " +
          "WHERE class_name = '" + class_name + "' AND " +
          "name <> '_SYSTEM' ORDER BY name";
        ResultSet rs = DBAccess.rawResultExec(query);
        ids.append("<" + lower + "s label=\"" + class_name + "s\">\n");
        while (rs.next()) {
          ids.append("  <" + lower + " label=\"" +
              rs.getString(1) + "\" />\n");
        }
        ids.append("</" + lower + "s>\n");
      } catch (Exception e) {
        e.printStackTrace();
      }

      return ids.toString();
    }

    public String getUsers() {
        return getAuthIDs("User");
    }

    public String getRoles() {
      return getAuthIDs("Role");
    }
}
