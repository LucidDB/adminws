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
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import com.dynamobi.ws.domain.WrapperOptions;
import com.dynamobi.ws.domain.RemoteData;

import com.dynamobi.ws.util.AppException;

/**
 * Service for handling all the foreign data wrappers/services
 * transactions.
 *
 * @ WebMethod is for soap
 * @ GET is for REST
 * @ Path is for the REST service path
 * @ Consumes is for REST
 * 
 * @author Kevin Secretan
 * @since Sept 17, 2010
 */

@WebService(serviceName="ForeignDataService", name="ForeignDataService")

public interface ForeignDataService {

  /**
   * @param wrapper - Name of the foreign data wrapper.
   * @return Returns a list of all wrapper options (excluding extended)
   * for a given wrapper name.
   */
  @WebMethod
  @GET
  @Path("/wrappers/options/{wrapper}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<WrapperOptions> getWrapperOptions(
      @PathParam("wrapper") String wrapper) throws AppException;

  /**
   * @param wrapper - Name of the foreign data wrapper.
   * @return Returns a list of all wrapper options (including extended)
   * for a given wrapper name and driver class.
   */
  @WebMethod
  @GET
  @Path("/wrappers/options/extended/{wrapper}/{driver}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<WrapperOptions> getExtendedWrapperOptions(
      @PathParam("wrapper") String wrapper,
      @PathParam("driver") String driver) throws AppException;

  /**
   * @param server - Name of the server.
   * @return Returns a list of all wrapper options defined for an
   * existing server.
   */
  @WebMethod
  @GET
  @Path("/serverss/options/{server}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<WrapperOptions> getWrapperOptionsForServer(
      @PathParam("server") String server) throws AppException;

  /**
   * Allows for creation of a new foreign server with certain options.
   * @param server_name - Name of the new server.
   * @param wrapper_name - Wrapper this server is based on.
   * @param options - XML List of wrapper options to load into the server.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("servers/create/{name}/{wrapper}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  @Consumes ("application/xml")
  public String createServer(@PathParam("name") String server_name,
      @PathParam("wrapper") String wrapper_name,
      List<WrapperOptions> options) throws AppException;

  /**
   * Tests to see if a foreign server can be connected to.
   * @param server_name - Name of foreign server.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @GET
  @Path("/servers/test/{name}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String testServer(@PathParam("name") String server_name)
    throws AppException;

  /**
   * Used for retrieving a list of
   * foreign data from a foreign server for the user to import
   * locally.
   *
   * @param server_name - Name of the foreign data server.
   * @return Returns a RemoteData object which contains a list of schemas,
   * foreign objects, as well as which items are changed or deleted
   * compared to local imports (if any).
   */
  @WebMethod
  @GET
  @Path("/servers/get_data/{name}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public RemoteData getForeignData(@PathParam("name") String server_name)
    throws AppException;


  /**
   * Called to import a foreign schema into a local schema,
   * along with a list of table names from the foreign schema to
   * import. (An empty list implies all tables.)
   *
   * @param server - Name of the foreign server to import from.
   * @param from_schema - Name of the foreign schema to import from.
   * @param to_schema - Name of the local schema to import into.
   * @param tables - List of foreign table names to import.
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/servers/import/{server}/{from_schema}/{to_schema}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  @Consumes ("application/xml")
  public String importForeignSchema(@PathParam("server") String server,
      @PathParam("from_schema") String from_schema,
      @PathParam("to_schema") String to_schema,
      List<String> tables) throws AppException;



}

