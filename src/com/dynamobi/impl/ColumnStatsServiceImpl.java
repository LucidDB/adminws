/**
 * 
 */
package com.dynamobi.impl;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.api.ColumnStatsService;
import com.dynamobi.domain.ColumnStats;
import com.dynamobi.util.AppException;
import com.dynamobi.util.DBAccess;

/**
 * @author zhangrui
 *
 */

@Path("/colstats")
@WebService(
    endpointInterface = "com.dynamobi.api.ColumnStatsService"
)

public class ColumnStatsServiceImpl
    implements ColumnStatsService
{

    /* (non-Javadoc)
     * @see com.dynamobi.api.ColumnStatsService#findColumnStats(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public List<ColumnStats> findColumnStats(
        String catalogName,
        String schemaName,
        String tableName,
        String columnName)
        throws AppException
    {
        List<ColumnStats> list = DBAccess.findColumnStats(catalogName,
            schemaName,
            tableName,
            columnName);
        return list;
    }

    /* (non-Javadoc)
     * @see com.dynamobi.api.ColumnStatsService#getAllColumnStats()
     */
    public List<ColumnStats> getAllColumnStats()
        throws AppException
    {
        List<ColumnStats> list = DBAccess.getAllColumnStats();
        return list;
    }

}