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
import com.dynamobi.ws.domain.ForeignServer;
import com.dynamobi.ws.domain.Wrapper;
import com.dynamobi.ws.domain.WrapperOptions;
import com.dynamobi.ws.domain.RemoteData;
import com.dynamobi.ws.domain.XMLStructure;

import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DB;
import com.dynamobi.ws.util.DBAccess;

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
    WrapperOptions first = new WrapperOptions(true); // is_extended
    final String query = DB.select("distinct option_ordinal, option_name, " +
        "option_description, is_option_required, " +
        "option_choice_value as option_default_value, " +
        "case  " +
        "  when option_choice_value = 'TRUE' OR option_choice_value = 'FALSE' then 'BOOLEAN' " +
        "  else 'TEXT' " +
        "end as option_type, " +
        "option_choice_ordinal", DB.populate(
          "table(sys_boot.mgmt.browse_connect_foreign_server({0,lit}, " +
          "  cursor(values ('DRIVER_CLASS', {1,lit}), ('URL', ''), " +
          "    ('EXTENDED_OPTIONS', 'TRUE')))) ", wrapper, driver),
        "option_choice_ordinal = -1 ") +
      "order by option_ordinal ";
    DB.execute(query, first, options_list);
    return options_list;
  }

  public List<WrapperOptions> getWrapperOptionsForServer(String server)
      throws AppException {
    List<WrapperOptions> options_list = new ArrayList<WrapperOptions>();
    WrapperOptions first = new WrapperOptions("for_server");
    final String query = DB.select("option_name, option_value",
        "sys_root.dba_foreign_server_options",
        DB.populate("foreign_server_name = {0,lit}", server));
    DB.execute(query, first, options_list);
    return options_list;
  }

  public String createServer(String server_name, String wrapper_name,
      List<WrapperOptions> options)
      throws AppException {
    String query = DB.populate("CREATE OR REPLACE SERVER {0,str} " +
        " FOREIGN DATA WRAPPER {1,id}" +
        " OPTIONS ( ", server_name, wrapper_name);
    boolean is_first = true;
    for (WrapperOptions option : options) {
      if (!is_first) query += ", ";
      else is_first = false;
      query += DB.populate("{0,str} {1,lit}", option.name, option.value);
    }
    query += ")";
    return DB.execute_success(query);
  }

  public String testServer(String server_name) throws AppException {
    final String query = DB.populate(
        "CALL sys_boot.mgmt.test_data_server({0,lit})", server_name);
    return DB.execute_success(query);
  }

  public RemoteData getForeignData(String server_name) throws AppException {
    long start, end;
    // tmpLocalSchema = "_TMP_LOCAL_SCHEMA" + UUID.randomUUID().toString();
    start = System.currentTimeMillis();
    String valid_server = testServer(server_name);
    if (!valid_server.equals(""))
      return null;  // invalid
    end = System.currentTimeMillis();
    System.out.println("validate: " + (end - start)/1000.0);

    // get a list of already imported tables for this server and
    // unique column identifiers.
    start = System.currentTimeMillis();
    String query = DB.select("schema_name, table_name, column_name, datatype",
        "sys_root.dba_columns c INNER JOIN sys_root.dba_foreign_tables f " +
        DB.populate("ON foreign_server_name = {0,lit} AND ", server_name) +
        " table_name = foreign_table_name ORDER BY schema_name, table_name, " +
        "column_name");
    RemoteData remote_data = new RemoteData();
    DB.execute(query, remote_data);
    end = System.currentTimeMillis();
    System.out.println("already imported: " + (end - start) / 1000.0);

    start = System.currentTimeMillis();
    // get a list of foreign schemas:
    query = DB.select("DISTINCT schema_name, description",
        DB.populate("table(sys_boot.mgmt.browse_foreign_schemas({0,lit}))",
          server_name) + " order by schema_name");
    remote_data.data_mode = "foreign_schemas";
    DB.execute(query, remote_data);
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
    remote_data.data_mode = "foreign_tables";
    for (String schema_name : remote_data.foreign_schemas) {
      remote_data.fschema_name = schema_name;
      query = DB.select("distinct table_name, column_name, ordinal, " +
          "formatted_datatype as column_type, description, default_value ",
          DB.populate(
            "table(sys_boot.mgmt.browse_foreign_columns({0,lit}, {1,lit}))",
            server_name, schema_name) + " ORDER BY table_name, ordinal");
      DB.execute(query, remote_data);
      // mysteriously sometimes this causes an error in some dark corner
      end = System.currentTimeMillis();
      System.out.println("tables and stuff: " + (end - start) / 1000.0);
    }

    start = System.currentTimeMillis();
    remote_data.findChanges();
    remote_data.readyResults();
    end = System.currentTimeMillis();
    System.out.println("ready results: " + (end - start) / 1000.0);
    return remote_data;
  }

  public String importForeignSchema(String server, String from_schema,
      String to_schema,
      List<String> tables, boolean copy_local) throws AppException {
    if (from_schema == null || from_schema.equals("")) {
      String q = DB.select("option_value","sys_root.dba_foreign_server_options",
          DB.populate(
            "foreign_server_name={0,lit} and option_name='SCHEMA_NAME'",
            server));
      XMLStructure sch_name = new XMLStructure("schema_name");
      DB.execute(q, sch_name);
      try {
        from_schema = sch_name.row_data.get(0);
      } catch (IndexOutOfBoundsException e) {
        from_schema = "";
      }
    }
    String query = DB.populate("IMPORT FOREIGN SCHEMA {0,id}", from_schema);
    if (tables.size() > 0) {
      query += " LIMIT TO (";
      boolean is_first = true;
      for (String table : tables) {
        if (!is_first) query += ",";
        else is_first = false;
        query += DB.populate("{0,id}", table);
      }
      query += ")";
    }
    query += DB.populate(" FROM SERVER {0,str} INTO {1,id}", server, to_schema);
    String ret = DB.execute_success(query);
    if (copy_local) {
      for (String table : tables) {
        ret += copyForeignTable("LOCALDB", to_schema, table, to_schema,
            "local_" + table);
      }
    }
    return ret;
  }

  public String copyForeignTable(String catalog, String from_schema,
      String from_table, String to_schema, String to_table) throws AppException
  {
    // do we need to create to_table?
    String q = DB.select("table_name",
        "sys_root.dba_tables",
        DB.populate("catalog_name={0,lit} and schema_name={1,lit} and " +
          "table_name={2,lit}", catalog, to_schema, to_table));
    XMLStructure exist_check = new XMLStructure("tab");
    DB.execute(q, exist_check);
    boolean create = false;
    try {
      exist_check.row_data.get(0);
    } catch (IndexOutOfBoundsException e) {
      create = true;
    }

    String create_success = "";
    if (create) {
      // get structure of from table
      q = DB.select("statement", DB.populate(
          "table(sys_root.generate_ddl_for_table({0,lit}, {1, lit}, {2, lit}))",
            catalog, from_schema, from_table));
      XMLStructure stmt = new XMLStructure("stmt");
      DB.execute(q, stmt);
      StringBuilder create_stmt = new StringBuilder();
      for (String row : stmt.row_data) {
        if (row.indexOf("FOREIGN TABLE") != -1) {
          row = row.replaceFirst("FOREIGN TABLE ",
              DB.populate("TABLE {0,id}.", catalog));
          row = row.replaceFirst(from_schema, to_schema).replaceFirst(
              from_table, to_table);
        }
        if (row.indexOf("SERVER \"") != -1)
          break;
        create_stmt.append(row + "\n");
      }
      create_success = DB.execute_success(create_stmt.toString());
    }

    String query = DB.populate("insert into {0,id}.{1,id}.{2,id} ", catalog,
        to_schema, to_table);
    query += DB.populate("select * from {0,id}.{1,id}.{2,id}", catalog,
        from_schema, from_table);
    return DB.execute_success(query);
  }

  public List<Wrapper> getWrappers() throws AppException {
	// TODO Auto-generated method stub
	return DBAccess.getWrappers();
	
  }


public List<ForeignServer> getForeignServers(String wrapper)
		throws AppException {
	// TODO Auto-generated method stub
	return DBAccess.getForeignServers(wrapper);
}
  
  
  

}
