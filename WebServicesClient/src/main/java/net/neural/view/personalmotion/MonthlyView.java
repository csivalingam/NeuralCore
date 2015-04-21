package net.zfp.view.personalmotion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.personalmotion.PersonalMotion;


@XmlRootElement(name="MonthlyView")
@XmlAccessorType(XmlAccessType.FIELD)
public class MonthlyView {
	
	@XmlAttribute(name="label")
	private String label;
	
	@XmlAttribute(name="title")
	private String title;
	
	@XmlAttribute(name="value")
	private Integer value;
	
	
	public MonthlyView() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
