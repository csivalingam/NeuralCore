package net.zfp.entity.tax;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.IntegerEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="TaxLocation")
@XmlRootElement(name="TaxLocation")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxLocation extends DomainEntity {

	private static final long serialVersionUID = 809164429415846950L;
	
	@XmlAttribute(name="locationName")
	private String locationName;
	
	@XmlAttribute(name="locationCode")
	private String locationCode;
	
	@XmlAttribute(name="locationCode")
	private String countryCode;
	
	
	public TaxLocation() {}


	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getLocationCode() {
		return locationCode;
	}


	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	

}
