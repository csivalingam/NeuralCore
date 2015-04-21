package net.zfp.entity.category;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;

@Entity(name="MerchandisingCategory" )
@XmlRootElement(name="MerchandisingCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class MerchandisingCategory extends IntegerEntity {


	private static final long serialVersionUID = 7913469140950533298L;

	@XmlAttribute(name="code")
	private String code;
	@XmlAttribute(name="name")
	private String name;
	
	public MerchandisingCategory() { }
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}