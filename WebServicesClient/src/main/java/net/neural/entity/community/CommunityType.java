package net.zfp.entity.community;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;

/**
 * Entity implementation class for Entity: Community Type
 * 
 * @author Youngwook Yoo
 * @since 2014-09-03
 *
 */
@Entity(name="CommunityType" )
@XmlRootElement(name="CommunityType")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityType extends IntegerEntity {
	private static final long serialVersionUID = 81913369415546950L;
	
	@XmlAttribute(name="type")
	private String type;
	
	public CommunityType(){}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
