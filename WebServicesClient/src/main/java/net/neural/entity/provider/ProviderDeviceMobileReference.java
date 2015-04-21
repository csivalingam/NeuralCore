package net.zfp.entity.provider;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ProviderDeviceMobileReference")
@XmlRootElement(name="ProviderDeviceMobileReference")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderDeviceMobileReference extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlElement(name="ProviderDevice")
	@ManyToOne
	@JoinColumn(name="providerDeviceId")
	private ProviderDevice providerDevice;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
		
	@XmlAttribute(name="reference")
	private String reference;
	
	public ProviderDeviceMobileReference() {}

	public ProviderDevice getProviderDevice() {
		return providerDevice;
	}

	public void setProviderDevice(ProviderDevice providerDevice) {
		this.providerDevice = providerDevice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	
}
