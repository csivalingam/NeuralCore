package net.zfp.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MobileSurveyForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class MobileSurveyForm {
	

	@XmlElement(name="email")
	private String email;
	@XmlElement(name="firstName")
	private String firstName;
	@XmlElement(name="lastName")
	private String lastName;
	@XmlElement(name="surveyId")
	private Long surveyId;
	
	@XmlElement(name="questionId")
	private Long questionId;
	@XmlElement(name="questionType")
	private Integer questionType;
	@XmlElement(name="value")
	private String value ;
	
	
	public MobileSurveyForm() {
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Long getSurveyId() {
		return surveyId;
	}


	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}


	public Long getQuestionId() {
		return questionId;
	}


	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}


	public Integer getQuestionType() {
		return questionType;
	}


	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
	
	
}
