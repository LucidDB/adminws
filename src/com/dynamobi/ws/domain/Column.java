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
  @XmlAttribute public String default_value;
	@XmlAttribute public String remarks;
	@XmlAttribute public Integer distinct_value_count;
	@XmlAttribute public Boolean distinct_value_count_estimated;
	@XmlAttribute public Date last_analyze_time;

  public String toString() {
    return "uuid: " + uuid + "\nname: " + name + "\nordinal_position: "
      + ordinal_position + "\ndatatype: " + datatype
      + "\nprecision: " + precision + "\ndec_digits: " + dec_digits
      + "\nis_nullable: " + is_nullable + "\nremarks: " + remarks
      + "\ndefault_value: " + default_value
      + "\ndistinct_value_count: " + distinct_value_count
      + "\ndistinct_value_count_estimated: " + distinct_value_count_estimated
      + "\nlast_analyze_time: " + last_analyze_time;
  }

}
