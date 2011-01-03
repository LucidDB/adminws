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
import java.util.Arrays;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.ws.domain.Counter;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DB;

/**
 * @author zhangrui, Kevin
 *
 */
@Path("/counter")
@WebService(
    endpointInterface="com.dynamobi.ws.api.PerformanceCountersService"
)
public class PerformanceCountersService
    implements com.dynamobi.ws.api.PerformanceCountersService
{

    /* (non-Javadoc)
     * @see com.dynamobi.api.PerformanceCountersService#findPerformanceCounterByName(java.lang.String)
     */
    public Counter findPerformanceCounterByName(String counterName)
        throws AppException
    {
      Counter retVal = new Counter();

      final String query = DB.select(
          "source_name, counter_name, counter_units, counter_value",
          "SYS_ROOT.DBA_PERFORMANCE_COUNTERS",
          DB.populate("counter_name = {0,lit}", counterName));
      DB.execute(query, retVal);
      return retVal;
    }

    /* (non-Javadoc)
     * @see com.dynamobi.api.PerformanceCountersService#getAllPerformanceCounters()
     */
    public List<Counter> getAllPerformanceCounters()
        throws AppException
    {
      List<Counter> retVal = new ArrayList<Counter>();

      final String query = DB.select(
          "counter_category, counter_subcategory, source_name, counter_name, " +
          "counter_units, counter_value ", "sys_root.dba_performance_counters");
      Counter commander = new Counter();
      commander.list_mode = true;
      DB.execute(query, commander, retVal);
      return retVal;
    }

    public List<Counter> getCountersByNames(String names)
      throws AppException {
      List<Counter> retVal = new ArrayList<Counter>();
      String query = DB.select(
          "counter_category, counter_subcategory, source_name, counter_name, " +
          "counter_units, counter_value", "sys_root.dba_performance_counters",
          DB.populate("counter_name IN ({0,lit_list})",
            Arrays.asList(names.split(","))));
      Counter commander = new Counter();
      commander.list_mode = true;
      DB.execute(query, commander, retVal);
      return retVal;
    }
}
