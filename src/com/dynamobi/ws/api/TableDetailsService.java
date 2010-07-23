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

import com.dynamobi.ws.domain.Schema;
import com.dynamobi.ws.domain.Table;
import com.dynamobi.ws.domain.TableDetails;
import com.dynamobi.ws.domain.TablesInfo;
import com.dynamobi.ws.util.AppException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * Interface: Service for creating/editing and retrieving table data.
 *
 * @ WebMethod is for soap
 * @ GET is for REST
 * @ Path is for the REST service path
 * @ Consumes is for REST
 * 
 * @author Kevin Secretan
 * @since June 17, 2010
 */

@WebService(serviceName="TableDetailsService", name="TableDetailsService")
// optional: targetNameSpace = "http://url.com"

public interface TableDetailsService {

  @WebMethod
  @GET
  @Path("/")
  public TablesInfo readTablesInfo() throws AppException;

  @WebMethod
  @GET
  @Path("/{catalog}/{schema}")
  public Schema getSchema(@PathParam("catalog") String catalogName,
      @PathParam("schema") String schemaName) throws AppException;

  @WebMethod
  @POST
  @Path("/{catalog}")
  @Consumes ("application/xml")
  public Schema putSchema(@PathParam("catalog") String catalogName,
      Schema schema) throws AppException;


  @WebMethod
  @GET
  @Path("/{catalog}/{schema}/{table}")
  @RolesAllowed( {"Admin","Authenticated"} )
  public TableDetails getTableDetails(@PathParam("catalog") String catalog,
      @PathParam("schema") String schema,
      @PathParam("table") String table) throws AppException;

  @WebMethod
  @POST
  @Path("/{catalog}/{schema}/{table}")
  @RolesAllowed( {"Admin","Authenticated"} )
  @Consumes ("application/xml")
    public boolean postTableDetails(@PathParam("catalog") String catalog,
        @PathParam("schema") String schema,
        @PathParam("table") String table, TableDetails details);

}
