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


/**
 * Holds UserDetails info
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="userdetails")
public class UserDetails {

  public String name;
  public String password;
  public String creation_timestamp;
  public String modification_timestamp;

  public String toString() {
    return
     "\nname: " + name +
     "\npassword: " + password +
     "\ncreation_timestamp: " + creation_timestamp +
     "\nmodification_timestamp: " + modification_timestamp;
  }

  // Auto-generated for AMF
  @XmlAttribute
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @XmlAttribute
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  @XmlAttribute
  public String getCreation_timestamp() { return creation_timestamp; }
  public void setCreation_timestamp(String creation_timestamp) { this.creation_timestamp = creation_timestamp; }

  @XmlAttribute
  public String getModification_timestamp() { return modification_timestamp; }
  public void setModification_timestamp(String modification_timestamp) { this.modification_timestamp = modification_timestamp; }

}

