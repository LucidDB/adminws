/**
 * 
 */
package com.dynamobi.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
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
	@XmlAttribute public int last_analyze_row_count;
	@XmlAttribute public Date last_analyze_timestamp;
	@XmlAttribute public int current_row_count;
	@XmlAttribute public int deleted_row_count;
	@XmlAttribute public String table_type;
	
	
	
	
	

}
