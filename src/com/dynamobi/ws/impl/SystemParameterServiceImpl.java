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

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.ws.api.SystemParameterService;
import com.dynamobi.ws.domain.SystemParameter;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DBAccess;

@Path("/sysparam")
@WebService(
    endpointInterface="com.dynamobi.ws.api.SystemParameterService"
)
public class SystemParameterServiceImpl
    implements SystemParameterService
{

    public SystemParameter findSystemParameterByName(String paramName) throws AppException
    {
        SystemParameter entity = DBAccess.findSystemParameterByName(paramName);        
        return entity;
    }

    public List<SystemParameter> getAllSystemParameters() throws AppException
    {
        List<SystemParameter> list = DBAccess.getAllSystemParameters();
        return list;
    }

    public boolean updateSystemParameter(String paramName, String paramValue) throws AppException
    {
        
        boolean isSuccess = false;
        isSuccess = DBAccess.updateSystemParameter(paramName,paramValue);
        return isSuccess;
    }

}
