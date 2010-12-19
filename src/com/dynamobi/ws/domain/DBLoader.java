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

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="result")
public abstract class DBLoader {
  public boolean error = false;
  public String error_msg = "";

  abstract public void loadRow(ResultSet rs) throws SQLException;

  abstract public void finalize();

  public void exception(Exception e) {
    error = true;
    error_msg = e.getMessage();
    e.printStackTrace();
  }

  @XmlAttribute
  public boolean getError() { return error; }
  public void setError(boolean error) { this.error = error; }

  @XmlAttribute
  public String getErrorMsg() { return error_msg; }
  public void setErrorMsg(String error_msg) { this.error_msg = error_msg; }
}
