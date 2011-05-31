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
/**
 * 
 */
package com.dynamobi.ws.impl;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.ws.api.ColumnStatsService;
import com.dynamobi.ws.domain.ColumnStats;
import com.dynamobi.ws.domain.ColumnStatsHolder;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DBAccess;

/**
 * @author zhangrui
 *
 */

@Path("/colstats")
@WebService(
    endpointInterface = "com.dynamobi.ws.api.ColumnStatsService"
)

public class ColumnStatsServiceImpl
    implements ColumnStatsService
{

    /* (non-Javadoc)
     * @see com.dynamobi.api.ColumnStatsService#findColumnStats(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public ColumnStatsHolder findColumnStats(
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
        ColumnStatsHolder ret = new ColumnStatsHolder();
        ret.stats = list;
        return ret;
    }

    /* (non-Javadoc)
     * @see com.dynamobi.api.ColumnStatsService#getAllColumnStats()
     */
    public ColumnStatsHolder getAllColumnStats()
        throws AppException
    {
        List<ColumnStats> list = DBAccess.getAllColumnStats();
        ColumnStatsHolder ret = new ColumnStatsHolder();
        ret.stats = list;
        return ret;
    }

}
