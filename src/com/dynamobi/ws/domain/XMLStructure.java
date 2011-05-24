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
package com.dynamobi.ws.domain;

import com.dynamobi.ws.domain.DBLoader;
import com.dynamobi.ws.util.DB;
import java.sql.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Useful structure for wrapping up some db values
 * into an XML or JSON string the client can use.
 * Mainly used for the Object Tree.
 * It makes it easy to return lists of data.
 * @see com.dynamobi.ws.api.AutoIndexService.getCandidatesJson
 * and
 * @see com.dynamobi.ws.api.FlexSQLAdmin.getUsersJson()
 *
 * @author Kevin Secretan
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="result")
public class XMLStructure extends DBLoader<XMLStructure> {

  public static enum Mode {XML, JSON};
  public Mode mode;

  public String result;

  public ArrayList<String> row_data;

  private StringBuffer result_builder;

  private String root, child;

  private void init(String root_node, String child_node, Mode md) {
    mode = md;
    root = root_node;
    child = child_node;
    if (mode == Mode.XML) {
      result_builder = new StringBuffer("<" + root_node + ">\n");
      if (child_node.equals(""))
        result_builder.append("<![CDATA[\n");
    } else {
      result_builder = new StringBuffer("{\"" + root_node + "\": [  \n");
    }
    row_data = new ArrayList<String>();
  }

  public XMLStructure() {
    init("result", "", Mode.XML);
  }

  public XMLStructure(String root_node) {
    init(root_node, "", Mode.XML);
  }

  /**
   * @param root_node - defines the name of the root XML node
   * @param child_node - defines the name of the child node along with any
   *                     following attributes separated by spaces.
   */
  public XMLStructure(String root_node, String child_node) {
    init(root_node, child_node, Mode.XML);
  }

  /**
   * Mainly a plug for JSON structures.
   */
  public XMLStructure(String root_node, Mode md) {
    init(root_node, "", md);
  }

  public XMLStructure(String root_node, String child_node, Mode md) {
    init(root_node, child_node, md);
  }

  public void loadRow(ResultSet rs) throws SQLException {
    if (child.equals("")) {
      String stmt = rs.getString(1);
      if (mode == Mode.JSON)
        stmt = "\"" + stmt.replaceAll("\"", "\\\"") + "\",";
      result_builder.append(stmt + "\n");
      row_data.add(stmt);
    } else {
      String[] child_parts = child.split(" ");
      String node = child_parts[0];
      if (mode == Mode.XML)
        result_builder.append("<" + node + " ");
      else
        result_builder.append("{ ");
      int c = 0;
      for (int i = 1; i < child_parts.length; ++i) {
        String val = rs.getString(++c);
        val = (val == null) ? "" : val.replaceAll("\"", "&quot;");
        if (mode == Mode.XML) {
          result_builder.append(
              DB.populate("{0,str}={1,id} ", child_parts[i], val));
        } else {
          if (i > 1) {
            result_builder.append(", ");
          }
          result_builder.append("\"" + child_parts[i].replaceAll("\"", "&quot;")
              + "\" : \"" + val + "\"");
        }
        row_data.add(val);
      }
      if (mode == Mode.XML)
        result_builder.append("/>\n");
      else
        result_builder.append("},\n");
    }
  }

  public void finalize() {
    if (mode == Mode.XML) {
      if (child.equals(""))
        result_builder.append("]]>");
      result_builder.append("</" + root + ">");
    } else {
      // kill last comma
      result_builder.setCharAt(result_builder.length()-2, '\n');
      result_builder.append("]\n}");
    }
    result = result_builder.toString();
  }

  public XMLStructure copy() { return this; }

  @XmlAttribute
  public String getResult() { return result; }
  public void setResult(String result) { this.result = result; }

}
