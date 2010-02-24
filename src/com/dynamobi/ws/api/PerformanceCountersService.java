package com.dynamobi.ws.api;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.dynamobi.ws.domain.Counter;
import com.dynamobi.ws.util.AppException;

@WebService
public interface PerformanceCountersService
{   

    /**
     * Read all current performance counters.
     * @return a list of Counters
     * @throws AppException
     */
    @GET
    List<Counter> getAllPerformanceCounters() throws AppException;
    /**
     * Read special performance counter thru counter name.
     * @param counterName
     * @return Counter
     * @throws AppException
     */
    @GET
    @Path("/{counter_name}")
    Counter findPerformanceCounterByName(@PathParam("counter_name") String counterName) throws AppException;
}
