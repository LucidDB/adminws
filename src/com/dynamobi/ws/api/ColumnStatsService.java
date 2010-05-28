/**
 * 
 */
package com.dynamobi.ws.api;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.dynamobi.ws.domain.ColumnStats;
import com.dynamobi.ws.util.AppException;

/**
 * @author zhangrui
 * 
 */
@WebService
@PermitAll
public interface ColumnStatsService
{

    /**
     * Read all column status.
     * 
     * @return a list of ColumnStats
     * @throws AppException
     */
    @GET
    @RolesAllowed( { "Admin", "Authenticated" })
    List<ColumnStats> getAllColumnStats()
        throws AppException;

    /**
     * Read special column status based on input condition.
     * 
     * @return a list of ColumnStats
     * @throws AppException
     */
    @GET
    @Path("/find")
    @RolesAllowed( { "Admin", "Authenticated" })
    List<ColumnStats> findColumnStats(@QueryParam("catalog")
    String catalogName, @QueryParam("schema")
    String schemaName, @QueryParam("table")
    String tableName, @QueryParam("column")
    String columnName)
        throws AppException;

}
