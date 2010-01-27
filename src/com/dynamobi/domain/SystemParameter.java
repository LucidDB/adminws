package com.dynamobi.domain;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class SystemParameter
{
    private String paramName;
    
    private String paramValue;

    public String getParamName()
    {
        return paramName;
    }

    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }

    public String getParamValue()
    {
        return paramValue;
    }

    public void setParamValue(String paramValue)
    {
        this.paramValue = paramValue;
    }
    
    
}
