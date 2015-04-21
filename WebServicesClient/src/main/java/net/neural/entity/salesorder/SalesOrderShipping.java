package net.zfp.entity.salesorder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="SalesOrderShipping" )
@XmlRootElement(name="SalesOrderShipping")
@XmlAccessorType(XmlAccessType.FIELD)

public class SalesOrderShipping extends DomainEntity{ 

	private static final long serialVersionUID = 6839277385951612500L;
	
	@XmlAttribute(name="lastName")
	private String lastName;
	
	@XmlAttribute(name="firstName")
	private String firstName;
	
	@XmlAttribute(name="email")
	private String email;
	
	@XmlAttribute(name="address1")
	private String address1;
	
	@XmlAttribute(name="address2")
	private String address2;
	
	@XmlAttribute(name="city")
	private String city;
	
	@XmlAttribute(name="postalCode")
	private String postalCode;
	
	@XmlAttribute(name="province")
	private String province;
	
	@XmlAttribute(name="country")
	private String country;
	
	@XmlElement(name="SalesOrder")
	@OneToOne
	@JoinColumn(name="salesOrderNumber", referencedColumnName= "orderNumber")
	private SalesOrder salesOrder;
	
	@XmlAttribute(name="instruction")
	private String instruction;
	
	public SalesOrderShipping() {
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
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

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	

	
}
