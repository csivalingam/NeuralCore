package net.zfp.entity.community;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.feature.Feature;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CommunityFeature" )
@XmlRootElement(name="CommunityFeature")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityFeature extends DomainEntity {
	
	private static final long serialVersionUID = 819164429415546950L;
	
	@XmlElement(name="CommunityPortal")
	@ManyToOne
	@JoinColumn(name="communityPortalId")
	private CommunityPortal communityPortal;

	@XmlElement(name="Feature")
	@ManyToOne
	@JoinColumn(name="featureId")
	private Feature feature;
	
	public CommunityFeature() { }

	public CommunityPortal getCommunityPortal() {
		return communityPortal;
	}

	public void setCommunityPortal(CommunityPortal communityPortal) {
		this.communityPortal = communityPortal;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	
}

