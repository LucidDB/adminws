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


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="foreignttable")
public class ForeignTable {
	
	private String catalog;
	private String schema;
	private String name;
	private String server;
	private List<Option> options = new ArrayList<Option>();
	private List<Column> columns = new ArrayList<Column>();


	@XmlAttribute (name = "catalog")
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	@XmlAttribute (name = "schema")
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "options")
	@XmlElement(name = "option")
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	@XmlElementWrapper(name = "columns")
	@XmlElement(name = "column")
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	@XmlAttribute
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	

	

	

}



