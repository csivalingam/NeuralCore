package net.zfp.entity.media;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.FactorAttribute;
import net.zfp.entity.SourceType;
import net.zfp.entity.Status;
import net.zfp.entity.community.Community;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="MediaImage")
@XmlRootElement(name="MediaImage")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaImage extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="url")
	private String url;

	@XmlAttribute(name="displayOrder")
	private Integer displayOrder;
	
	@XmlAttribute(name="locale")
	private String locale;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlElement(name="SourceType")
	@ManyToOne
	@JoinColumn(name="sourceTypeId")
	private SourceType sourceType;
	
	@XmlAttribute(name="imageTypeId")
	private Long imageTypeId;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlElement(name="FactorAttribute")
	@OneToOne
	@JoinColumn(name="factorAttributeId")
	private FactorAttribute factorAttribute;
	
	public MediaImage() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public Long getImageTypeId() {
		return imageTypeId;
	}

	public void setImageTypeId(Long imageTypeId) {
		this.imageTypeId = imageTypeId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public FactorAttribute getFactorAttribute() {
		return factorAttribute;
	}

	public void setFactorAttribute(FactorAttribute factorAttribute) {
		this.factorAttribute = factorAttribute;
	}

	
   
}
