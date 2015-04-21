package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.feature.FeatureTravelType;

@XmlRootElement(name="travelTypeView")
@XmlAccessorType(XmlAccessType.FIELD)
public class TravelTypeView {

	@XmlAttribute(name="type")
	private String type;
	@XmlAttribute(name="name")
	private String name;
	
	public TravelTypeView() {
		super();
	}

	public TravelTypeView(FeatureTravelType ftt) {
		this.type = ftt.getTravelType().getType();
		this.name = ftt.getTravelType().getName();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
