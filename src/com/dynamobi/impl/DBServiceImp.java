package com.dynamobi.impl;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.api.DBService;
import com.dynamobi.domain.Column;
import com.dynamobi.domain.Table;
import com.dynamobi.domain.TableDetails;
import com.dynamobi.util.AppException;
import com.dynamobi.util.DBAccess;

/**
 * DBService implementation
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */
@Path("/tables")
@WebService (
    endpointInterface = "com.dynamobi.api.DBService"
  )
public class DBServiceImp
    implements DBService
{

    public List<Table> readTablesInfo()
        throws AppException
    {

        List<Table> ret = DBAccess.getTableInfo();

        return ret;

    }

    public List<Table> readTablesInfoBySchema(String schemaName)
        throws AppException
    {

        List<Table> ret = DBAccess.getTableInfo(schemaName);

        return ret;

    }

    public List<Column> readColumnNamesFromTable(String tableName,

    String schemaName)
        throws AppException
    {

        List<Column> ret = DBAccess.getColumnNamesFromTable(
            tableName,
            schemaName);

        return ret;

    }
    
    public TableDetails getTableDetails(String schema, String table) 
    throws AppException
    {
    	TableDetails ret = DBAccess.getTableDetails(schema, table);
    	
    	return ret;
    }

}
