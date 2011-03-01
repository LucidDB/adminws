/*
Dynamo Web Services is a web service project for administering LucidDB
Copyright (C) 2011 Dynamo Business Intelligence Corporation

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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;

/**
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="info")
public class PermissionGroup {
  public String catalog;
  public String schema;
  public String type;
  public String element;
  public String grantee;
  public List<String> permissions;

  // Auto-generated for AMF
  @XmlElement
  public String getCatalog() { return catalog; }
  public void setCatalog(String catalog) { this.catalog = catalog; }

  @XmlElement
  public String getSchema() { return schema; }
  public void setSchema(String schema) { this.schema = schema; }

  @XmlElement
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  @XmlElement
  public String getElement() { return element; }
  public void setElement(String element) { this.element = element; }

  @XmlElement
  public String getGrantee() { return grantee; }
  public void setGrantee(String grantee) { this.grantee = grantee; }

  @XmlElement
  @XmlList public List<String> getPermissions() { return permissions; }
  public void setPermissions(List<String> permissions) { this.permissions = permissions; }

}

