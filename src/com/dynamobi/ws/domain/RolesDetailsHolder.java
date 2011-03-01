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
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.dynamobi.ws.domain.RolesDetails;
import com.dynamobi.ws.domain.UserPermsDetails;
import com.dynamobi.ws.domain.DBLoader;

import java.sql.*;

/**
 * Holder for the list of roles details. (Not a strict holder.)
 * TODO: Rename this and supporting classes to show that what is meant
 * is users and roles permissions holder.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="roles")
public final class RolesDetailsHolder extends DBLoader<RolesDetailsHolder> {
  public List<RolesDetails> value;
  public List<UserPermsDetails> value2;

  @XmlTransient
  public Map<String, RolesDetails> details;
  private Map<String, UserPermsDetails> user_details;
  private Map<String, PermissionsInfo> perms;

  private void init() {
    value = new ArrayList<RolesDetails>();
    value2 = new ArrayList<UserPermsDetails>();
    details = new LinkedHashMap<String, RolesDetails>();
    user_details = new LinkedHashMap<String, UserPermsDetails>();
    perms = new LinkedHashMap<String, PermissionsInfo>();
  }

  public RolesDetailsHolder() {
    init();
  }

  public RolesDetailsHolder(List<RolesDetails> value) {
    init();
    this.value = value;
  }

  public void loadRow(ResultSet rs) throws SQLException {
    int col = 1;
    final String catalog = rs.getString(col++);
    final String schema = rs.getString(col++);
    final String element = rs.getString(col++);
    final String grantee = rs.getString(col++);
    final String grantor = rs.getString(col++);
    final String action = rs.getString(col++);
    final String role_name = rs.getString(col++);
    final String grant_type = rs.getString(col++);
    final String class_name = rs.getString(col++);
    final boolean with_grant = rs.getBoolean(col++);

    final String key = grantee + catalog + schema + element + class_name;
    PermissionsInfo p;
    if (!perms.containsKey(key)) {
      p = new PermissionsInfo();
      p.catalog_name = catalog;
      p.schema_name = schema;
      p.item_name = element;
      p.item_type = class_name;
      perms.put(key, p);
    } else {
      p = perms.get(key);
    }

    if (!action.equals("INHERIT_ROLE"))
      p.actions.add(action);

    if (role_name != null) {
      // Role permission
      RolesDetails rd;
      if (!details.containsKey(role_name)) {
        rd = new RolesDetails();
        rd.name = role_name;
        details.put(role_name, rd);
      } else {
        rd = details.get(role_name);
      }

      // Dealing with a users or permissions entry?
      if (grant_type.equals("User")) {
        rd.users.add(grantee);
        if (with_grant) {
          rd.users_with_grant_option.add(grantee);
        }
      } else if (grant_type.equals("Role")) {
        rd.permissions.add(p);
      } else {
        throw new SQLException("Unknown grant type");
      }

    } else {
      // User permission
      UserPermsDetails upd;
      if (!user_details.containsKey(grantee)) {
        upd = new UserPermsDetails();
        upd.name = grantee;
        user_details.put(grantee, upd);
      } else {
        upd = user_details.get(grantee);
      }
      upd.permissions.add(p);
    }

  }

  public void finalize() {
    value.addAll(details.values());
    value2.addAll(user_details.values());
  }

  public RolesDetailsHolder copy() { return this; }

  // Auto-generated for AMF
  @XmlElement(name="role")
  public List<RolesDetails> getValue() { return value; }
  public void setValue(List<RolesDetails> value) { this.value = value; }

  @XmlElement(name="user")
  public List<UserPermsDetails> getValue2() { return value2; }
  public void setValue2(List<UserPermsDetails> value2) { this.value2 = value2; }

}

