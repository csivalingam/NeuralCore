package net.zfp.entity.provider;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.community.Community;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Provider")
@XmlRootElement(name="Provider")
@XmlAccessorType(XmlAccessType.FIELD)
public class Provider extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="code")
	private String code;
	@XmlAttribute(name="name")
	private String name;
	
	@XmlElement(name="ProviderType")
	@OneToOne
	@JoinColumn(name="type")
	private ProviderType providerType;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	public Provider() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProviderType getProviderType() {
		return providerType;
	}

	public void setProviderType(ProviderType providerType) {
		this.providerType = providerType;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}
   
}
