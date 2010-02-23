package com.dynamobi.api;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.dynamobi.domain.Schema;
import com.dynamobi.domain.Table;
import com.dynamobi.domain.TableDetails;
import com.dynamobi.domain.TablesInfo;
import com.dynamobi.util.AppException;

/**
 * Interface: Get tables' info from system views.
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */
@WebService
public interface DBService
{

    @GET
    public TablesInfo readTablesInfo()
        throws AppException;

   /* @Path("/{schema}")
    @GET
    public List<Table> readTablesInfoBySchema(@PathParam("schema")
    String schemaName)
        throws AppException;*/
    
    @Path("/{catalog}/{schema}")
    @GET
    public Schema getSchema(@PathParam("catalog") String catalogName, @PathParam("schema") String schemaName) throws AppException;
    
    @Path("/{catalog}")
    @POST
    @Consumes ("application/xml")
    public Schema putSchema(@PathParam("catalog") String catalogName, Schema schema) throws AppException;
    

    @Path("/{catalog}/{schema}/{table}")
    @GET
    public TableDetails getTableDetails(@PathParam("catalog") String catalogName, @PathParam("schema")String schema,
    		@PathParam("table") String table) throws AppException;

}
