/**
 * 
 */
package com.dynamobi.impl;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.domain.Counter;
import com.dynamobi.util.AppException;
import com.dynamobi.util.DBAccess;

/**
 * @author zhangrui
 *
 */
@Path("/counter")
@WebService(
    endpointInterface="com.dynamobi.api.PerformanceCountersService"
)
public class PerformanceCountersService
    implements com.dynamobi.api.PerformanceCountersService
{

    /* (non-Javadoc)
     * @see com.dynamobi.api.PerformanceCountersService#findPerformanceCounterByName(java.lang.String)
     */
    public Counter findPerformanceCounterByName(String counterName)
        throws AppException
    {
        Counter co = DBAccess.findPerformanceCounterByName(counterName);
        return co;
    }

    /* (non-Javadoc)
     * @see com.dynamobi.api.PerformanceCountersService#getAllPerformanceCounters()
     */
    public List<Counter> getAllPerformanceCounters()
        throws AppException
    {
       List<Counter> list = DBAccess.getAllPerformanceCounters();
        return list;
    }

}
