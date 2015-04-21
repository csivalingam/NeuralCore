package net.zfp.entity.provider;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.device.DeviceAttribute;
import net.zfp.entity.segment.Segment;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ProviderDevice")
@XmlRootElement(name="ProviderDevice")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderDevice extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;

	@XmlAttribute(name="deviceAttributeValue")
	private String deviceAttributeValue;
	
	@XmlElement(name="Provider")
	@ManyToOne
	@JoinColumn(name="providerId")
	private Provider provider;
	
	@XmlElement(name="DeviceAttribute")
	@ManyToOne
	@JoinColumn(name="deviceAttributeId")
	private DeviceAttribute deviceAttribute;
	
	@XmlElement(name="Segment")
	@OneToOne
	@JoinColumn(name="segmentId")
	private Segment segment;
	
	@XmlAttribute(name="name")
	private String name;
		
	@XmlAttribute(name="shortDescription")
	private String shortDescription;
	
	@XmlAttribute(name="longDescription")
	private String longDescription;
	
	@XmlAttribute(name="smallImageUrl")
	private String smallImageUrl;
	
	@XmlAttribute(name="largeImageUrl")
	private String largeImageUrl;
	
	@XmlAttribute(name="unitPrice")
	private Double unitPrice;
	
	@XmlAttribute(name="reference")
	private String reference;
	
	@XmlAttribute(name="rank")
	private Integer rank;
	
	public ProviderDevice() {}

	
	public Integer getRank() {
		return rank;
	}


	public void setRank(Integer rank) {
		this.rank = rank;
	}


	public String getDeviceAttributeValue() {
		return deviceAttributeValue;
	}

	public void setDeviceAttributeValue(String deviceAttributeValue) {
		this.deviceAttributeValue = deviceAttributeValue;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public DeviceAttribute getDeviceAttribute() {
		return deviceAttribute;
	}

	public void setDeviceAttribute(DeviceAttribute deviceAttribute) {
		this.deviceAttribute = deviceAttribute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public String getLargeImageUrl() {
		return largeImageUrl;
	}

	public void setLargeImageUrl(String largeImageUrl) {
		this.largeImageUrl = largeImageUrl;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	
   
}
