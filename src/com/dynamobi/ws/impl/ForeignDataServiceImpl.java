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

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dynamobi.ws.api.ForeignDataService;
import com.dynamobi.ws.domain.WrapperOptions;
import com.dynamobi.ws.domain.RemoteData;

import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DBAccess;
import com.dynamobi.ws.util.DB;

/**
 * ForeignDataService implementation
 * 
 * @author Kevin Secretan
 * @since Sept 17, 2010
 */
@WebService(endpointInterface = "com.dynamobi.ws.api.ForeignDataService")
@Path("/foreign_data")
public class ForeignDataServiceImpl implements ForeignDataService {

  public List<WrapperOptions> getWrapperOptions(String wrapper)
    throws AppException {
      List<WrapperOptions> options_list = new ArrayList<WrapperOptions>();
      WrapperOptions first = new WrapperOptions();
      final String query = DB.select("distinct option_ordinal, option_name, " +
          "option_description, is_option_required, option_choice_value as option_default_value, " +
          "case  " +
          "  when option_choice_value = 'TRUE' OR option_choice_value = 'FALSE' then 'BOOLEAN' " +
          "  else 'TEXT' " +
          "end as option_type, " +
          "option_choice_ordinal", DB.populate(
            "table(sys_boot.mgmt.browse_connect_foreign_server({0,lit}, cursor(select '' as option_name, '' as option_value " +
            "      from sys_boot.jdbc_metadata.empty_view))) ", wrapper),
          "option_choice_ordinal = -1 ") +
          " order by option_ordinal ";
      DB.execute(query, first, options_list);
      return options_list;
  }

