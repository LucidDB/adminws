/**
 *
 */
package com.dynamobi.ws.domain;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.dynamobi.ws.domain.TableDetails;

/**
 * Holder for the table details object we send up from the client.
 * @author Kevin Secretan
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="details")
public final class TableDetailsHolder { 
  @XmlElement(name="details") public TableDetails value;

  public TableDetailsHolder() {
    value = new TableDetails();
  }

  public TableDetailsHolder(TableDetails value) {
    this.value = value;
  }
}

