package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.community.Community;

@Entity(name="Translation" )
@XmlRootElement(name="translation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Translation extends BaseEntity{

	private static final long serialVersionUID = -1317675426837027468L;

	@XmlAttribute(name="translationKey")
	private String translationKey;
	@XmlAttribute(name="translation")
	private String translation;
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="community_id")
	private Community community;
	@XmlAttribute(name="locale")
	private String locale;
	
	//@XmlAttribute(name="componentId")
	//private Long componentId;
	
	@XmlAttribute(name="component")
	@ManyToOne
	@JoinColumn(name="componentId")
	private Component component;
	
	@XmlAttribute(name="portalType")
	@ManyToOne
	@JoinColumn(name="portalTypeId")
	private PortalType portalType;
	
	public Translation() {
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public PortalType getPortalType() {
		return portalType;
	}

	public void setPortalType(PortalType portalType) {
		this.portalType = portalType;
	}

}
