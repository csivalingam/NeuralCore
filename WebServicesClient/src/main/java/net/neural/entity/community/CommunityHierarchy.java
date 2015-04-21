package net.zfp.entity.community;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CommunityHierarchy" )
@XmlRootElement(name="CommunityHierarchy")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityHierarchy extends BaseEntity {
	
	private static final long serialVersionUID = 919164429415546950L;
	
	@XmlAttribute(name="depth")
	private Integer depth;
				
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="parentId")
	private Community parent;

	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	public CommunityHierarchy() { }

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Community getParent() {
		return parent;
	}

	public void setParent(Community parent) {
		this.parent = parent;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}
	
	
}

