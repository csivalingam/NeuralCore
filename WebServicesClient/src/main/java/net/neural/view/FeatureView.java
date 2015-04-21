package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.feature.Feature;

@XmlRootElement(name="FeatureView")
@XmlAccessorType(XmlAccessType.FIELD)
public class FeatureView {

	@XmlAttribute(name="name")
	private String name;
	
	public FeatureView() {
		super();
	}
	
	public FeatureView(Feature feature){
		this.name = feature.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
