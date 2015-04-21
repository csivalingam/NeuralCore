package net.zfp.entity.community;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.PortalType;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CommunityPortal" )
@XmlRootElement(name="CommunityPortal")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityPortal extends DomainEntity {
	
	private static final long serialVersionUID = 819164429415546950L;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;

	@XmlElement(name="PortalType")
	@ManyToOne
	@JoinColumn(name="portalTypeId")
	private PortalType portalType;
	
	public CommunityPortal() { }

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public PortalType getPortalType() {
		return portalType;
	}

	public void setPortalType(PortalType portalType) {
		this.portalType = portalType;
	}
	
}

