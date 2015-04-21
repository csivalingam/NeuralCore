package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.VehicleType;

@XmlRootElement(name="VehicleView")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleView {
	
	@XmlAttribute(name="type")
	private Integer type;
	
	@XmlAttribute(name="name")
	private String name;
	
	public VehicleView() {
		super();
	}
	
	public VehicleView(VehicleType vehicle){
		this.type = vehicle.getType();
		this.name = vehicle.getName();
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
