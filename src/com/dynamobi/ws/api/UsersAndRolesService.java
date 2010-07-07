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
  @POST
  @Path("/roles")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<RolesDetails> getRolesDetails() throws AppException;

  @WebMethod
  @POST
  @Path("/roles/add/{role}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String addNewRole(@PathParam("role") String role) throws AppException;

  /*@WebMethod
  @POST
  @Path("/roles")
  @RolesAllowed( {"Admin", "Authenticated"} )
  @Consumes ("application/xml")
  public boolean modifyUsers(List<UserDetails> details) throws AppException;
  */

}
