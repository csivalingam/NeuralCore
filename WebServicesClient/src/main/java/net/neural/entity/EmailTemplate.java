package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="EmailTemplate" )
@XmlRootElement(name="EmailTemplate")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailTemplate extends BaseEntity{

	private static final long serialVersionUID = 1110417722183271203L;
	
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="code")
	private String code;

	@XmlAttribute(name="subject")
	private String subject;
	
	@XmlAttribute(name="content")
	private String content;
	
	@XmlAttribute(name="emailTemplateBackgroundImageURL")
	private String emailTemplateBackgroundImageURL;
	
	
	@XmlElement(name="EmailTemplateType")
	@OneToOne
	@JoinColumn(name="typeId")
	private EmailTemplateType emailTemplateType;
	
	public EmailTemplate() {
	}

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmailTemplateBackgroundImageURL() {
		return emailTemplateBackgroundImageURL;
	}

	public void setEmailTemplateBackgroundImageURL(
			String emailTemplateBackgroundImageURL) {
		this.emailTemplateBackgroundImageURL = emailTemplateBackgroundImageURL;
	}

	public EmailTemplateType getEmailTemplateType() {
		return emailTemplateType;
	}

	public void setEmailTemplateType(EmailTemplateType emailTemplateType) {
		this.emailTemplateType = emailTemplateType;
	}
	
	

	
}
