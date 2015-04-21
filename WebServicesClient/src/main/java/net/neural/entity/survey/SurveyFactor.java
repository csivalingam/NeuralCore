package net.zfp.entity.survey;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.CountryList;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.Operator;
import net.zfp.entity.community.Community;

/**
 * Entity implementation class for Entity: SurveyFactor
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@Entity(name="SurveyFactor")
@XmlRootElement(name="SurveyFactor")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyFactor extends DomainEntity {
	private static final long serialVersionUID = 21913319415546950L;
	
	@XmlElement(name="CountryList")
	@ManyToOne
	@JoinColumn(name="countryListId")
	private CountryList countryList;
	
	@XmlAttribute(name="key")
	private String key;

	@XmlAttribute(name="value")
	private Double value;
	
	public SurveyFactor(){}
	
	
	public CountryList getCountryList() {
		return countryList;
	}

	public void setCountryList(CountryList countryList) {
		this.countryList = countryList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
