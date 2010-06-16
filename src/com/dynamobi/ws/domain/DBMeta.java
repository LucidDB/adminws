/**
 * 
 */
package com.dynamobi.ws.domain;

/**
 * VO for getDBMeta()
 * @author Ray Zhang
 * @since Feb 04,2010
 *
 * @author Kevin Secretan
 * @since June 15, 2010
 */
public class DBMeta {
  
  private String schemaName;
  private String type;
  private String name;
  private String colName;
  private int colLength;
  private boolean colIsNull;
  private String dataType;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColName() {
    return colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }

  public int getColLength() {
    return colLength;
  }

  public void setColLength(int colLength) {
    this.colLength = colLength;
  }

  public boolean isColIsNull() {
    return colIsNull;
  }

  public void setColIsNull(boolean colIsNull) {
    this.colIsNull = colIsNull;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getSchemaName()
  {
    return schemaName;
  }

  public void setSchemaName(String schemaName)
  {
    this.schemaName = schemaName;
  }

  public String toString() {
    return "Schema: " + schemaName + ", Type: " + type + ", Name: " + name
      + ", Column Name: " + colName + ", Column Length: " + colLength
      + ", Is Null: " + colIsNull + ", DataType: " + dataType;
  }

}
