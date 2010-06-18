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
