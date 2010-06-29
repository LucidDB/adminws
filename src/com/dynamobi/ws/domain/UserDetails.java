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
  @XmlAttribute public String creation_timestamp;
  @XmlAttribute public String modification_timestamp;

  public String toString() {
    return
     "\nname: " + name +
     "\npassword: " + password +
     "\ncreation_timestamp: " + creation_timestamp +
     "\nmodification_timestamp: " + modification_timestamp;
  }
}
