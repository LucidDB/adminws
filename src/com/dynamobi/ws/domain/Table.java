/**
 * 
 */
package com.dynamobi.ws.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * VO: It holds the table information.
 * 
 * @author Ray Zhang
 * @since Jan-11-2010
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class Table
{
	@XmlAttribute
	public String uuid;
	
	@XmlAttribute
    public String name;

	@XmlAttribute
    public String schema;

	@XmlAttribute
    public String catalog;

}
