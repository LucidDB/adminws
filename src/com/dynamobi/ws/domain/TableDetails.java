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

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.sql.*;
import com.dynamobi.ws.domain.DBLoader;

/**
 * Holds the detailed information on a table.
 * 
 * @author Nicholas Goodman
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="tabledetails")
public class TableDetails extends DBLoader<TableDetails>
{
	public String uuid;
	public String name;
	public String schema;
	public String catalog;
	public Date create_time;
	public Integer last_analyze_row_count;
	public Date last_analyze_timestamp;
	public Integer current_row_count;
	public Integer deleted_row_count;
	public String table_type;
	public List<Column> column;
	
	
  public String toString() {
    return "uuid: " + uuid + "\nname: " + name + "\nschema: " + schema
      + "\ncatalog: " + catalog + "\ncreate_time: " + create_time.toString()
      + "\nlast_analyze_row_count: " + last_analyze_row_count
      + "\nlast_analyze_timestamp: " + last_analyze_timestamp
      + "\ncurrent_row_count: " + current_row_count
      + "\ndeleted_row_count: " + deleted_row_count
      + "\ntable_type: " + table_type
      + "\ncolumns: " + column.toString();
  }
	
  public void loadRow(ResultSet rs) throws SQLException {
    int c = 1;
    name = rs.getString(c++);
    schema = rs.getString(c++);
    catalog = rs.getString(c++);
    uuid = rs.getString(c++);
    create_time = rs.getDate(c++);
    last_analyze_row_count = rs.getInt(c++);
    last_analyze_timestamp = rs.getDate(c++);
    current_row_count = rs.getInt(c++);
    deleted_row_count = rs.getInt(c++);
    table_type = rs.getString(c++);
  }

  public void finalize() { }
  public TableDetails copy() { return this; }

  // Auto-generated for AMF
  @XmlAttribute
  public String getUuid() { return uuid; }
  public void setUuid(String uuid) { this.uuid = uuid; }

  @XmlAttribute
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @XmlAttribute
  public String getSchema() { return schema; }
  public void setSchema(String schema) { this.schema = schema; }

  @XmlAttribute
  public String getCatalog() { return catalog; }
  public void setCatalog(String catalog) { this.catalog = catalog; }

  @XmlAttribute
  public Date getCreate_time() { return create_time; }
  public void setCreate_time(Date create_time) { this.create_time = create_time; }

  @XmlAttribute
  public Integer getLast_analyze_row_count() { return last_analyze_row_count; }
  public void setLast_analyze_row_count(Integer last_analyze_row_count) { this.last_analyze_row_count = last_analyze_row_count; }

  @XmlAttribute
  public Date getLast_analyze_timestamp() { return last_analyze_timestamp; }
  public void setLast_analyze_timestamp(Date last_analyze_timestamp) { this.last_analyze_timestamp = last_analyze_timestamp; }

  @XmlAttribute
  public Integer getCurrent_row_count() { return current_row_count; }
  public void setCurrent_row_count(Integer current_row_count) { this.current_row_count = current_row_count; }

  @XmlAttribute
  public Integer getDeleted_row_count() { return deleted_row_count; }
  public void setDeleted_row_count(Integer deleted_row_count) { this.deleted_row_count = deleted_row_count; }

  @XmlAttribute
  public String getTable_type() { return table_type; }
  public void setTable_type(String table_type) { this.table_type = table_type; }

  @XmlElement
  public List<Column> getColumn() { return column; }
  public void setColumn(List<Column> column) { this.column = column; }

}

