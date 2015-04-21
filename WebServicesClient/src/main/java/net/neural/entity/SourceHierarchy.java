package net.zfp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SourceHierarchy" )
@XmlRootElement(name="SourceHierarchy")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceHierarchy extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="depth")
	private Integer depth;
	
	@XmlAttribute(name="community_id")
	private Long community_id;
	
	@XmlAttribute(name="parent_id")
	private Long parent_id;
	
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="source_id")
	private Source source;
	
	public SourceHierarchy() { }

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	public Long getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Long community_id) {
		this.community_id = community_id;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	
	


   
}
