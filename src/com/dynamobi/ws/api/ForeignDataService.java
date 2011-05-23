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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import javax.annotation.security.RolesAllowed;

import com.dynamobi.ws.domain.ForeignServer;
import com.dynamobi.ws.domain.ForeignTable;
import com.dynamobi.ws.domain.ForeignTables;
import com.dynamobi.ws.domain.Wrapper;
import com.dynamobi.ws.domain.WrapperOptions;
import com.dynamobi.ws.domain.RemoteData;

import com.dynamobi.ws.util.AppException;
import com.sun.jersey.api.view.ImplicitProduces;

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
   * @param wrapper - Name of the foreign wrapper to list Servers
   * @return Returns a list of all servers for a given wrapper   
   */
  @WebMethod
  @GET
  @Path("/wrappers")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<Wrapper> getWrappers() throws AppException;
  
  /**
   * @param wrapper - Name of the foreign data wrapper.
   * @return Returns a list of all servers for that wrapper
   */
  @WebMethod
  @GET
  @Path("/servers/getByWrapper/{wrapper}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<ForeignServer> getForeignServers(
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
  @Path("/servers/options/{server}")
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
   * @param tables - List of foreign table names to import
   * @param copy_local - Whether to create local warehouses or not--send "true" or "false"
   * @return Returns an empty string on success, otherwise an SQL error message.
   */
  @WebMethod
  @POST
  @Path("/servers/import/{server}/{from_schema}/{to_schema}/{copy_local}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  @Consumes ("application/xml")
  public String importForeignSchema(@PathParam("server") String server,
      @PathParam("from_schema") String from_schema,
      @PathParam("to_schema") String to_schema,
      List<String> tables, @PathParam("copy_local") String copy_local)
      throws AppException;

  /**
   * Called to physically copy the data at a foreign table into a local
   * LucidDB table. This is done when creating a data warehouse.
   *
   * @param catalog - catalog of foreign table
   * @param from_schema - schema of foreign table
   * @param from_table - foreign table name
   * @param to_schema - schema to copy to
   * @param to_table - table to copy into (will create if it doesn't exist)
   */
  @WebMethod
  @POST
  @Path("/tables/copy/{catalog}/{from_schema}/{from_table}/{to_schema}/{to_table}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public String copyForeignTable(@PathParam("catalog") String catalog,
      @PathParam("from_schema") String from_schema,
      @PathParam("from_table") String from_table,
      @PathParam("to_schema") String to_schema,
      @PathParam("to_table") String to_table) throws AppException;
  
  /**
   * @param catalog Catalog the schema is in
   * @param schema The schema name to create the foreign table in.
   */
  @WebMethod
  @POST
  @Path("/table")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public ForeignTable createForeignTable(ForeignTable foreignTable) throws AppException;
  
  /**

   */
  @WebMethod
  @GET
  @Path("/table")
  @RolesAllowed( {"Admin", "Authenticated"} )
  @Produces("application/xml")
  public  ForeignTables listForeignTables(
		  @QueryParam(value ="catalog") @DefaultValue("LOCALDB") String catalog,
		  @QueryParam(value ="schema") String schema) throws AppException;
  

  
  


}

