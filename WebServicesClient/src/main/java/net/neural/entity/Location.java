package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="Location" )
@XmlRootElement(name="Location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location extends DomainEntity{

	private static final long serialVersionUID = 1110417722183271203L;

	@XmlAttribute(name="countryCode")
	private String countryCode;
	
	@XmlAttribute(name="countryName")
	private String countryName;
	
	@XmlAttribute(name="provinceCode")
	private String provinceCode;
	
	@XmlAttribute(name="cityCode")
	private String provinceName;
	
	@XmlAttribute(name="cityCode")
	private String cityCode;
	
	@XmlAttribute(name="cityName")
	private String cityName;
	
	@XmlAttribute(name="longitude")
	private Double longitude;
	
	@XmlAttribute(name="latitude")
	private Double latitude;
	
	public Location() {
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	

}
