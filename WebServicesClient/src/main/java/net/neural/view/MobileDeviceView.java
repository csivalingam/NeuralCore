package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="MobileDeviceView")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileDeviceView {
		
	@XmlElement
	private String image;
	@XmlElement
	private String reference;
	
	public MobileDeviceView() {}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
}
