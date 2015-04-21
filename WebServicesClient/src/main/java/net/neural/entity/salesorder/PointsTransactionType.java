package net.zfp.entity.salesorder;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;

@Entity(name="PointsTransactionType")
@XmlRootElement(name="PointsTransactionType")
@XmlAccessorType(XmlAccessType.FIELD)

public class PointsTransactionType extends IntegerEntity{ 

	private static final long serialVersionUID = 6839277385911612500L;
	
	@XmlAttribute(name="code")
	private String code;
	
		
	public PointsTransactionType() {
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	
}
