package net.zfp.entity.product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="ProductType")
@XmlRootElement(name="ProductType")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductType extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;

	@XmlAttribute(name="type")
	private String type;
	
	public ProductType() { }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}