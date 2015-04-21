package net.zfp.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SurveyForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class SurveyForm {
	

	@XmlElement(name="accountId")
	private Long accountId;
	
	@XmlElement(name="surveyAnswerParserString")
	private String surveyAnswerParserString;
	
	@XmlElement(name="domainName")
	private String domainName ;
	
	
	public SurveyForm() {
	}


	public Long getAccountId() {
		return accountId;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public String getSurveyAnswerParserString() {
		return surveyAnswerParserString;
	}


	public void setSurveyAnswerParserString(String surveyAnswerParserString) {
		this.surveyAnswerParserString = surveyAnswerParserString;
	}


	public String getDomainName() {
		return domainName;
	}


	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	
}
