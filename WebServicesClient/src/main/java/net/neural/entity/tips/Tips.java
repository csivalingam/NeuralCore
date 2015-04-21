package net.zfp.entity.tips;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.SourceType;


/**
 * Entity implementation class for Entity: CarbonFootprint
 *
 */
@Entity(name="Tips")
@XmlRootElement(name="Tips")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tips extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415546950L;

	@XmlAttribute(name="header")
	private String header;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlElement(name="SourceType")
	@ManyToOne
	@JoinColumn(name="sourceTypeId")
	private SourceType sourceType;
	
	@XmlElement(name="TipsCategory")
	@ManyToOne
	@JoinColumn(name="tipsCategoryId")
	private TipsCategory tipsCategory;

	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="relevance")
	private Integer relevance;
	
	public Tips() {}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public TipsCategory getTipsCategory() {
		return tipsCategory;
	}

	public void setTipsCategory(TipsCategory tipsCategory) {
		this.tipsCategory = tipsCategory;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getRelevance() {
		return relevance;
	}

	public void setRelevance(Integer relevance) {
		this.relevance = relevance;
	}
	
}

