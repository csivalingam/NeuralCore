package net.zfp.entity.salesorder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SalesOrderType")
@XmlRootElement(name="SalesOrderType")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesOrderType extends IntegerEntity {

	private static final long serialVersionUID = 849164429415846950L;
	
	@XmlAttribute(name="type")
	private Integer type;
	
	@XmlAttribute(name="name")
	private String name;
	
	
	public SalesOrderType() {}


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
