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
package com.dynamobi.ws.impl;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.ws.api.FlexSQLAdmin;
import com.dynamobi.ws.util.DBAccess;

import java.sql.*;

/**
 * Implementation of the interface FlexSQLAdmin
 * 
 * @author Ray Zhang
 * @since Feb 04,2010
 *
 * @author Kevin Secretan
 * @since June 15, 2010
 */

@Path("/sqlcommands")
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
          "name <> '_SYSTEM' AND name <> 'PUBLIC' ORDER BY name";
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

    public String getForeignTables(String schema) {
      return "";
    }

    private String getRoutine(String schema, String type) {
      StringBuffer result = new StringBuffer("<" + type.toLowerCase() + "s>\n");
      try {
        final String query = "SELECT DISTINCT invocation_name, external_name, "
          + "is_table_function, is_deterministic "
          + "FROM sys_root.dba_routines "
          + "WHERE schema_name = '" + schema + "' AND "
          + "routine_type = '" + type + "'";
        ResultSet rs = DBAccess.rawResultExec(query);
        while (rs.next()) {
          String inv_name = rs.getString(1).replace("\"", "&quot;");
          if (inv_name != null) inv_name = inv_name.replace("\"", "&quot;");
          String ext_name = rs.getString(2);
          if (ext_name != null) ext_name = ext_name.replace("\"", "&quot;");
          final boolean is_tab_fun = rs.getBoolean(3);
          final boolean is_determ = rs.getBoolean(4);
          result.append("  <" + type.toLowerCase() + " label=\"" + inv_name +
             "\" externalName=\"" + ext_name + "\" isTableFunction=\"" + 
            is_tab_fun + "\" isDeterministic=\"" + is_determ + "\" />\n");
        }
        result.append("</" + type.toLowerCase() + "s>");
      } catch (SQLException e) {
        e.printStackTrace();
      }

      return result.toString();
    }

    public String getFunctions(String schema) {
      return getRoutine(schema, "FUNCTION");
    }

    public String getProcedures(String schema) {
      return getRoutine(schema, "PROCEDURE");
    }

    public String getRemoteData() {
      StringBuffer result = new StringBuffer("<wrappers>\n");
      try {
        final String query = "SELECT w.foreign_wrapper_name, w.library, "
          + "s.foreign_server_name FROM sys_root.dba_foreign_wrappers w "
          + "left outer join sys_root.dba_foreign_servers s ON "
          + "s.foreign_wrapper_name = w.foreign_wrapper_name "
          + "ORDER BY w.foreign_wrapper_name";
        ResultSet rs = DBAccess.rawResultExec(query);
        String last_wrapper = "";
        while (rs.next()) {
          String wrapper_name = rs.getString(1);
          String wrapper_lib = rs.getString(2);
          String server_name = rs.getString(3);

          if (! wrapper_name.equals(last_wrapper)) {
            if (! last_wrapper.equals("") )
              result.append("    </servers>\n  </wrapper>\n");
            result.append("  <wrapper label=\"" + wrapper_name + "\" library=\"" + wrapper_lib + "\">\n");
            result.append("    <servers label=\"Servers\">\n");
          }
          
          if (server_name != null)
            result.append("      <server label=\"" + server_name + "\" />\n");

          last_wrapper = wrapper_name;
        }
        result.append("    </servers>\n  </wrapper>\n</wrappers>");
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return result.toString();
    }

    public String getJars(String schema) {
      StringBuffer result = new StringBuffer("<jars>\n");
      try {
        final String query = "SELECT \"name\" FROM " +
          "sys_fem.\"SQL2003\".\"Jar\" j INNER JOIN " +
          "sys_boot.jdbc_metadata.schemas_view_internal svi " +
          "ON svi.\"mofId\" = j.\"namespace\" " +
          "WHERE object_schema = '" + schema + "'";
        ResultSet rs = DBAccess.rawResultExec(query);
        while (rs.next()) {
          String name = rs.getString(1);
          result.append("  <jar label=\"" + name + "\" schema=\"" + schema +
              "\" />\n");
        }
        result.append("</jars>\n");
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return result.toString();

    }

}
