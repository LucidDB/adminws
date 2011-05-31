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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="systemparameterholder")
public class SystemParameterHolder  {

  public List<SystemParameter> params;

  // Auto-generated for AMF
  @XmlElement
  public List<SystemParameter> getParams() { return params; }
  public void setParams(List<SystemParameter> params) { this.params = params; }

}

