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
