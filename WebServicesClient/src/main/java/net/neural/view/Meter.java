package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlElement;

//@XmlRootElement( name = "meter")
@XmlElement( value = "meter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meter {

	@XmlAttribute(name="id")
	private long id;
	@XmlAttribute(name="type")
	private String type;
	@XmlAttribute(name="power")
	private double power;
	@XmlAttribute(name="energy")
	private double energy;
	@XmlAttribute(name="units")
	private String units;
	@XmlAttribute(name="timeStamp")
	private Date timeStamp;
	
	public Meter() {
	}
	
	public Meter(String type) {
		this.type = type;
	}

	public Meter (
			long id, 
			String type, 
			double power, 
			double energy, 
			String units,
			Date timeStamp
			) {
		super();
		this.id = id;
		this.type = type;
		this.power = power;
		this.energy = energy;
		this.units = units;
		this.timeStamp = timeStamp;
	}
	
//	public static String valueOf(String string){
//		return string;
//	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
