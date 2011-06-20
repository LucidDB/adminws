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
package com.dynamobi.ws.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * Convert resultSet to XML
 * @author Ray Zhang
 * @since Feb 05,2010
 *
 */
public class JDBCUtil
{

    private JDBCUtil()
    {

    }

    public static String resultSetToXML(ResultSet rs) throws SQLException
    {

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        StringBuffer ret = new StringBuffer();       
        
        while (rs.next()) {
            ret.append("<Table>");  
            for (int i = 1; i <= colCount; i++) {
                String columnName = rsmd.getColumnName(i).replaceAll(" ", "&nbsp;");
                String value = rs.getString(i);
                if (value != null && (value.indexOf("<") != -1 || value.indexOf(">") != -1)) {
                  value = "<![CDATA[" + value + "]]>";
                }
                ret.append("<"+columnName+">"+ value);
                ret.append("</"+columnName+">");
            }
            ret.append("</Table>");  
        }
        return ret.toString();
        
    }

}
