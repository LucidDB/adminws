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

import javax.xml.bind.annotation.XmlRootElement;
import com.dynamobi.ws.domain.DBLoader;
import java.sql.*;

@XmlRootElement
public class Counter extends DBLoader<Counter>
{
    private String counterCategory;

    private String counterSubcategory;

    private String sourceName;
    
    private String counterName;
    
    private String counterUnits;
    
    private String counterValue;

    public boolean list_mode = false;
    private Counter copy;

    public String getCounterCategory() {
      return (counterCategory == null) ? "" : counterCategory;
    }

    public void setCounterCategory(String counterCategory) {
      this.counterCategory = counterCategory;
    }

    public String getCounterSubcategory() {
      return (counterSubcategory == null) ? "" : counterSubcategory;
    }

    public void setCounterSubcategory(String counterSubcategory) {
      this.counterSubcategory = counterSubcategory;
    }

    public String getCounterName()
    {
        return counterName;
    }

    public void setCounterName(String counterName)
    {
        this.counterName = counterName;
    }

    public String getCounterUnits()
    {
      return (counterUnits == null) ? "" : counterUnits;
    }

    public void setCounterUnits(String counterUnits)
    {
        this.counterUnits = counterUnits;
    }

    public String getCounterValue()
    {
        return counterValue;
    }

    public void setCounterValue(String counterValue)
    {
        this.counterValue = counterValue;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }
    
    
    public void loadRow(ResultSet rs) throws SQLException {
      if (!list_mode) {
        setSourceName(rs.getString(1));
        setCounterName(rs.getString(2));
        setCounterUnits(rs.getString(3));
        setCounterValue(rs.getString(4));
      } else {
        Counter en = new Counter();
        en.setCounterCategory(rs.getString(1));
        en.setCounterSubcategory(rs.getString(2));
        en.setSourceName(rs.getString(3));
        en.setCounterName(rs.getString(4));
        en.setCounterUnits(rs.getString(5));
        en.setCounterValue(rs.getString(6));
        copy = en;
      }
    }

    public void finalize() { }
    public Counter copy() { return copy; }
}
