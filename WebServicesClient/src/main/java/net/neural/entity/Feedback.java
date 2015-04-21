package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Feedback" )
@XmlRootElement(name="feedback")
@XmlAccessorType(XmlAccessType.FIELD)

public class Feedback extends BaseEntity{

	private static final long serialVersionUID = 7844502384801561348L;

	@XmlAttribute(name="email")
	private String email;
	@XmlAttribute(name="questionDesign")
	private Integer questionDesign;
	@XmlAttribute(name="questionEfficiency")
	private Integer questionEfficiency;
	@XmlAttribute(name="questionRelevance")
	private Integer questionRelevance;
	@XmlAttribute(name="questionHelpful")
	private Integer questionHelpful;
	@XmlAttribute(name="comments")
	private String comments;
	@XmlAttribute(name="status")
	private Long status;
	
	public Feedback() {	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getQuestionDesign() {
		return questionDesign;
	}

	public void setQuestionDesign(Integer questionDesign) {
		this.questionDesign = questionDesign;
	}

	public Integer getQuestionEfficiency() {
		return questionEfficiency;
	}

	public void setQuestionEfficiency(Integer questionEfficiency) {
		this.questionEfficiency = questionEfficiency;
	}

	public Integer getQuestionRelevance() {
		return questionRelevance;
	}

	public void setQuestionRelevance(Integer questionRelevance) {
		this.questionRelevance = questionRelevance;
	}

	public Integer getQuestionHelpful() {
		return questionHelpful;
	}

	public void setQuestionHelpful(Integer questionHelpful) {
		this.questionHelpful = questionHelpful;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	
}
