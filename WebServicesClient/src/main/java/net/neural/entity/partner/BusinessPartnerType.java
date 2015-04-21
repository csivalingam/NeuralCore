package net.zfp.entity.partner;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="BusinessPartnerType")
@XmlRootElement(name="BusinessPartnerType")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessPartnerType extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="cobranded")
	private String code;
		
	public BusinessPartnerType() { }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
