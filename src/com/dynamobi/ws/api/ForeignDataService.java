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

import com.dynamobi.ws.util.AppException;

/**
 * Interface: Service for handling all the foreign data wrappers/services
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

  @WebMethod
  @GET
  @Path("/wrappers/options/{wrapper}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  public List<WrapperOptions> getWrapperOptions(
      @PathParam("wrapper") String wrapper) throws AppException;

  @WebMethod
  @POST
  @Path("servers/create/{name}/{wrapper}")
  @RolesAllowed( {"Admin", "Authenticated"} )
  @Consumes ("application/xml")
  public String createServer(@PathParam("name") String server_name,
      @PathParam("wrapper") String wrapper_name,
      List<WrapperOptions> options) throws AppException;

}

