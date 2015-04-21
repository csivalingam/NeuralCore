package net.zfp.entity.category;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;

@Entity(name="Category" )
@XmlRootElement(name="Category")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;

	@XmlAttribute(name="code")
	private String code;
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="translationKey")
	private String translationKey;
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlElement(name="CategoryType")
	@ManyToOne
	@JoinColumn(name="categoryTypeId")
	private CategoryType categoryType;
	
	public Category() { }

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}
	
	
}