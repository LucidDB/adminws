package com.dynamobi.impl;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.api.SystemParameterService;
import com.dynamobi.domain.SystemParameter;
import com.dynamobi.util.AppException;
import com.dynamobi.util.DBAccess;

@Path("/sysparam")
@WebService(
    endpointInterface="com.dynamobi.api.SystemParameterService"
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
