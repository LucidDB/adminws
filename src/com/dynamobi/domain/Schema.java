/**
 * 
 */
package com.dynamobi.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * VO: It holds the schema information.
 * 
 * @author Ray Zhang
 * @since Jan-11-2010
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Schema
{
	@XmlAttribute public String uuid;
	@XmlAttribute public String name;
    
	@XmlElement(name="table")
    public List<Table> tables;


}
