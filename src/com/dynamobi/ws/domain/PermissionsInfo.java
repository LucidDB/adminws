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
/*
 *
 */
package com.dynamobi.ws.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="info")
public class PermissionsInfo {

  public String catalog_name;
  public String schema_name;
  public String item_name;
  public String item_type;
  public List<String> actions;

  public PermissionsInfo() {
    actions = new ArrayList<String>();
  }

  public String toString() {
    return
     "\ncatalog_name: " + catalog_name + 
     "\nschema_name: " + schema_name +
     "\nitem_name: " + item_name +
     "\nitem_type: " + item_type +
     "\nactions: " + actions;
  }

  // Auto-generated for AMF
  @XmlAttribute
  public String getCatalog_name() { return catalog_name; }
  public void setCatalog_name(String catalog_name) { this.catalog_name = catalog_name; }

  @XmlAttribute
  public String getSchema_name() { return schema_name; }
  public void setSchema_name(String schema_name) { this.schema_name = schema_name; }

  @XmlAttribute
  public String getItem_name() { return item_name; }
  public void setItem_name(String item_name) { this.item_name = item_name; }

  @XmlAttribute
  public String getItem_type() { return item_type; }
  public void setItem_type(String item_type) { this.item_type = item_type; }

  @XmlAttribute
  @XmlList public List<String> getActions() { return actions; }
  public void setActions(List<String> actions) { this.actions = actions; }

}

