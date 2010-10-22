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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * VO: It holds the schema information.
 * 
 * @author Ray Zhang
 * @since Jan-11-2010
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Schema
{
	public String uuid;
	public String name;
    
	public List<Table> tables;


  // Auto-generated for AMF
  @XmlAttribute
  public String getUuid() { return uuid; }
  public void setUuid(String uuid) { this.uuid = uuid; }

  @XmlAttribute
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }


  // Auto-generated for AMF
  @XmlElement(name="table")
  public List<Table> getTables() { return tables; }
  public void setTables(List<Table> tables) { this.tables = tables; }

}

