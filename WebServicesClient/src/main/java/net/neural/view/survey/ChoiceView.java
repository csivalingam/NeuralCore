package net.zfp.view.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="ChoiceView")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChoiceView {
		
	@XmlElement
	private String name;
	@XmlElement
	private String value;
	@XmlElement
	private Integer choiceCode;

	public ChoiceView() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getChoiceCode() {
		return choiceCode;
	}

	public void setChoiceCode(Integer choiceCode) {
		this.choiceCode = choiceCode;
	}
}
