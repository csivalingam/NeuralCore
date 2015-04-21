package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="MediaResolutionType" )
@XmlRootElement(name="mediaResolutionType")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaResolutionType extends BaseEntity {

	private static final long serialVersionUID = 6788935693441365341L;

	@XmlAttribute(name="type")
	private String type;
	@XmlAttribute(name="width")
	private Integer width;
	@XmlAttribute(name="height")
	private Integer height;
	@XmlAttribute(name="aspect")
	private String aspect;
	
	public MediaResolutionType() { }
	
	public MediaResolutionType(String type, Integer width, Integer height, String aspect) {
		super();
		this.type = type;
		this.width = width;
		this.height = height;
		this.aspect = aspect;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getAspect() {
		return aspect;
	}

	public void setAspect(String aspect) {
		this.aspect = aspect;
	}
	

	
}