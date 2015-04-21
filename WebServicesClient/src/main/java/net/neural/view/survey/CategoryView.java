package net.zfp.view.survey;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * View implementation class for Entity: CategoryView
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@XmlRootElement(name="CategoryView")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryView {
	
	@XmlElement
	private Long categoryId;
	
	@XmlElement
	private String categoryName;
	
	@XmlElement
	private String imageUrl;
	
	@XmlElement
	private List<QuestionView> questionViews;
	
	@XmlElement
	private Double result;

	public CategoryView() {
		questionViews = new ArrayList<QuestionView>();
	}


	public Long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public List<QuestionView> getQuestionViews() {
		return questionViews;
	}


	public void setQuestionViews(List<QuestionView> questionViews) {
		this.questionViews = questionViews;
	}


	public Double getResult() {
		return result;
	}


	public void setResult(Double result) {
		this.result = result;
	}
	
	
}
