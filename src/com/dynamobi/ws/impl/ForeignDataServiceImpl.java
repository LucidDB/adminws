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
package com.dynamobi.ws.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dynamobi.ws.api.ForeignDataService;
import com.dynamobi.ws.domain.WrapperOptions;

import com.dynamobi.ws.util.AppException;
import com.dynamobi.ws.util.DBAccess;

/**
 * ForeignDataService implementation
 * 
 * @author Kevin Secretan
 * @since Sept 17, 2010
 */
@WebService(endpointInterface = "com.dynamobi.ws.api.ForeignDataService")
@Path("/foreign_data")
public class ForeignDataServiceImpl implements ForeignDataService {

  public List<WrapperOptions> getWrapperOptions(String wrapper)
    throws AppException {
      List<WrapperOptions> options_list = new ArrayList<WrapperOptions>();

      try {
        final String query = "select distinct " +
          "option_ordinal,  " +
          "option_name, " +
          "option_description, " +
          "is_option_required, " +
          "option_choice_value as option_default_value, " +
          "case  " +
          "  when option_choice_value = 'TRUE' OR option_choice_value = 'FALSE' then 'BOOLEAN' " +
          "  else 'TEXT' " +
          "end as option_type, " +
          "option_choice_ordinal " +
          "from " +
          "  table(sys_boot.mgmt.browse_connect_foreign_server('" + wrapper + "', " +
          "    cursor(select '' as option_name, '' as option_value " +
          "      from sys_boot.jdbc_metadata.empty_view))) " +
          "where option_choice_ordinal = -1 " +
          "order by option_ordinal ";

        ResultSet rs = DBAccess.rawResultExec(query);
        while (rs.next()) {
          int c = 0;
          int ordinal = rs.getInt(++c);
          String name = rs.getString(++c);
          String desc = rs.getString(++c);
          boolean req = rs.getBoolean(++c);
          String default_val = rs.getString(++c);
          String type = rs.getString(++c);

          WrapperOptions options = new WrapperOptions();
          options.ordinal = ordinal;
          options.name = name;
          options.desc = desc;
          options.required = req;
          options.value = default_val;
          if (options.value == null)
            options.value = "";
          options.type = type;

          options_list.add(options);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return options_list;
  }

  public String createServer(String server_name, String wrapper_name,
      List<WrapperOptions> options)
    throws AppException {

      String retval = "";
      try {
        String query = "CREATE SERVER " + server_name +
          " FOREIGN DATA WRAPPER " + wrapper_name +
          " OPTIONS ( ";
        for (WrapperOptions option : options) {
          query += option.name + " '" + option.value + "', ";
        }
        int last_ind = query.lastIndexOf(", ");
        if (last_ind != -1)
          query = query.substring(0, last_ind);
        query += ")";
        System.out.println(query);

        ResultSet rs = DBAccess.rawResultExec(query);
      } catch (SQLException e) {
        e.printStackTrace();
        retval = e.getMessage();
      }
      return retval;
  }

}

