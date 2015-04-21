package net.zfp.entity.carbon;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.FootprintType;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.community.Community;


/**
 * Entity implementation class for Entity: CarbonFootprint
 *
 */
@Entity(name="CarbonFootprint" )
@XmlRootElement(name="CarbonFootprint")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarbonFootprint extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415546950L;

	@XmlAttribute(name="value")
	private Double value;
	
	@XmlAttribute(name="units")
	private String units;
	
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;

	@XmlElement(name="SourceType")
	@ManyToOne
	@JoinColumn(name="sourceTypeId")
	private SourceType sourceType;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlElement(name="FootprintType")
	@OneToOne
	@JoinColumn(name="footprintTypeId")
	private FootprintType footprintType;

	public CarbonFootprint() {}
	
	public CarbonFootprint(Community community, Source source, SourceType sourceType, FootprintType footprintType) {
		
		this.source = source;
		this.community =community;
		this.sourceType = sourceType;
		this.footprintType = footprintType;
	}
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public FootprintType getFootprintType() {
		return footprintType;
	}

	public void setFootprintType(FootprintType footprintType) {
		this.footprintType = footprintType;
	}

	
}

