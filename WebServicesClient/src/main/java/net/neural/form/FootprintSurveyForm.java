package net.zfp.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FootprintForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class FootprintSurveyForm {
	

	@XmlElement(name="accountId")
	private Long accountId;
	@XmlElement(name="communityName")
	private String communityName;
	@XmlElement(name="postalcode")
	private String postalcode;
	@XmlElement(name="agegroup")
	private Integer agegroup;
	@XmlElement(name="homesquarefeet")
	private Double homesquarefeet;
	@XmlElement(name="occupants")
	private Integer occupants;
	@XmlElement(name="heatingType")
	private Integer heatingType;
	@XmlElement(name="waterHeatingType")
	private Integer waterHeatingType;
	
	public FootprintSurveyForm() {
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
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
