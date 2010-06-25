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
 * Holds SessionInfo
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="sessioninfo")
public class SessionInfo {

  @XmlAttribute public int id;
  @XmlAttribute public String connect_url;
  @XmlAttribute public String user;
  @XmlAttribute public String query;

  public String toString() {
    return
     "\nid: " + id +
     "\nconnect_url: " + connect_url +
     "\nuser: " + user +
     "\nquery: " + query;
  }
}
