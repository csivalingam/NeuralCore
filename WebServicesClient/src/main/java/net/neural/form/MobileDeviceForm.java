package net.zfp.form;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MobileDeviceForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class MobileDeviceForm {
	
	@XmlElement(name="created")
	private Date created;
	@XmlElement(name="memberId")
	private Long memberId;
	
	@XmlElement(name="OS")
	private String OS;
	@XmlElement(name="hardwareModel")
	private String hardwareModel;
	@XmlElement(name="phoneName")
	private String phoneName;
	@XmlElement(name="carrier")
	private String carrier;
	@XmlElement(name="identifier")
	private String identifier;
	@XmlElement(name="appversion")
	private String appversion;
	
	public MobileDeviceForm() {
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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
