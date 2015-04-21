package net.zfp.entity.survey;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SurveyAnswerValueType")
@XmlRootElement(name="SurveyAnswerValueType")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyAnswerValueType extends DomainEntity {

	private static final long serialVersionUID = 829164429415846948L;
	
	@XmlAttribute(name="type")
	private String type;
	
	public SurveyAnswerValueType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
