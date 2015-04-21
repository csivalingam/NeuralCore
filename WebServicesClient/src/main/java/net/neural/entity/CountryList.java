package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: CountryList
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@Entity(name="CountryList")
@XmlRootElement(name="CountryList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryList extends BaseEntity{
	
	private static final long serialVersionUID = 21913219415546950L;
	
	@XmlAttribute(name="commonName")
	private String commonName;
	
	@XmlAttribute(name="formalName")
	private String formalName;
	
	public CountryList(){}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getFormalName() {
		return formalName;
	}

	public void setFormalName(String formalName) {
		this.formalName = formalName;
	}
	
	
}
