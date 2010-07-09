/**
 *
 */
package com.dynamobi.ws.domain;

//import javax.xml.rpc.holders.Holder;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.dynamobi.ws.domain.RolesDetails;

/**
 * Holder for the list of roles details.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="roles")
public final class RolesDetailsHolder { // implements Holder {
  @XmlElement(name="role") public List<RolesDetails> value;

  public RolesDetailsHolder() {
    value = new ArrayList<RolesDetails>();
  }

  public RolesDetailsHolder(List<RolesDetails> value) {
    this.value = value;
  }
}
