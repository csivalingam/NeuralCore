package net.zfp.entity.survey;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.IntegerEntity;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SurveyQuestionType")
@XmlRootElement(name="SurveyQuestionType")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyQuestionType extends IntegerEntity {

	private static final long serialVersionUID = 829164429415846950L;
	
	@XmlAttribute(name="type")
	private String type;
	
	public SurveyQuestionType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
