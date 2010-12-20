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

import java.sql.*;
import com.dynamobi.ws.domain.DBLoader;

/**
 * VO: It holds the column information.
 * 
 * @author rzhang, ngoodman
 * @since Jan-15-2010
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Column extends DBLoader<Column>
{
	public String name;
	public String uuid;
	public Integer ordinal_position;
	public String datatype;
	public Integer precision;
	public Integer dec_digits;
	public Boolean is_nullable;
  public String default_value;
	public String remarks;
	public Integer distinct_value_count;
	public Boolean distinct_value_count_estimated;
	public Date last_analyze_time;

  private Column copy;

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

  public void loadRow(ResultSet rs) throws SQLException {
    Column c = new Column();
    c.uuid = rs.getString(1);
    c.name = rs.getString(2);
    if (c.name != null)
      c.name = c.name.replaceAll("\"", "&quot;");
    c.ordinal_position = rs.getInt(3);
    c.datatype = rs.getString(4);
    c.precision = rs.getInt(5);
    c.dec_digits = rs.getInt(6);
    c.is_nullable = rs.getBoolean(7);
    c.remarks = rs.getString(8);
    c.distinct_value_count = rs.getInt(9);
    c.distinct_value_count_estimated = rs.getBoolean(10);
    c.last_analyze_time = rs.getDate(11);
    c.default_value = rs.getString(12);
    copy = c;
  }

  public void finalize() { }
  public Column copy() { return copy; }

  // Auto-generated for AMF
  @XmlAttribute
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @XmlAttribute
  public String getUuid() { return uuid; }
  public void setUuid(String uuid) { this.uuid = uuid; }

  @XmlAttribute
  public Integer getOrdinal_position() { return ordinal_position; }
  public void setOrdinal_position(Integer ordinal_position) { this.ordinal_position = ordinal_position; }

  @XmlAttribute
  public String getDatatype() { return datatype; }
  public void setDatatype(String datatype) { this.datatype = datatype; }

  @XmlAttribute
  public Integer getPrecision() { return precision; }
  public void setPrecision(Integer precision) { this.precision = precision; }

  @XmlAttribute
  public Integer getDec_digits() { return dec_digits; }
  public void setDec_digits(Integer dec_digits) { this.dec_digits = dec_digits; }

  @XmlAttribute
  public Boolean getIs_nullable() { return is_nullable; }
  public void setIs_nullable(Boolean is_nullable) { this.is_nullable = is_nullable; }

  @XmlAttribute
  public String getDefault_value() { return default_value; }
  public void setDefault_value(String default_value) { this.default_value = default_value; }

  @XmlAttribute
  public String getRemarks() { return remarks; }
  public void setRemarks(String remarks) { this.remarks = remarks; }

  @XmlAttribute
  public Integer getDistinct_value_count() { return distinct_value_count; }
  public void setDistinct_value_count(Integer distinct_value_count) { this.distinct_value_count = distinct_value_count; }

  @XmlAttribute
  public Boolean getDistinct_value_count_estimated() { return distinct_value_count_estimated; }
  public void setDistinct_value_count_estimated(Boolean distinct_value_count_estimated) { this.distinct_value_count_estimated = distinct_value_count_estimated; }

  @XmlAttribute
  public Date getLast_analyze_time() { return last_analyze_time; }
  public void setLast_analyze_time(Date last_analyze_time) { this.last_analyze_time = last_analyze_time; }

}

