package net.zfp.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="building")
@XmlAccessorType(XmlAccessType.FIELD)

public class Building {

	@XmlAttribute(name="id")
	private long id;

	@XmlAttribute(name="providerId")
	private long providerId;

	@XmlAttribute(name="name")
	private String name;

	@XmlAttribute(name="description")
	private String description;
	
	@XmlAttribute(name="estimated")
	private boolean estimated = false;

	//@XmlElements ( @XmlElement(name="meter", type=Meter.class) )
	//@XmlElementWrapper(name="meters")
	@XmlElements ( value = {
        @XmlElement(name="meter", type=Meter.class)
	} ) 
	private List<Meter> meters = new ArrayList<Meter>();

	public Building() {
	}

	public Building(String name) {
		this.name = name;
	}

	public Building(long id, long providerId, String name, String description, boolean estimated, List<Meter> meters) {
		super();
		this.id = id;
		this.providerId =providerId;
		this.name = name;
		this.description = description;
		this.estimated = estimated;
		this.meters = meters;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Meter> getMeters() {
		return meters;
	}

	public void setMeters(List<Meter> meters) {
		this.meters = meters;
	}

	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public boolean isEstimated() {
		return estimated;
	}

	public void setEstimated(boolean estimated) {
		this.estimated = estimated;
	}
	
}
