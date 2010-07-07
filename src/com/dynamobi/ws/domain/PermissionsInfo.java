/*
 *
 */
package com.dynamobi.ws.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * &lt;info schemaName="blah" ... /&gt;
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="info")
public class PermissionsInfo {

  @XmlAttribute public String schema_name;
  @XmlAttribute public String item_name;
  @XmlAttribute public String item_type;
  @XmlAttribute public String permissions;

  public String toString() {
    return
     "\nschema_name: " + schema_name +
     "\nitem_name: " + item_name +
     "\nitem_type: " + item_type +
     "\npermissions: " + permissions;
  }
}
