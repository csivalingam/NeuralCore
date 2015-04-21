package net.zfp.entity.group;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="GroupsType")
@XmlRootElement(name="GroupsType")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupsType extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlAttribute(name="type")
	private Integer type;
	
	@XmlAttribute(name="name")
	private String name;
	
	public GroupsType() {}

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
