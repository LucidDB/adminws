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
import com.dynamobi.ws.domain.SessionInfo;
import com.dynamobi.ws.domain.RolesDetails;
import com.dynamobi.ws.domain.RolesDetailsHolder;
import com.dynamobi.ws.domain.PermissionsInfo;
import com.dynamobi.ws.util.AppException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * Interface: Service for creating/editing/viewing users and roles.
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

  @WebMethod
  @GET
  @Path("/")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<UserDetails> getUsersDetails() throws AppException;

  @WebMethod
  @GET
  @Path("/sessions")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<SessionInfo> getCurrentSessions() throws AppException;

  @WebMethod
  @POST
  @Path("/add/{user}/{password}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String addNewUser(@PathParam("user") String user,
               @PathParam("password") String password) throws AppException;

  @WebMethod
  @POST
  @Path("/modify/{user}/{password}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String modifyUser(@PathParam("user") String user,
               @PathParam("password") String password) throws AppException;

  @WebMethod
  @POST
  @Path("/delete/{user}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String deleteUser(@PathParam("user") String user)
                 throws AppException;

  @WebMethod
  @GET
  @Path("/roles")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public RolesDetailsHolder getRolesDetails() throws AppException;

  @WebMethod
  @POST
  @Path("/roles/add/{role}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String addNewRole(@PathParam("role") String role) throws AppException;

  @WebMethod
  @POST
  @Path("/roles/delete/{role}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String deleteRole(@PathParam("role") String role) throws AppException;

  @WebMethod
  @POST
  @Path("/torole/{user}/{role}/{added}/{with_grant}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String userToRole(@PathParam("user") String user,
      @PathParam("role") String role,
      @PathParam("added") boolean added,
      @PathParam("with_grant") boolean with_grant) throws AppException;

  /** 
   * Grant permissions for everything in the given schema;
   * This and all others expect permissions as a comma-separated string
   * of all the permissions.
   */
  @WebMethod
  @POST
  @Path("/roles/grant/{catalog}/{schema}/{permissions}/{grantee}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String grantPermissionsOnSchema(@PathParam("catalog") String catalog,
                @PathParam("schema") String schema,
                @PathParam("permissions") String permissions,
                @PathParam("grantee") String grantee)
    throws AppException;

  /** Grant permissions for a specific element like a table or view */
  @WebMethod
  @POST
  @Path("/roles/grant/{catalog}/{schema}/{type}/{element}/{permissions}/{grantee}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String grantPermissions(@PathParam("catalog") String catalog,
                @PathParam("schema") String schema,
                @PathParam("type") String type,
                @PathParam("element") String element,
                @PathParam("permissions") String permissions,
                @PathParam("grantee") String grantee)
    throws AppException;

  /** Revoke permissions for everything in the given schema */
  @WebMethod
  @POST
  @Path("/roles/revoke/{catalog}/{schema}/{permissions}/{grantee}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String revokePermissionsOnSchema(@PathParam("catalog") String catalog,
                @PathParam("schema") String schema,
                @PathParam("permissions") String permissions,
                @PathParam("grantee") String grantee)
    throws AppException;

  /** Revoke permissions for a specific element like a table or view */
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
