package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="hisotry")
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryView {

	@XmlAttribute(name="date")
	private String date;
	@XmlAttribute(name="value")
	private Double value;
	
	public HistoryView() {
		super();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
	
}
