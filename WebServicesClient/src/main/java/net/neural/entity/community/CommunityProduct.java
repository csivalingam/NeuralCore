package net.zfp.entity.community;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CommunityProduct" )
@XmlRootElement(name="CommunityProduct")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityProduct extends DomainEntity {
	
	private static final long serialVersionUID = 819164429415546950L;
	
	@XmlAttribute(name="communityCode")
	private String communityCode;
	
	@XmlAttribute(name="UPC")
	private String UPC;
	
	public CommunityProduct() { }

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}
	
	
	
}

