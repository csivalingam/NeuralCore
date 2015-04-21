package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Contact")
@XmlRootElement(name="Contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -528297474284710014L;
	@XmlAttribute(name="firstName")
	private String firstName;
	@XmlAttribute(name="lastName")
	private String lastName;
	@XmlAttribute(name="address1")
	private String address1;
	@XmlAttribute(name="address2")
	private String address2;
	@XmlAttribute(name="city")
	private String city;
	@XmlAttribute(name="province")
	private String province;
	@XmlAttribute(name="postalCode")
	private String postalCode;
	@XmlAttribute(name="country")
	private String country;
	@XmlAttribute(name="longitude")
	private String longitude;
	@XmlAttribute(name="latitude")
	private String latitude;
	
	@XmlAttribute(name="streetType")
	private String streetType;
	@XmlAttribute(name="phone")
	private String phone;
	@XmlAttribute(name="mobile")
	private String mobile;
	@XmlAttribute(name="email")
	private String email;
	@XmlAttribute(name="hashTag")
	private String hashTag;
	
	public Contact() {}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStreetType() {
		return streetType;
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	
	
	
	
}
