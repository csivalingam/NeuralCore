package net.zfp.entity.segment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.community.Community;


@Entity(name="Segment" )
@XmlRootElement(name="segment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Segment extends DomainEntity{

	private static final long serialVersionUID = 1110417722183271203L;

	@XmlAttribute(name="code")
	private String code;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlElement(name="SegmentType")
	@OneToOne
	@JoinColumn(name="typeId")
	private SegmentType segmentType;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	public Segment() {
	}

	
	public SegmentType getSegmentType() {
		return segmentType;
	}


	public void setSegmentType(SegmentType segmentType) {
		this.segmentType = segmentType;
	}


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


	public Community getCommunity() {
		return community;
	}


	public void setCommunity(Community community) {
		this.community = community;
	}
	
	
	
}
