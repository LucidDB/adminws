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

/**
 * TableDetailsService implementation
 * 
 * @author Kevin Secretan
 * @since June 17, 2010
 */
@WebService(endpointInterface = "com.dynamobi.ws.api.TableDetailsService")
@Path("/metadata")
public class TableDetailsServiceImpl implements TableDetailsService {

  public TablesInfo readTablesInfo() throws AppException {
    return DBAccess.getTablesInfo();
    // List<Table> ret = DBAccess.getTableInfo();
    // return ret;
  }

  public List<Table> readTablesInfoBySchema(String schemaName)
    throws AppException
  {
    List<Table> ret = DBAccess.getTableInfo(schemaName);
    return ret;
  }

  public List<Column> readColumnNamesFromTable(String tableName,
      String schemaName) throws AppException
  {
    List<Column> ret = DBAccess.getColumnNamesFromTable(tableName,
                                                        schemaName);
    return ret;
  }

  public TableDetails getTableDetails(String catalog, String schema, String table)
    throws AppException
  {
    TableDetails ret = DBAccess.getTableDetails(catalog, schema, table);
    return ret;
  }

  public Schema getSchema(String catalogName, String schemaName)
    throws AppException
  {
    return DBAccess.getSchemaByName(catalogName, schemaName);
  }

  public String createSchema(String catalog, String schema)
    throws AppException
  {
    try {
      DBAccess.createSchema(catalog, schema);
    } catch (AppException ex) {
      return ex.getMessage();
    }
    return "";
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
