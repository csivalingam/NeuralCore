package net.zfp.entity.device;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.SourceType;
import net.zfp.entity.category.Category;
import net.zfp.entity.provider.ProviderDevice;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="DeviceSourceType")
@XmlRootElement(name="DeviceSourceType")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceSourceType extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlElement(name="ProviderDevice")
	@OneToOne
	@JoinColumn(name="providerDeviceId")
	private ProviderDevice providerDevice;
	
	@XmlElement(name="SourceType")
	@ManyToOne
	@JoinColumn(name="sourceTypeId")
	private SourceType sourceType;
	
	@XmlElement(name="Category")
	@OneToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	public DeviceSourceType() {}


	public ProviderDevice getProviderDevice() {
		return providerDevice;
	}


	public void setProviderDevice(ProviderDevice providerDevice) {
		this.providerDevice = providerDevice;
	}


	public SourceType getSourceType() {
		return sourceType;
	}


	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
