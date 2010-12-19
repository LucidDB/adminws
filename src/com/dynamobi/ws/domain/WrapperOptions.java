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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import com.dynamobi.ws.domain.DBLoader;
import java.sql.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="wrapperoptions")
public class WrapperOptions extends DBLoader<WrapperOptions> {

  public int ordinal;
  public String name;
  public String desc;
  public boolean required;
  public String value;
  public String type;
  public boolean is_extended;

  public String toString() {
    return "\nordinal: " + ordinal +
     "\nname: " + name +
     "\ndesc: " + desc +
     "\nreq: " + required +
     "\nvalue: " + value +
     "\ntype: " + type;
  }

  private WrapperOptions copy;

  public void loadRow(ResultSet rs) throws SQLException {
    int c = 0;
    int ordinal = rs.getInt(++c);
    String name = rs.getString(++c);
    String desc = rs.getString(++c);
    boolean req = rs.getBoolean(++c);
    String default_val = rs.getString(++c);
    String type = rs.getString(++c);

    WrapperOptions options = new WrapperOptions();
    options.ordinal = ordinal;
    options.name = name;
    options.desc = desc;
    options.required = req;
    options.value = default_val;
    if (options.value == null)
      options.value = "";
    options.type = type;
    options.is_extended = false;

    copy = options;
  }

  public void finalize() {

  }

  public WrapperOptions copy() { return copy; }

  // Auto-generated for AMF
  @XmlAttribute
  public int getOrdinal() { return ordinal; }
  public void setOrdinal(int ordinal) { this.ordinal = ordinal; }

  @XmlAttribute
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @XmlAttribute
  public String getDesc() { return desc; }
  public void setDesc(String desc) { this.desc = desc; }

  @XmlAttribute
  public boolean getRequired() { return required; }
  public void setRequired(boolean required) { this.required = required; }

  @XmlAttribute
  public String getValue() { return value; }
  public void setValue(String value) { this.value = value; }

  @XmlAttribute
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  @XmlAttribute
  public boolean getIsExtended() { return is_extended; }
  public void setIsExtended(boolean ex) { is_extended = ex; }

}

