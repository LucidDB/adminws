/*
Dynamo Web Services is a web service project for administering LucidDB
Copyright (C) 2011 Dynamo Business Intelligence Corporation

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

import javax.jws.WebService;
import javax.ws.rs.Path;

import com.dynamobi.ws.api.AutoIndexService;
import com.dynamobi.ws.util.DB;
import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.domain.XMLStructure;

import java.sql.*;

/**
 * Implementation of the AutoIndex interface.
 * 
 * @author Kevin Secretan
 */

@Path("/indexes")
@WebService(endpointInterface = "com.dynamobi.ws.api.AutoIndexService")
public class AutoIndexServiceImpl
    implements AutoIndexService
{

  private String get_show_query(String cat, String schema, String table,
      int threshold) {
    return DB.populate(
          "table(applib.show_idx_candidates({0,lit},{1,lit},{2,lit},{3,str}))"
          , cat, schema, table, Integer.toString(threshold));
  }

  private XMLStructure getCandidatesGen(String cat, String schema, String table,
      int threshold, XMLStructure ds) throws AppException {
    final String query = DB.select("column_name",
        get_show_query(cat, schema, table, threshold));
    DB.execute(query, ds);
    return ds;
  }

  public XMLStructure getCandidates(String cat, String schema, String table,
      int threshold) throws AppException {
    XMLStructure ds = new XMLStructure("indexes", "column column_name");
    return getCandidatesGen(cat, schema, table, threshold, ds);
  }

  public XMLStructure getCandidatesJson(String cat, String schema, String table,
      int threshold) throws AppException {
    XMLStructure ds = new XMLStructure("indexes", XMLStructure.Mode.JSON);
    return getCandidatesGen(cat, schema, table, threshold, ds);
  }

  public String createIndexes(String cat, String schema, String table,
      int threshold) throws AppException {
    final String query = DB.populate("CALL applib.create_indexes({0,lit})",
        DB.select("*", get_show_query(cat, schema, table, threshold)));
    return DB.execute_success(query);
  }

}
