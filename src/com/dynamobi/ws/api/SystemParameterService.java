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
package com.dynamobi.ws.api;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.dynamobi.ws.domain.SystemParameter;
import com.dynamobi.ws.domain.SystemParameterHolder;
import com.dynamobi.ws.util.AppException;

@WebService
public interface SystemParameterService
{
    /**
     * Read all system parameters
     * @return a list of SystemParameter
     */
    @GET
    SystemParameterHolder getAllSystemParameters() throws AppException;
    
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
