package com.dynamobi.api;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.dynamobi.domain.Column;
import com.dynamobi.domain.Table;
import com.dynamobi.domain.TableDetails;
import com.dynamobi.util.AppException;

/**
 * Interface: Get tables' info from system views.
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */
@WebService
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

    @Path("/{schema}/{table}")
    @GET
    public TableDetails getTableDetails(@PathParam("schema")String schema,
    		@PathParam("table") String table) throws AppException;

}
