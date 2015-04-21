package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="Operator" )
@XmlRootElement(name="Operator")
@XmlAccessorType(XmlAccessType.FIELD)
public class Operator extends IntegerEntity{

	private static final long serialVersionUID = 1110417722183271203L;

	@XmlAttribute(name="operator")
	private String operator;
	
	@XmlAttribute(name="name")
	private String name;
	
	
	public Operator() {
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
