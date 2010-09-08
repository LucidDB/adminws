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

import javax.jws.WebService;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * The implementor is responsible for executing generic queries
 * along with returning XML for the ObjectTree.
 *
 * @author Ray Zhang
 * @since Feb 04,2010
 *
 * @author Kevin Secretan
 * @since June 15, 2010
 * 
 */
@WebService
@PermitAll
public interface FlexSQLAdmin
{
    /**
     * @param connection
     * @param catalog
     * @return
     */
    @RolesAllowed( {"Admin","Authenticated"} )
    public String getDBMetaData(String connection, String catalog);
    /**
     * exec SQL
     * @param connection
     * @param sqlquerytype
     * @param sql
     * @param toomany
     * @return 
     */
    @RolesAllowed( {"Admin","Authenticated"} )
    public String execSQL(String connection, String sqlquerytype, String sql, int toomany);
    
    /**
     * update data
     * @param connection
     * @param testsql
     * @param sql
     * @param toomany
     * @return
     */
    @RolesAllowed( {"Admin","Authenticated"} )
    public String handleUpdate(String connection, String testsql, String sql, int toomany);


    @RolesAllowed( {"Admin","Authenticated"} )
    public String getUsers();
    @RolesAllowed( {"Admin","Authenticated"} )
    public String getRoles();

    @RolesAllowed( {"Admin","Authenticated"} )
    public String getForeignTables(String schema);

    @RolesAllowed( {"Admin","Authenticated"} )
    public String getFunctions(String schema);

    @RolesAllowed( {"Admin","Authenticated"} )
    public String getProcedures(String schema);

    @RolesAllowed( {"Admin", "Authenticated"} )
    public String getRemoteData();
}
