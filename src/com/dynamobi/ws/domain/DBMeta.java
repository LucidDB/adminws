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
