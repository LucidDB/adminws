/**
 *
 */
package com.dynamobi.ws.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.List;

import com.dynamobi.ws.domain.PermissionsInfo;

/**
 * Holder for the roles xml tree.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="role")
public class RolesDetails {

  @XmlAttribute public String name;
  @XmlElement public List<String> users;
  @XmlElement public List<PermissionsInfo> permissions;

  public String toString() {
    return
     "\nname: " + name +
     "\nusers: " + users +
     "\npermissions: " + permissions;
  }
}
