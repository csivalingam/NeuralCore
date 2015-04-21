package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;



@Entity(name="Unit" )
@XmlRootElement(name="Unit")
@XmlAccessorType(XmlAccessType.FIELD)
public class Unit extends DomainEntity{

	private static final long serialVersionUID = 1110417722183271203L;

	@XmlAttribute(name="code")
	private String code;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="description")
	private String description;
	
	public Unit() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
