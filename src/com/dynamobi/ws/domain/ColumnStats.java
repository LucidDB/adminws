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
/**
 * 
 */
package com.dynamobi.ws.domain;

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
