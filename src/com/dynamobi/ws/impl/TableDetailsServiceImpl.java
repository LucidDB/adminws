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

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.ws.api.TableDetailsService;
import com.dynamobi.ws.domain.Column;
import com.dynamobi.ws.domain.Schema;
import com.dynamobi.ws.domain.Table;
import com.dynamobi.ws.domain.TableDetails;
import com.dynamobi.ws.domain.TablesInfo;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DBAccess;
import com.dynamobi.ws.util.DB;

/**
 * TableDetailsService implementation
 * 
 * @author Kevin Secretan
 * @since June 17, 2010
 */
@WebService(endpointInterface = "com.dynamobi.ws.api.TableDetailsService")
@Path("/metadata")
public class TableDetailsServiceImpl implements TableDetailsService {

  public TableDetails getTableDetails(String catalog,
      String schema, String table) throws AppException {
    TableDetails retVal = new TableDetails();
    String query = DB.select(
        "dst.table_name, dst.schema_name, dst.catalog_name, dt.lineage_id, " +
        "dst.creation_timestamp, dst.last_analyze_row_count, " +
        "dst.last_analyze_timestamp, dst.current_row_count, " +
        "dst.deleted_row_count,dt.table_type",
        "sys_boot.mgmt.dba_stored_tables_internal1 dst join " +
        "sys_root.dba_tables dt on dst.\"lineageId\" = dt.lineage_id",
        DB.populate(
          "dt.catalog_name = {0,lit} and dt.schema_name = {1,lit} and " +
          "dt.table_name = {2,lit}", catalog, schema, table));
    DB.execute(query, retVal);

    query = DB.select(
        "dc.lineage_id, dc.column_name, dc.ordinal_position, dc.datatype," +
        "dc.\"PRECISION\", dc.dec_digits, dc.is_nullable, dc.remarks, " +
        "dcs.distinct_value_count, dcs.is_distinct_value_count_estimated, " +
        "dcs.last_analyze_time, dc.default_value",
        "sys_root.dba_columns dc left join sys_root.dba_column_stats " +
        "dcs on dc.table_name = dcs.table_name and " +
        "dc.schema_name = dcs.schema_name and " +
        "dc.catalog_name = dcs.catalog_name and " +
        "dc.column_name = dcs.column_name ",
        DB.populate(
          "dc.catalog_name = {0,lit} and dc.schema_name = {1,lit} and " +
          "dc.table_name = {2,lit}", catalog, schema, table));
    List<Column> columns = new ArrayList<Column>();
    Column commander = new Column();
    DB.execute(query, commander, columns);
    retVal.column = columns;
    return retVal;
  }

  public String createSchema(String catalog, String schema)
    throws AppException
  {
    String query = DB.populate(
        "create schema {0,id}.{1,id}", catalog.trim(),
        schema.trim());
    return DB.execute_success(query);
  }

  public boolean postTableDetails(String catalog, String schema,
      String table, TableDetails details)
  {
    if (details == null) {
      return false;
    }
    try {
      boolean retval = DBAccess.postTableDetails(catalog, schema, table, details);
    } catch (AppException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

}
