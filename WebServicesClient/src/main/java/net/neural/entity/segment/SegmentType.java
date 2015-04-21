package net.zfp.entity.segment;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;


@Entity(name="SegmentType" )
@XmlRootElement(name="SegmentType")
@XmlAccessorType(XmlAccessType.FIELD)
public class SegmentType extends IntegerEntity{

	private static final long serialVersionUID = 1110417722183271203L;
	
	@XmlAttribute(name="type")
	private String type;
		
	public SegmentType() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
