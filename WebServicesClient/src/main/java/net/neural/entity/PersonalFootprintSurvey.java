package net.zfp.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.community.Community;


/**
 * Entity implementation class for Entity: CarbonFootprint
 *
 */
@Entity(name="PersonalFootprintSurvey" )
@XmlRootElement(name="PersonalFootprintSurvey")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalFootprintSurvey extends BaseEntity {
	
	private static final long serialVersionUID = 499164429415546950L;
	
	@XmlAttribute(name="nickName")
	private String nickName;
	
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlAttribute(name="postalcode")
	private String postalcode;
	
	@XmlAttribute(name="agegroup")
	private Integer agegroup;
	
	@XmlAttribute(name="homesquarefeet")
	private Double homesquarefeet;
	
	@XmlAttribute(name="occupants")
	private Integer occupants;
	
	@XmlAttribute(name="heatingType")
	private Integer heatingType;
	
	@XmlAttribute(name="waterHeatingType")
	private Integer waterHeatingType;

	public PersonalFootprintSurvey() {}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public Integer getAgegroup() {
		return agegroup;
	}

	public void setAgegroup(Integer agegroup) {
		this.agegroup = agegroup;
	}

	public Double getHomesquarefeet() {
		return homesquarefeet;
	}

	public void setHomesquarefeet(Double homesquarefeet) {
		this.homesquarefeet = homesquarefeet;
	}

	public Integer getOccupants() {
		return occupants;
	}

	public void setOccupants(Integer occupants) {
		this.occupants = occupants;
	}

	public Integer getHeatingType() {
		return heatingType;
	}

	public void setHeatingType(Integer heatingType) {
		this.heatingType = heatingType;
	}

	public Integer getWaterHeatingType() {
		return waterHeatingType;
	}

	public void setWaterHeatingType(Integer waterHeatingType) {
		this.waterHeatingType = waterHeatingType;
	}
	
	
}

