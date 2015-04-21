package net.zfp.entity.mobile;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="MobileDevice")
@XmlRootElement(name="MobileDevice")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileDevice extends DomainEntity {
	
	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlAttribute(name="created")
	private Date created;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlAttribute(name="OS")
	private String OS;
	
	@XmlAttribute(name="hardwareModel")
	private String hardwareModel;
	
	@XmlAttribute(name="phoneName")
	private String phoneName;
	
	@XmlAttribute(name="carrier")
	private String carrier;
	
	@XmlAttribute(name="identifier")
	private String identifier;
	
	@XmlAttribute(name="appVersion")
	private String appversion;
	
	public MobileDevice() {}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String oS) {
		OS = oS;
	}

	public String getHardwareModel() {
		return hardwareModel;
	}

	public void setHardwareModel(String hardwareModel) {
		this.hardwareModel = hardwareModel;
	}

	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}
}
