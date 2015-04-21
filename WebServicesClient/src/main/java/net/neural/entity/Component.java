package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Component" )
@XmlRootElement(name="component")
@XmlAccessorType(XmlAccessType.FIELD)
public class Component extends BaseEntity {

	private static final long serialVersionUID = 2480185570110920307L;

	@XmlAttribute(name="componentName")
	private String componentName;

	public Component() {
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
}
