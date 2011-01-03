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

/**
 * Useful structure for wrapping up some db values
 * into an XML string the client can use.
 * Mainly used for the Object Tree.
 *
 * @author Kevin Secretan
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="result")
public class XMLStructure extends DBLoader<XMLStructure> {

  public String result;

  private StringBuffer result_builder;

  private String root, child;

  private void init(String root_node, String child_node) {
    root = root_node;
    child = child_node;
    result_builder = new StringBuffer("<" + root_node + ">\n");
    if (child_node.equals(""))
      result_builder.append("<![CDATA[\n");
  }

  public XMLStructure() {
    init("result", "");
  }

  public XMLStructure(String root_node) {
    init(root_node, "");
  }

  public XMLStructure(String root_node, String child_node) {
    init(root_node, child_node);
  }

  public void loadRow(ResultSet rs) throws SQLException {
    if (child.equals("")) {
      String stmt = rs.getString(1);
      result_builder.append(stmt + "\n");
    } else {
      String[] child_parts = child.split(" ");
      String node = child_parts[0];
      result_builder.append("<" + node + " ");
      int c = 0;
      for (int i = 1; i < child_parts.length; ++i) {
        String val = rs.getString(++c);
        val = (val == null) ? "" : val.replaceAll("\"", "&quot;");
        result_builder.append(
            DB.populate("{0,str}={1,id} ", child_parts[i], val));
      }
      result_builder.append("/>\n");
    }
  }

  public void finalize() {
    if (child.equals(""))
      result_builder.append("]]>");
    result_builder.append("</" + root + ">");
    result = result_builder.toString();
  }

  public XMLStructure copy() { return this; }

  @XmlAttribute
  public String getResult() { return result; }
  public void setResult(String result) { this.result = result; }

}
