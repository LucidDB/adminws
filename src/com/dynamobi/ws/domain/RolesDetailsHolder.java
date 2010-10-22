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
/**
 *
 */
package com.dynamobi.ws.domain;

//import javax.xml.rpc.holders.Holder;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.dynamobi.ws.domain.RolesDetails;
import com.dynamobi.ws.domain.UserPermsDetails;

/**
 * Holder for the list of roles details. (Not a strict holder.)
 * TODO: Rename this and supporting classes to show that what is meant
 * is users and roles permissions holder.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="roles")
public final class RolesDetailsHolder {
  public List<RolesDetails> value;
  public List<UserPermsDetails> value2;

  public RolesDetailsHolder() {
    value = new ArrayList<RolesDetails>();
    value2 = new ArrayList<UserPermsDetails>();
  }

  public RolesDetailsHolder(List<RolesDetails> value) {
    this.value = value;
  }

  // Auto-generated for AMF
  @XmlElement(name="role")
  public List<RolesDetails> getValue() { return value; }
  public void setValue(List<RolesDetails> value) { this.value = value; }

  @XmlElement(name="user")
  public List<UserPermsDetails> getValue2() { return value2; }
  public void setValue2(List<UserPermsDetails> value2) { this.value2 = value2; }

}

