package net.zfp.view.survey;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * View implementation class for Entity: QuestionView
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@XmlRootElement(name="QuestionView")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuestionView {
	
	@XmlElement
	private Long questionId;
	
	@XmlElement
	private Integer questionType;
	
	@XmlElement
	private Long answerValueType;
	
	@XmlElement
	private String questionName;
	
	@XmlElement
	private String operator;
	
	@XmlElement
	private Double factor;
	
	@XmlElement
	private String referenceOperator;
	
	@XmlElement
	private Double reference;
	
	@XmlElement
	private List<ChoiceView> choiceViews;
	
	@XmlElement
	private RangeView rangeViews;

	@XmlElement
	private Boolean isMandatory = false;
	
	@XmlElement
	private String moreInfoImage;
	
	public QuestionView() {
		choiceViews = new ArrayList<ChoiceView>();
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


	public String getQuestionName() {
		return questionName;
	}


	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}


	public List<ChoiceView> getChoiceViews() {
		return choiceViews;
	}


	public void setChoiceViews(List<ChoiceView> choiceViews) {
		this.choiceViews = choiceViews;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public Double getFactor() {
		return factor;
	}


	public void setFactor(Double factor) {
		this.factor = factor;
	}


	public String getReferenceOperator() {
		return referenceOperator;
	}


	public void setReferenceOperator(String referenceOperator) {
		this.referenceOperator = referenceOperator;
	}


	public Double getReference() {
		return reference;
	}


	public void setReference(Double reference) {
		this.reference = reference;
	}


	public Long getAnswerValueType() {
		return answerValueType;
	}


	public void setAnswerValueType(Long answerValueType) {
		this.answerValueType = answerValueType;
	}


	public RangeView getRangeViews() {
		return rangeViews;
	}


	public void setRangeViews(RangeView rangeViews) {
		this.rangeViews = rangeViews;
	}


	public Boolean getIsMandatory() {
		return isMandatory;
	}


	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}


	public String getMoreInfoImage() {
		return moreInfoImage;
	}


	public void setMoreInfoImage(String moreInfoImage) {
		this.moreInfoImage = moreInfoImage;
	}
	
	
}
