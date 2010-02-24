package com.dynamobi.ws.api;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.dynamobi.ws.domain.SystemParameter;
import com.dynamobi.ws.util.AppException;

@WebService
public interface SystemParameterService
{
    /**
     * Read all system parameters
     * @return a list of SystemParameter
     */
    @GET
    List<SystemParameter> getAllSystemParameters() throws AppException;
    
    /**
     * Read special system parameter thru parameter name
     * @return a SystemParameter
     */
    @GET
    @Path("/{param_name}")
    
    SystemParameter findSystemParameterByName( @PathParam ("param_name") String paramName) throws AppException;
    
    /**
     * Update special system parameter thru parameter name
     * @return boolean value
     */
    @POST
    boolean updateSystemParameter(@QueryParam("param_name") String paramName, @QueryParam("param_value") String paramValue) throws AppException;
}
