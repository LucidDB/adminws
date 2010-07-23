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

  public Schema putSchema(String catalogName, Schema schema)
    throws AppException
  {
    return DBAccess.putSchema(catalogName, schema);
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
