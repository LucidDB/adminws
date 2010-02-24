package com.dynamobi.ws.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * VO: It holds the column information.
 * 
 * @author rzhang, ngoodman
 * @since Jan-15-2010
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Column
{
	@XmlAttribute public String name;
	@XmlAttribute public String uuid;
	@XmlAttribute public Integer ordinal_position;
	@XmlAttribute public String datatype;
	@XmlAttribute public Integer precision;
	@XmlAttribute public Integer dec_digits;
	@XmlAttribute public Boolean is_nullable;
	@XmlAttribute public String remarks;
	@XmlAttribute public Integer distinct_value_count;
	@XmlAttribute public Boolean distinct_value_count_estimated;
	@XmlAttribute public Date last_analyze_time;
	

}