package net.zfp.entity.community;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: CorporateCategory
 * 
 * @author Youngwook Yoo
 * @since 2014-09-12
 *
 */
@Entity(name="CorporateCategory" )
@XmlRootElement(name="CorporateCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class CorporateCategory extends DomainEntity {
	private static final long serialVersionUID = 21913369415546950L;
	
	@XmlAttribute(name="officeRangeFrom")
	private Integer officeRangeFrom;
	
	@XmlAttribute(name="officeRangeTo")
	private Integer officeRangeTo;
	
	public CorporateCategory(){}

	public Integer getOfficeRangeFrom() {
		return officeRangeFrom;
	}

	public void setOfficeRangeFrom(Integer officeRangeFrom) {
		this.officeRangeFrom = officeRangeFrom;
	}

	public Integer getOfficeRangeTo() {
		return officeRangeTo;
	}

	public void setOfficeRangeTo(Integer officeRangeTo) {
		this.officeRangeTo = officeRangeTo;
	}
	
	
	
}
