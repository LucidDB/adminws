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
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;
import java.util.ArrayList;

import com.dynamobi.ws.domain.PermissionsInfo;

/**
 * Holder for the roles xml tree.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="role")
public class RolesDetails {

  public String name;
  public List<String> users;
  public List<String> users_with_grant_option;
  public List<PermissionsInfo> permissions;

  public RolesDetails() {
    users = new ArrayList<String>();
    users_with_grant_option = new ArrayList<String>();
    permissions = new ArrayList<PermissionsInfo>();
  }

  public String toString() {
    return
     "\nname: " + name +
     "\nusers: " + users +
     "\nusers with grant: " + users_with_grant_option + 
     "\npermissions: " + permissions;
  }

  // Auto-generated for AMF
  @XmlAttribute
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @XmlElement
  @XmlList public List<String> getUsers() { return users; }
  public void setUsers(List<String> users) { this.users = users; }

  @XmlElement
  @XmlList public List<String> getUsers_with_grant_option() { return users_with_grant_option; }
  public void setUsers_with_grant_option(List<String> users_with_grant_option) { this.users_with_grant_option = users_with_grant_option; }

  @XmlElement(name="permission")
  public List<PermissionsInfo> getPermissions() { return permissions; }
  public void setPermissions(List<PermissionsInfo> permissions) { this.permissions = permissions; }

}

