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

/**
 * Holds the detailed information on a table.
 * 
 * @author Nicholas Goodman
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="tabledetails")
public class TableDetails
{
	@XmlAttribute public String uuid;
	@XmlAttribute public String name;
	@XmlAttribute public String schema;
	@XmlAttribute public String catalog;
	@XmlAttribute public Date create_time;
	@XmlAttribute public Integer last_analyze_row_count;
	@XmlAttribute public Date last_analyze_timestamp;
	@XmlAttribute public Integer current_row_count;
	@XmlAttribute public Integer deleted_row_count;
	@XmlAttribute public String table_type;
	@XmlElement public List<Column> column;
	
	
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
	
	
	

}
