/**
 * 
 */
package com.dynamobi.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * VO: It holds the catalog information.
 * 
 * @author Ray Zhang
 * @since Jan-11-2010
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="tableinfo")
public class TablesInfo
{
	public List<Catalog> catalog;

}
