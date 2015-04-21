package net.zfp.entity.category;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.IntegerEntity;

@Entity(name="CategoryType" )
@XmlRootElement(name="CategoryType")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryType extends IntegerEntity {


	private static final long serialVersionUID = 7913469140950533298L;

	@XmlAttribute(name="code")
	private String code;
	
	public CategoryType() { }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}