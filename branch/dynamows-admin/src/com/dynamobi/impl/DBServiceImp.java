package com.dynamobi.impl;

import java.util.List;

import javax.ws.rs.Path;

import com.dynamobi.api.DBService;
import com.dynamobi.domain.Column;
import com.dynamobi.domain.Table;
import com.dynamobi.util.AppException;
import com.dynamobi.util.DBAccess;

/**
 * DBService implementation
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */

@Path("/TableService")
public class DBServiceImp
    implements DBService
{

    public List<Table> readTablesInfo()
        throws AppException
    {

        List<Table> ret = DBAccess.getTableInfo();

        return ret;

    }

    public List<Table> readTablesInfo(String schemaName)
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

}
