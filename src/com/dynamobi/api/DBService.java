package com.dynamobi.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.dynamobi.domain.Column;
import com.dynamobi.domain.Table;
import com.dynamobi.util.AppException;

/**
 * Interface: Get tables' info from system views.
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */

public interface DBService
{

    @GET
    public List<Table> readTablesInfo()
        throws AppException;

    @Path("/{schema}")
    @GET
    public List<Table> readTablesInfoBySchema(@PathParam("schema")
    String schemaName)
        throws AppException;

    @Path("/{tableName}/{schemaName}")
    @GET
    public List<Column> readColumnNamesFromTable(@PathParam("tableName")
    String tableName, @PathParam("schemaName")
    String schemaName)
        throws AppException;

}