  public List<WrapperOptions> getExtendedWrapperOptions(String wrapper,
      String driver) throws AppException {
    List<WrapperOptions> options_list = new ArrayList<WrapperOptions>();

    try {
      final String query = "select distinct " +
        "option_ordinal,  " +
        "option_name, " +
        "option_description, " +
        "is_option_required, " +
        "option_choice_value as option_default_value, " +
        "case  " +
        "  when option_choice_value = 'TRUE' OR option_choice_value = 'FALSE' then 'BOOLEAN' " +
        "  else 'TEXT' " +
        "end as option_type, " +
        "option_choice_ordinal " +
        "from " +
        "  table(sys_boot.mgmt.browse_connect_foreign_server('" + wrapper + "', " +
        "    cursor(values ('DRIVER_CLASS', '" + driver + "'), ('URL', ''), " +
        "      ('EXTENDED_OPTIONS', 'TRUE')))) " +
        "where option_choice_ordinal = -1 " +
        "order by option_ordinal ";

      ResultSet rs = DBAccess.rawResultExec(query);
      while (rs.next()) {
        int c = 0;
        int ordinal = rs.getInt(++c);
        String name = rs.getString(++c);
        String desc = rs.getString(++c);
        boolean req = rs.getBoolean(++c);
        String default_val = rs.getString(++c);
        String type = rs.getString(++c);

        WrapperOptions options = new WrapperOptions();
        options.ordinal = ordinal;
        options.name = name;
        options.desc = desc;
        options.required = req;
        options.value = default_val;
        if (options.value == null)
          options.value = "";
        options.type = type;
        options.is_extended = true;

        options_list.add(options);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return options_list;
  }

  public List<WrapperOptions> getWrapperOptionsForServer(String server)
    throws AppException {
      List<WrapperOptions> options_list = new ArrayList<WrapperOptions>();

      try {
        final String query = "select option_name, option_value " +
          "from sys_root.dba_foreign_server_options " +
          "where foreign_server_name = '" + server + "'";

        ResultSet rs = DBAccess.rawResultExec(query);
        while (rs.next()) {
          String op_name = rs.getString(1);
          String op_val = rs.getString(2);

          WrapperOptions options = new WrapperOptions();
          options.name = op_name;
          options.value = op_val;
          options_list.add(options);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return options_list;
  }

  public String createServer(String server_name, String wrapper_name,
      List<WrapperOptions> options)
    throws AppException {

      String retval = "";
      try {
        String query = "CREATE OR REPLACE SERVER " + server_name +
          " FOREIGN DATA WRAPPER \"" + wrapper_name + "\"" +
          " OPTIONS ( ";
        for (WrapperOptions option : options) {
          query += option.name + " '" + option.value + "', ";
        }
        int last_ind = query.lastIndexOf(", ");
        if (last_ind != -1)
          query = query.substring(0, last_ind);
        query += ")";

        ResultSet rs = DBAccess.rawResultExec(query);
      } catch (SQLException e) {
        e.printStackTrace();
        retval = e.getMessage();
      }
      return retval;
  }

  public String testServer(String server_name) throws AppException {
      String retval = "";
      try {
        final String query = "CALL sys_boot.mgmt.test_data_server('" +
          server_name + "')";
        ResultSet rs = DBAccess.rawResultExec(query);
      } catch (SQLException e) {
        e.printStackTrace();
        retval = e.getMessage();
      }
      return retval;
  }

  public RemoteData getForeignData(String server_name) throws AppException {
    long start, end;
    start = System.currentTimeMillis();
    String valid_server = testServer(server_name);
    if (!valid_server.equals(""))
      return null;  // invalid
    end = System.currentTimeMillis();
    System.out.println("validate: " + (end - start)/1000.0);

    RemoteData remote_data = new RemoteData();
    // tmpLocalSchema = "_TMP_LOCAL_SCHEMA" + UUID.randomUUID().toString();
    
    String query;
    try {

      // get a list of already imported tables for this server and
      // unique column identifiers.
      start = System.currentTimeMillis();

      query = "SELECT schema_name, table_name, column_name, datatype " +
        "FROM sys_root.dba_columns c INNER JOIN sys_root.dba_foreign_tables f "
        + "ON foreign_server_name = '" + server_name + "' AND " +
        " table_name = foreign_table_name ORDER BY schema_name, table_name, " +
        "column_name";
      ResultSet rs = DBAccess.rawResultExec(query);
      while (rs != null && rs.next()) {
        int c = 0;
        String schema = rs.getString(++c);
        String table = rs.getString(++c);
        String col = rs.getString(++c);
        String type = rs.getString(++c);
        remote_data.addLocalTableColumn(schema, table, col + type);
      }
      end = System.currentTimeMillis();
      System.out.println("already imported: " + (end - start) / 1000.0);

      start = System.currentTimeMillis();
      // get a list of foreign schemas:
      query = "SELECT DISTINCT schema_name, description FROM table(sys_boot.mgmt.browse_foreign_schemas('" + server_name + "')) order by schema_name";
      rs = DBAccess.rawResultExec(query);
      while (rs.next()) {
        String schema_name = rs.getString(1);
        String desc = rs.getString(2);
        remote_data.foreign_schemas.add(schema_name);
        remote_data.foreign_descriptions.add(desc);
      }
      // If this server has no schemas, maybe there's a default one, so
      // let's check.
      if (remote_data.foreign_schemas.size() == 0) {
        remote_data.foreign_schemas.add("");
        remote_data.foreign_descriptions.add("");
      }
      end = System.currentTimeMillis();
      System.out.println("schemas: " + (end - start) / 1000.0);

      // Get a list of tables and columns for each schema.
      start = System.currentTimeMillis();
      for (String schema_name : remote_data.foreign_schemas) {
        try {
          query = "select distinct table_name, column_name, ordinal, " +
            "column_type, description, default_value " +
            "FROM table(sys_boot.mgmt.browse_foreign_columns('" + server_name +
            "', '" + schema_name + "')) ORDER BY table_name, ordinal";
          // Sometimes need to run this twice...
          rs = DBAccess.rawResultExec(query);
          rs = DBAccess.rawResultExec(query);
          while (rs.next()) {
            int c = 0;
            String table = rs.getString(++c);
            String col = rs.getString(++c);
            int ordinal = rs.getInt(++c);
            String type = rs.getString(++c);
            String desc = rs.getString(++c);
            String default_val = rs.getString(++c);
            remote_data.addForeignTableColumn(schema_name, table, col + type);
          }
        } catch (SQLException ex) {
          // Bad foreign schema?
          continue;
        }
        end = System.currentTimeMillis();
        System.out.println("tables and stuff: " + (end - start) / 1000.0);

      }

      start = System.currentTimeMillis();
      remote_data.findChanges();
      remote_data.readyResults();
      end = System.currentTimeMillis();
      System.out.println("ready results: " + (end - start) / 1000.0);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return remote_data;
  }

  public String importForeignSchema(String server, String from_schema,
      String to_schema,
      List<String> tables) throws AppException {
    String retval = "";
    try {
      if (from_schema == null || from_schema.equals("")) from_schema = "";
      String query = "IMPORT FOREIGN SCHEMA \"" + from_schema + "\"";
      if (tables.size() > 0) {
        query += " LIMIT TO (";
        for (String table : tables) {
          query += "\"" + table + "\",";
        }
        int last_ind = query.lastIndexOf(",");
        if (last_ind != -1)
          query = query.substring(0, last_ind) + ")";
      }
      query += " FROM SERVER " + server + " INTO \"" + to_schema + "\"";
      System.out.println(query);
      ResultSet rs = DBAccess.rawResultExec(query);
    } catch (SQLException e) {
      e.printStackTrace();
      retval = e.getMessage();
    }
    return retval;
  }

}

