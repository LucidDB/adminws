package com.dynamobi.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Counter
{
    private String sourceName;
    
    private String counterName;
    
    private String counterUnits;
    
    private String counterValue;

    public String getCounterName()
    {
        return counterName;
    }

    public void setCounterName(String counterName)
    {
        this.counterName = counterName;
    }

    public String getCounterUnits()
    {
        return counterUnits;
    }

    public void setCounterUnits(String counterUnits)
    {
        this.counterUnits = counterUnits;
    }

    public String getCounterValue()
    {
        return counterValue;
    }

    public void setCounterValue(String counterValue)
    {
        this.counterValue = counterValue;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }
    
    
    
    
}
