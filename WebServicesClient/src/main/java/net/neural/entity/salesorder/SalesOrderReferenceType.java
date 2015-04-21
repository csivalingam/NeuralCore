package net.zfp.entity.salesorder;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.product.ProductType;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SalesOrderReferenceType")
@XmlRootElement(name="SalesOrderReferenceType")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesOrderReferenceType extends DomainEntity {

	private static final long serialVersionUID = 849164429415846950L;
	
	@XmlAttribute(name="type")
	private Integer type;
	
	@XmlAttribute(name="name")
	private String name;
	
	
	public SalesOrderReferenceType() {}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
}
