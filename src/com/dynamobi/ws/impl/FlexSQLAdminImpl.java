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
import com.dynamobi.ws.util.DB;
import com.dynamobi.ws.domain.*;

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

    private XMLStructure getAuthIDs(String class_name, boolean json) {
      String lower = class_name.toLowerCase();
      XMLStructure ds;
      if (!json)
        ds = new XMLStructure(lower + "s", lower + " label");
      else
        ds = new XMLStructure(lower + "s", XMLStructure.Mode.JSON);
      final String query = DB.select("name", "sys_root.dba_auth_ids",
          DB.populate("class_name = {0,lit} AND name <> '_SYSTEM' AND " +
            "name <> 'PUBLIC' ORDER BY name", class_name));
      DB.execute(query, ds);
      return ds;
    }

    public XMLStructure getUsers() {
        return getAuthIDs("User", false);
    }

    public XMLStructure getUsersJson() {
        return getAuthIDs("User", true);
    }

    public XMLStructure getRoles() {
      return getAuthIDs("Role", false);
    }

    public XMLStructure getRolesJson() {
      return getAuthIDs("Role", true);
    }

    private XMLStructure getRoutine(String schema, String type, boolean json) {
      String tag = type.toLowerCase();
      XMLStructure ds;
      if (!json) {
        ds = new XMLStructure(tag + "s",
            tag + " label externalName isTableFunction isDeterministic");
      } else {
        ds = new XMLStructure(tag + "s",
            tag + " label externalName isTableFunction isDeterministic",
            XMLStructure.Mode.JSON);
      }
      final String query = DB.select("DISTINCT invocation_name, external_name, "
          + "is_table_function, is_deterministic ", "sys_root.dba_routines",
          DB.populate("schema_name = {0,lit} AND routine_type = {1,lit}",
            schema, type));
      DB.execute(query, ds);
      return ds;
    }

    public XMLStructure getFunctions(String schema) {
      return getRoutine(schema, "FUNCTION", false);
    }

    public XMLStructure getFunctionsJson(String schema) {
      return getRoutine(schema, "FUNCTION", true);
    }

    public XMLStructure getProcedures(String schema) {
      return getRoutine(schema, "PROCEDURE", false);
    }

    public XMLStructure getProceduresJson(String schema) {
      return getRoutine(schema, "PROCEDURE", true);
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

    private XMLStructure getJarsGen(String schema, boolean json) {
      XMLStructure ds;
      if (!json)
        ds = new XMLStructure("jars", "jar label");
      else
        ds = new XMLStructure("jars", "jar label", XMLStructure.Mode.JSON);
      final String query = DB.select("\"name\"",
          DB.populate("sys_fem.{0,id}.{1,id} j INNER JOIN " +
            "sys_boot.jdbc_metadata.schemas_view_internal svi " +
            "ON svi.{2,id} = j.{3,id}",
          "SQL2003", "Jar", "mofId", "namespace"),
          DB.populate("object_schema = {0,lit}", schema));
      DB.execute(query, ds);
      return ds;
    }
    public XMLStructure getJars(String schema) {
      return getJarsGen(schema, false);
    }
    public XMLStructure getJarsJson(String schema) {
      return getJarsGen(schema, true);
    }

    private XMLStructure getSchemaDdlGen(String catalog, String schema, boolean json) {
      XMLStructure ds;
      if (!json)
        ds = new XMLStructure("statement");
      else
        ds = new XMLStructure("statement", XMLStructure.Mode.JSON);
      final String query = DB.select("statement", DB.populate(
            "table(sys_root.generate_ddl_for_schema({0,lit}, {1,lit}))",
            catalog, schema));
      DB.execute(query, ds);
      return ds;
    }
    public XMLStructure getSchemaDdl(String catalog, String schema) {
      return getSchemaDdlGen(catalog, schema, false);
    }
    public XMLStructure getSchemaDdlJson(String catalog, String schema) {
      return getSchemaDdlGen(catalog, schema, true);
    }
}
