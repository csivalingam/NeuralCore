package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.category.Category;
import net.zfp.entity.source.SourceMode;

@Entity(name="Source" )
@XmlRootElement(name="Source")
@XmlAccessorType(XmlAccessType.FIELD)
public class Source extends BaseEntity {


	private static final long serialVersionUID = 6916469140950533298L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="code")
	private String code;
	@XmlElement(name="SourceType")
	@OneToOne
	@JoinColumn(name="sourceType")
	private SourceType sourceType;

	@XmlElement(name="Category")
	@OneToOne
	@JoinColumn(name="category")
	private Category category;
	
	@XmlAttribute(name="supressName")
	private Boolean supressName;
	@XmlAttribute(name="supressDisplay")
	private Boolean supressDisplay;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlElement(name="SourceMode")
	@OneToOne
	@JoinColumn(name="sourceModeId")
	private SourceMode sourceMode;
	
	public Source() { }

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

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean getSupressName() {
		return supressName;
	}

	public void setSupressName(Boolean supressName) {
		this.supressName = supressName;
	}

	public Boolean getSupressDisplay() {
		return supressDisplay;
	}

	public void setSupressDisplay(Boolean supressDisplay) {
		this.supressDisplay = supressDisplay;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public SourceMode getSourceMode() {
		return sourceMode;
	}

	public void setSourceMode(SourceMode sourceMode) {
		this.sourceMode = sourceMode;
	}

	
}