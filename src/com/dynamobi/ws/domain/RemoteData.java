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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Used for storing various metadata on foreign objects.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="remotedata")
public class RemoteData {

  public List<String> foreign_schemas;
  public List<String> foreign_descriptions;
  public String foreign_tables;
  public List<String> local_imported_tables;

  // List of remote tables that are different from local imported ones.
  public List<String> foreign_changed;
  // List of remote tables that are gone but we imported locally.
  public List<String> foreign_deleted;

  // Helper maps. Structure: {'SchemaName~TableName': 'Col1,Col2'}
  private Map<String, String> local_data;
  private Map<String, String> foreign_data;

  // Structure: {'SchemaName': 'Table1~Table2'}
  private Map<String, Set<String>> foreign_schema_tables;

  public RemoteData() {
    foreign_schemas = new ArrayList<String>();
    foreign_descriptions = new ArrayList<String>();
    local_imported_tables = new ArrayList<String>();
    foreign_changed = new ArrayList<String>();
    foreign_deleted = new ArrayList<String>();

    local_data = new HashMap<String,String>();
    foreign_data = new HashMap<String,String>();

    foreign_schema_tables = new HashMap<String,Set<String>>();
  }

  public void readyResults() {
    for (Map.Entry<String, String> local : local_data.entrySet()) {
      String[] key = local.getKey().split("~");
      local_imported_tables.add("<table schema=\"" + key[0] + "\" name=\"" +
          key[1] + "\" />");
    }
    for (Map.Entry<String, Set<String>> foreign : foreign_schema_tables.entrySet()) {
      String schema = foreign.getKey();
      for (String table : foreign.getValue()) {
        foreign_tables += "<table schema=\"" + schema + "\" name=\"" + table +
            "\" />\n";
      }
    }
  }

  public void addLocalTableColumn(String schema, String table, String identifier) {
    String put_key = schema + "~" + table;
    String put_val = identifier;
    if (local_data.containsKey(put_key)) {
      put_val = local_data.get(put_key) + "," + put_val;
    }
    local_data.put(put_key, put_val);
  }

  public void addForeignTableColumn(String schema, String table, String identifier) {
    if (table.equals("foreign_data_wrapper_options")) {
      //System.out.println(schema);
      // Argh. This table exists in multiple schemas, but it's the same,
      // this problem can be solved by keeping track of the schema,
      // but our local imported tables need to know about it too.
    }
    String put_key = table;
    String put_val = identifier;
    if (foreign_data.containsKey(put_key)) {
      put_val = foreign_data.get(put_key) + "," + put_val;
    }
    foreign_data.put(put_key, put_val);

    Set<String> set;
    if (foreign_schema_tables.containsKey(schema)) {
      set = foreign_schema_tables.get(schema);
      set.add(table);
    } else {
      set = new HashSet<String>();
      set.add(table);
      foreign_schema_tables.put(schema, set);
    }
  }

  public void findChanges() {
    for (Map.Entry<String, String> local : local_data.entrySet()) {
      String remote_key = local.getKey().split("~")[1];
      if (foreign_data.containsKey(remote_key)) {
        if (! foreign_data.get(remote_key).equals(local.getValue())) {
          // columns don't match
          //System.out.println(local.getKey() + ":");
          //System.out.println(foreign_data.get(remote_key));
          //System.out.println(local.getValue());
          foreign_changed.add(local.getKey());
        }
      } else { // removed
        foreign_deleted.add(local.getKey());
      }
    }
  }

  public String toString() {
    return 
      "\nforeign_schemas: " + foreign_schemas +
      "\nforeign_descriptions: " + foreign_descriptions +
      "\nforeign_tables: " + foreign_tables +
      "\nlocal_imported_tables: " + local_imported_tables;
  }


  // Auto-generated for AMF
  @XmlElement
  public List<String> getForeign_schemas() { return foreign_schemas; }
  public void setForeign_schemas(List<String> foreign_schemas) { this.foreign_schemas = foreign_schemas; }

  @XmlElement
  public List<String> getForeign_descriptions() { return foreign_descriptions; }
  public void setForeign_descriptions(List<String> foreign_descriptions) { this.foreign_descriptions = foreign_descriptions; }

  @XmlElement
  public String getForeign_tables() { return foreign_tables; }
  public void setForeign_tables(String foreign_tables) { this.foreign_tables = foreign_tables; }

  @XmlElement
  public List<String> getLocal_imported_tables() { return local_imported_tables; }
  public void setLocal_imported_tables(List<String> local_imported_tables) { this.local_imported_tables = local_imported_tables; }

  @XmlElement
  public List<String> getForeign_changed() { return foreign_changed; }
  public void setForeign_changed(List<String> foreign_changed) { this.foreign_changed = foreign_changed; }

  @XmlElement
  public List<String> getForeign_deleted() { return foreign_deleted; }
  public void setForeign_deleted(List<String> foreign_deleted) { this.foreign_deleted = foreign_deleted; }

}

