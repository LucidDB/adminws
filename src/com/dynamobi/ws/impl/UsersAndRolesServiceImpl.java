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
package com.dynamobi.ws.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dynamobi.ws.api.UsersAndRolesService;
import com.dynamobi.ws.domain.UserDetails;
import com.dynamobi.ws.domain.SessionInfo;
import com.dynamobi.ws.domain.RolesDetails;
import com.dynamobi.ws.domain.UserPermsDetails;
import com.dynamobi.ws.domain.RolesDetailsHolder;
import com.dynamobi.ws.domain.PermissionsInfo;
import com.dynamobi.ws.domain.PermissionGroup;
import com.dynamobi.ws.domain.SubQuery;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DB;

/**
 * UsersAndRolesService implementation
 * 
 * @author Kevin Secretan
 * @since June 24, 2010
 */
@WebService(endpointInterface = "com.dynamobi.ws.api.UsersAndRolesService")
@Path("/users")
public class UsersAndRolesServiceImpl implements UsersAndRolesService {


  public List<UserDetails> getUsersDetails() throws AppException {
    List<UserDetails> details = new ArrayList<UserDetails>();
    UserDetails seed = new UserDetails();
    final String query = "SELECT name, password, creation_timestamp, "
      + "last_modified_timestamp FROM sys_root.dba_users u WHERE "
      + "u.name <> '_SYSTEM'";
    DB.execute(query, seed, details);
    return details;
  }

  public List<SessionInfo> getCurrentSessions() throws AppException {
    List<SessionInfo> retVal = new ArrayList<SessionInfo>();
    SessionInfo seed = new SessionInfo();
    final String query = DB.select("s.session_id, s.connect_url, " +
        "s.current_user_name, s2.sql_text", "sys_root.dba_sessions s " +
        "LEFT JOIN sys_root.dba_sql_statements s2 ON " +
        "s.session_id = s2.session_id");
    DB.execute(query, seed, retVal);
    return retVal;
  }

  private enum Action { NEW, MOD, DEL };
  private String userAction(String user, String password, Action act)
                 throws AppException {
    String query = "";
    if (act == Action.NEW) {
      query = DB.populate("CREATE USER {0,str} IDENTIFIED BY {1,lit}", user, password);
    } else if (act == Action.MOD) {
      query = DB.populate("CREATE OR REPLACE USER {0,str} IDENTIFIED BY {1,lit}", user, password);
    } else if (act == Action.DEL) {
      query = DB.populate("DROP USER {0,str}", user);
    }
    return DB.execute_success(query);
  }

  public String addNewUser(String user, String password) throws AppException {
    return userAction(user, password, Action.NEW);
  }

  // TODO: also adjust the roles when modifying and deleting.
  public String modifyUser(String user, String password) throws AppException {
    return userAction(user, password, Action.MOD);
  }

  public String deleteUser(String user) throws AppException {
    return userAction(user, "", Action.DEL);
  }

  public RolesDetailsHolder getRolesDetails() throws AppException {
    String query = "SELECT DISTINCT granted_catalog, granted_schema, granted_element, "
      + "grantee, grantor, action, "
      + "CASE WHEN r.inherited_role_name IS NOT NULL THEN r.inherited_role_name "
      + "WHEN grant_type = 'Role' THEN grantee END AS role_name, "
      + "grant_type, class_name, g.with_grant_option "
      + "FROM sys_root.dba_element_grants g LEFT OUTER JOIN sys_root.dba_inherited_roles r "
      + "ON action = 'INHERIT_ROLE' AND r.inherited_role_mof_id = element_mof_id "
      + "WHERE grantee NOT IN ('PUBLIC', '_SYSTEM') AND "
      + "grantor <> '_SYSTEM'";

      /* old where for roles only
      + "WHERE (grant_type = 'Role' AND grantee <> 'PUBLIC' "
      + "AND grantee <> '_SYSTEM') OR action = 'INHERIT_ROLE'";
      */
    RolesDetailsHolder rh = new RolesDetailsHolder();
    DB.execute(query, rh);

    RolesDetails rd = new RolesDetails(rh);
    query = DB.select("name", "sys_root.dba_roles", "name <> 'PUBLIC'");
    DB.execute(query, rd);

    return rh;
  }

  public String addNewRole(String role) throws AppException {
    String query = DB.populate("CREATE ROLE {0,str}", role);
    return DB.execute_success(query);
  }

  public String deleteRole(String role) throws AppException {
    String query = DB.populate("DROP ROLE {0,str}", role);
    return DB.execute_success(query);
  }

  public String userToRole(String user, String role, boolean added, boolean with_grant) throws AppException {
    String query = "";
    if (added) {
      query = DB.populate("GRANT ROLE {0,str} TO {1,id}", role, user);
      if (with_grant)
        query += " WITH GRANT OPTION";
    } else {
      // TODO: get ability to remove users from roles.
      // Note: apparently only one user can have grant option?
      return "Not Implemented: REVOKE";
    }
    return DB.execute_success(query);
  }

  public String grantPermissionsOnSchema(String catalog, String schema,
                String permissions, String grantee) throws AppException {
    String retval = "";
    /*String query = "CALL APPLIB.DO_FOR_ENTIRE_SCHEMA('GRANT " +
      permissions + " ON %TABLE_NAME% TO \"" + grantee + "\"', " +
      "'" + schema + "', 'TABLES_AND_VIEWS')";
      * cannot do above because do_for_entire_schema does not respect
      * catalogs.
      */
    String query = DB.select("table_name", "sys_root.dba_tables", DB.populate(
          "catalog_name = {0,lit} AND schema_name = {1,lit}", catalog, schema));
    SubQuery sq = new SubQuery(DB.populate(
          "GRANT {0,str} ON {1,id}.{2,id}.{3,str} TO " +
          "{4,id}", permissions, catalog, schema, "/{0,id}", grantee), 1);
    DB.execute(query, sq);
    if (sq.error)
      return sq.error_msg;
    return "";
  }

  public String grantPermissions(String catalog, String schema, String type,
                String element, String permissions, String grantee)
                throws AppException {
    String query = DB.populate(
        "GRANT {0,str} ON {1,id}.{2,id}.{3,id} TO {4,id}",
        permissions, catalog, schema, element, grantee);
    return DB.execute_success(query);
  }

  public String grantPermissionGroup(PermissionGroup group) throws AppException
  {
    // build query
    // success execute
    return DB.execute_success("");
  }

  public String revokePermissionsOnSchema(String catalog, String schema,
                String permissions, String grantee) throws AppException {
    return "Not Implemented";
  }

  public String revokePermissions(String catalog, String schema, String type,
                String element, String permissions, String grantee)
                throws AppException {
    return "Not Implemented";
  }

}
