package net.zfp.entity.account;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.Location;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="MemberHouseholdProfile")
@XmlRootElement(name="MemberHouseholdProfile")
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberHouseholdProfile extends BaseEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="User")
	@OneToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlElement(name="MemberProfileValue")
	@OneToOne
	@JoinColumn(name="houseTypePV")
	private MemberProfileValue houseTypePV;
	
	@XmlElement(name="MemberProfileValue")
	@OneToOne
	@JoinColumn(name="occupantsPV")
	private MemberProfileValue occupantsPV;
	
	@XmlElement(name="MemberProfileValue")
	@OneToOne
	@JoinColumn(name="houseAreaPV")
	private MemberProfileValue houseAreaPV;
	
	@XmlElement(name="MemberProfileValue")
	@OneToOne
	@JoinColumn(name="houseHeatingPV")
	private MemberProfileValue houseHeatingPV;
	
	@XmlElement(name="MemberProfileValue")
	@OneToOne
	@JoinColumn(name="waterHeatingPV")
	private MemberProfileValue waterHeatingPV;
	
	@XmlElement(name="MemberProfileValue")
	@OneToOne
	@JoinColumn(name="stovePV")
	private MemberProfileValue stovePV;
	
	@XmlAttribute(name="fireplace")
	private Boolean fireplace;
	
	@XmlAttribute(name="BBQGas")
	private Boolean BBQGas;
	
	public MemberHouseholdProfile() {}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MemberProfileValue getHouseTypePV() {
		return houseTypePV;
	}

	public void setHouseTypePV(MemberProfileValue houseTypePV) {
		this.houseTypePV = houseTypePV;
	}

	public MemberProfileValue getOccupantsPV() {
		return occupantsPV;
	}

	public void setOccupantsPV(MemberProfileValue occupantsPV) {
		this.occupantsPV = occupantsPV;
	}

	public MemberProfileValue getHouseAreaPV() {
		return houseAreaPV;
	}

	public void setHouseAreaPV(MemberProfileValue houseAreaPV) {
		this.houseAreaPV = houseAreaPV;
	}

	public MemberProfileValue getHouseHeatingPV() {
		return houseHeatingPV;
	}

	public void setHouseHeatingPV(MemberProfileValue houseHeatingPV) {
		this.houseHeatingPV = houseHeatingPV;
	}

	public MemberProfileValue getWaterHeatingPV() {
		return waterHeatingPV;
	}

	public void setWaterHeatingPV(MemberProfileValue waterHeatingPV) {
		this.waterHeatingPV = waterHeatingPV;
	}

	public MemberProfileValue getStovePV() {
		return stovePV;
	}

	public void setStovePV(MemberProfileValue stovePV) {
		this.stovePV = stovePV;
	}

	public Boolean getFireplace() {
		return fireplace;
	}

	public void setFireplace(Boolean fireplace) {
		this.fireplace = fireplace;
	}

	public Boolean getBBQGas() {
		return BBQGas;
	}

	public void setBBQGas(Boolean bBQGas) {
		BBQGas = bBQGas;
	}
	
	
}
