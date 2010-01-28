/**
 * 
 */
package com.dynamobi.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhangrui
 *
 */
@XmlRootElement
public class ColumnStats
{
    private String catalogName;
    
    private String schemaName;
    
    private String tableName;
    
    private String columnName;
    
    private long distinctValueCount;
    
    private boolean isDistinctValueCountEstimated;
    
    private double percentSampled;
    
    private long sampleSize;

    public String getCatalogName()
    {
        return catalogName;
    }

    public void setCatalogName(String catalogName)
    {
        this.catalogName = catalogName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public long getDistinctValueCount()
    {
        return distinctValueCount;
    }

    public void setDistinctValueCount(long distinctValueCount)
    {
        this.distinctValueCount = distinctValueCount;
    }

    public boolean isDistinctValueCountEstimated()
    {
        return isDistinctValueCountEstimated;
    }

    public void setDistinctValueCountEstimated(boolean isDistinctValueCountEstimated)
    {
        this.isDistinctValueCountEstimated = isDistinctValueCountEstimated;
    }

    public double getPercentSampled()
    {
        return percentSampled;
    }

    public void setPercentSampled(double percentSampled)
    {
        this.percentSampled = percentSampled;
    }

    public long getSampleSize()
    {
        return sampleSize;
    }

    public void setSampleSize(long sampleSize)
    {
        this.sampleSize = sampleSize;
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    
    
}
