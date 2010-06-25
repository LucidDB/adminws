/**
 *
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
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="userdetails")
public class UserDetails {

  @XmlAttribute public String name;
  @XmlAttribute public String password;

  public String toString() {
    return
     "\nname: " + name +
     "\npassword: " + password;
  }
}
