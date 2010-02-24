package com.dynamobi.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBElement;

import com.dynamobi.ws.api.DBService;
import com.dynamobi.ws.domain.Catalog;
import com.dynamobi.ws.domain.Column;
import com.dynamobi.ws.domain.Schema;
import com.dynamobi.ws.domain.Table;
import com.dynamobi.ws.domain.TableDetails;
import com.dynamobi.ws.domain.TablesInfo;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DBAccess;

/**
 * DBService implementation
 * 
 * @author Ray Zhang
 * @since Jan-12-2010
 */
@Path("/metadata")
@WebService (
    endpointInterface = "com.dynamobi.ws.api.DBService"
  )
public class DBServiceImp
    implements DBService
{

    public TablesInfo readTablesInfo()
        throws AppException
    {

    	
    	return DBAccess.getTablesInfo();

        //List<Table> ret = DBAccess.getTableInfo();

        //return ret;

    }

    public List<Table> readTablesInfoBySchema(String schemaName)
        throws AppException
    {

        List<Table> ret = DBAccess.getTableInfo(schemaName);

        return ret;

    }

    public List<Column> readColumnNamesFromTable(String tableName,

    String schemaName)
        throws AppException
    {

        List<Column> ret = DBAccess.getColumnNamesFromTable(
            tableName,
            schemaName);

        return ret;

    }
    
    public TableDetails getTableDetails(String catalog, String schema, String table) 
    throws AppException
    {
    	TableDetails ret = DBAccess.getTableDetails(catalog, schema, table);
    	
    	return ret;
    }

	public Schema getSchema(String catalogName, String schemaName) throws AppException {
		return DBAccess.getSchemaByName(catalogName, schemaName);
	}

	public Schema putSchema(String catalogName, 
			Schema schema) throws AppException {
		// TODO Auto-generated method stub
		Schema s = new Schema();
		s.name = schema.name;
		s.uuid = s.uuid;
		if ( s.name == "NEWTABLE" ) {
			throw new AppException("blah");
			
		}
		return s;
	}


	
	

}
