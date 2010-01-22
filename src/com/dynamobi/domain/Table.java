/**
 * 
 */
package com.dynamobi.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * VO: It holds the table information.
 * 
 * @author Ray Zhang
 * @since Jan-11-2010
 */
@XmlRootElement
public class Table
{

    private String tableName;

    private String schemaName;

    private String catalogName;

    public String getCatalogName()
    {
        return catalogName;
    }

    public void setCatalogName(String catalogName)
    {
        this.catalogName = catalogName;
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
