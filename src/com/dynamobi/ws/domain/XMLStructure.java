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
import java.sql.*;
import javax.xml.bind.annotation.*;

/**
 * Currently only wraps a ddl statement generation,
 * I'm still determining if this is a useful generalization class
 * or not.
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
    result_builder = new StringBuffer("<" + root_node + "><![CDATA[\n");
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
    String stmt = rs.getString(1);
    result_builder.append(stmt + "\n");
  }

  public void finalize() {
    result_builder.append("]]></" + root + ">");
    result = result_builder.toString();
  }

  public XMLStructure copy() { return this; }

  @XmlAttribute
  public String getResult() { return result; }
  public void setResult(String result) { this.result = result; }

}
