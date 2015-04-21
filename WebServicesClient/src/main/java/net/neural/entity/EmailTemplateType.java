package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="EmailTemplateType" )
@XmlRootElement(name="EmailTemplateType")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailTemplateType extends DomainEntity{

	private static final long serialVersionUID = 1110327722183271203L;
	
	@XmlAttribute(name="type")
	private String type;
	
	public EmailTemplateType() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
