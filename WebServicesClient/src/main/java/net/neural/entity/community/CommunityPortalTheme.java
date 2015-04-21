package net.zfp.entity.community;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.PortalType;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CommunityPortalTheme" )
@XmlRootElement(name="CommunityPortalTheme")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityPortalTheme extends DomainEntity {
	
	private static final long serialVersionUID = 819164429415546950L;
	
	@XmlElement(name="CommunityPortal")
	@ManyToOne
	@JoinColumn(name="communityPortalId")
	private CommunityPortal CommunityPortal;
	
	@XmlAttribute(name="themeId")
	private Long themeId;
	
	public CommunityPortalTheme() { }

	public CommunityPortal getCommunityPortal() {
		return CommunityPortal;
	}

	public void setCommunityPortal(CommunityPortal communityPortal) {
		CommunityPortal = communityPortal;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	
	
}

