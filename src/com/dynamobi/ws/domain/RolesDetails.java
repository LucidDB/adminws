/**
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

import com.dynamobi.ws.domain.PermissionsInfo;

/**
 * Holder for the roles xml tree.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="role")
public class RolesDetails {

  @XmlAttribute public String name;
  @XmlElement @XmlList public List<String> users;
  @XmlElement @XmlList public List<String> users_with_grant_option;
  @XmlElement   public List<PermissionsInfo> permissions;

  public RolesDetails() {
    users = new ArrayList<String>();
    users_with_grant_option = new ArrayList<String>();
    permissions = new ArrayList<PermissionsInfo>();
  }

  public String toString() {
    return
     "\nname: " + name +
     "\nusers: " + users +
     "\nusers with grant: " + users_with_grant_option + 
     "\npermissions: " + permissions;
  }
}
