package net.zfp.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.EmailTemplate;


@XmlRootElement(name="GroupDetailView")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupDetailView {
	
	@XmlAttribute(name="id")
	private Long id;
	@XmlAttribute(name="headName")
	private String headName;
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="members")
	private Integer members;
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlElement(name="emailTemplate")
	private List<EmailTemplate> emailTemplate;
	
	@XmlElement(name="contactViews")
	private List<ContactView> contactViews;
	
	public GroupDetailView() {
		this.emailTemplate = new ArrayList<EmailTemplate>();
		this.contactViews = new ArrayList<ContactView>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMembers() {
		return members;
	}

	public void setMembers(Integer members) {
		this.members = members;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<EmailTemplate> getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(List<EmailTemplate> emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public List<ContactView> getContactViews() {
		return contactViews;
	}

	public void setContactViews(List<ContactView> contactViews) {
		this.contactViews = contactViews;
	}
	
	
}
