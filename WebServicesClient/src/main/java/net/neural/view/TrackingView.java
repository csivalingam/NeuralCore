package net.zfp.view;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.Airport;

@XmlRootElement(name="trackingView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso( {Airport.class} )
public class TrackingView {

	@XmlAttribute(name="trackingId")
	private String trackingId;

	public TrackingView(){}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	
	
}
