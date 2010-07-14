/*
 *
 */
package com.dynamobi.ws.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="info")
public class PermissionsInfo {

  @XmlAttribute public String catalog_name;
  @XmlAttribute public String schema_name;
  @XmlAttribute public String item_name;
  @XmlAttribute public String item_type;
  @XmlAttribute @XmlList public List<String> actions;

  public PermissionsInfo() {
    actions = new ArrayList<String>();
  }

  public String toString() {
    return
     "\ncatalog_name: " + catalog_name + 
     "\nschema_name: " + schema_name +
     "\nitem_name: " + item_name +
     "\nitem_type: " + item_type +
     "\nactions: " + actions;
  }
}
