package net.zfp.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resourceConsumption")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceConsumption {

	@XmlAttribute(name="buildings")
	List<Building> buildings = new ArrayList<Building>();

	public ResourceConsumption() {
		super();
	}

	public ResourceConsumption(List<Building> buildings) {
		super();
		this.buildings = buildings;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

}
