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
package com.dynamobi.ws.api;

import java.util.List;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.dynamobi.ws.domain.UserDetails;
import com.dynamobi.ws.domain.UserDetailsHolder;
import com.dynamobi.ws.domain.SessionInfo;
import com.dynamobi.ws.domain.SessionInfoHolder;
import com.dynamobi.ws.domain.RolesDetails;
import com.dynamobi.ws.domain.RolesDetailsHolder;
import com.dynamobi.ws.domain.PermissionGroup;
import com.dynamobi.ws.util.AppException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * Service for creating/editing/viewing users and roles.
 *
 * @ WebMethod is for soap
 * @ GET is for REST
 * @ Path is for the REST service path
 * @ Consumes is for REST
 * 
 * @author Kevin Secretan
 * @since June 24, 2010
 */

@WebService(serviceName="UsersAndRolesService", name="UsersAndRolesService")
// optional: targetNameSpace = "http://url.com"

public interface UsersAndRolesService {

  /**
   * @return Returns a list of details about users in the system including
   * the name, encrypted password, creation time, and modification time.
   */
  @WebMethod
  @GET
  @Path("/")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public UserDetailsHolder getUsersDetails() throws AppException;

  /**
   * @return Returns a list of currently active user sessions along with any
   * currenty executing queries on those sessions.
   */
  @WebMethod
  @GET
  @Path("/sessions")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public SessionInfoHolder getCurrentSessions() throws AppException;

  /**
   * @param user - Name of the user.
   * @param password - User's password.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/add/{user}/{password}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String addNewUser(@PathParam("user") String user,
               @PathParam("password") String password) throws AppException;

  /**
   * Changes a user's password.
   * @param user - Name of the user.
   * @param password - New password.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/modify/{user}/{password}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String modifyUser(@PathParam("user") String user,
               @PathParam("password") String password) throws AppException;

  /**
   * @param user - Name of the user to delete.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/delete/{user}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String deleteUser(@PathParam("user") String user)
                 throws AppException;

  /**
   * @return Returns a list of users, roles, and any permissions associated
   * with each individually.
   */
  @WebMethod
  @GET
  @Path("/roles")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public RolesDetailsHolder getRolesDetails() throws AppException;

  /**
   * @param role - Name of the role to add.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/roles/add/{role}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String addNewRole(@PathParam("role") String role) throws AppException;

  /**
   * @param role - Name of the role to delete.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/roles/delete/{role}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String deleteRole(@PathParam("role") String role) throws AppException;

  /**
   * Grants a role to a user. In the future will also support removing
   * a role from a user.
   * @param user - User to whom the change is applied.
   * @param role - Role being applied to the user.
   * @param added - True if granting, false if revoking.
   * @param with_grant - True if attempting to grant "WITH GRANT" option.
   * @return Returns an empty string on success, otherwise either a 
   * message indicating REVOKE is not implemented or an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/torole/{user}/{role}/{added}/{with_grant}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String userToRole(@PathParam("user") String user,
      @PathParam("role") String role,
      @PathParam("added") boolean added,
      @PathParam("with_grant") boolean with_grant) throws AppException;

  /**
   * Allows permission granting on a group of objects, making one DB query
   * for each object. This allows for batch-sending a bunch of permission
   * changes.
   * @param group - List of PermissionGroup objects to apply.
   * @return Empty string on success, otherwise error messages.
   */
  @WebMethod
  @POST
  @Path("/roles/grant")
  @RolesAllowed( {"Admin", "Authenticated"} )
  @Consumes ("application/xml")
  public String grantPermissionGroup(List<PermissionGroup> group) throws AppException;

  /**
   * This is a dummy method to get AMF to compile.
   * @return Returns an empty array list.
   */
  @WebMethod
  @GET
  @Path("/roles/grant/fakeout")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<PermissionGroup> getEmptyGroup() throws AppException;

  /**
   * (NOT IMPLEMENTED)
   * (DEPRECATED)
   * Revoke permissions for everything in the given schema
   * @param catalog - DB catalog of the schema.
   * @param schema - DB schema to revoke permissions.
   * @param permissions - Comma-separated list of permissions.
   * @param grantee - The user or role revoked of the permissions.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/roles/revoke/{catalog}/{schema}/{permissions}/{grantee}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String revokePermissionsOnSchema(@PathParam("catalog") String catalog,
                @PathParam("schema") String schema,
                @PathParam("permissions") String permissions,
                @PathParam("grantee") String grantee)
    throws AppException;

  /**
   * (NOT IMPLEMENTED)
   * (DEPRECATED)
   * Revoke permissions for a specific element like a table or view
   * @param catalog - DB catalog of the element.
   * @param schema - DB schema of the element.
   * @param type - Unused. Future uses may include per-column permissions.
   * @param element - DB element, such as a table, view, function, etc.
   * @param permissions - Comma-separated list of permissions to revoke.
   * @param grantee - User or role revoked of the permissions.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/roles/revoke/{catalog}/{schema}/{type}/{element}/{permissions}/{grantee}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String revokePermissions(@PathParam("catalog") String catalog,
                @PathParam("schema") String schema,
                @PathParam("type") String type,
                @PathParam("element") String element,
                @PathParam("permissions") String permissions,
                @PathParam("grantee") String grantee)
    throws AppException;

}
