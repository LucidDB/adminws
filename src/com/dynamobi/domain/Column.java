package com.dynamobi.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * VO: It holds the column name information.
 * 
 * @author Ray Zhang
 * @since Jan-15-2010
 */
@XmlRootElement
public class Column
{
    private String columnName;

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

}
