package net.zfp.entity.community;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.CountryList;

/**
 * Entity implementation class for Entity: CorporatePreference
 * 
 * @author Youngwook Yoo
 * @since 2014-09-03
 *
 */
@Entity(name="CorporatePreference" )
@XmlRootElement(name="CorporatePreference")
@XmlAccessorType(XmlAccessType.FIELD)
public class CorporatePreference extends BaseEntity {
	private static final long serialVersionUID = 21913369415546950L;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlAttribute(name="officeSize")
	private Integer officeSize;
	
	@XmlElement(name="CountryList")
	@OneToOne
	@JoinColumn(name="countryId")
	private CountryList country;
	
	@XmlElement(name="CorporateCategory")
	@OneToOne
	@JoinColumn(name="corporateCategoryId")
	private CorporateCategory corporateCategory;
	
	public CorporatePreference(){}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}
	
	
	public Integer getOfficeSize() {
		return officeSize;
	}

	public void setOfficeSize(Integer officeSize) {
		this.officeSize = officeSize;
	}

	public CountryList getCountry() {
		return country;
	}

	public void setCountry(CountryList country) {
		this.country = country;
	}

	public CorporateCategory getCorporateCategory() {
		return corporateCategory;
	}

	public void setCorporateCategory(CorporateCategory corporateCategory) {
		this.corporateCategory = corporateCategory;
	}
	
	
}
