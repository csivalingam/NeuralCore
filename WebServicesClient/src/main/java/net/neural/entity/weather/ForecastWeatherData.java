package net.zfp.entity.weather;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Location;

@Entity(name="ForecastWeatherData")
@XmlRootElement(name="ForecastWeatherData")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastWeatherData extends DomainEntity {
	
	private static final long serialVersionUID = 12123131545461L;

	@XmlElement(name="Location")
	@ManyToOne
	@JoinColumn(name="weatherLocationId")
	private Location location;

	@XmlAttribute(name="forecastDate")
	private Date forecastDate;
	
	@XmlAttribute(name="highTemp")
	private Integer highTemp;
	
	@XmlAttribute(name="lowTemp")
	private Integer lowTemp;
	
	@XmlAttribute(name="condition")
	private String condition;
	
	public ForecastWeatherData() { }

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getForecastDate() {
		return forecastDate;
	}

	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
	}

	public Integer getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(Integer highTemp) {
		this.highTemp = highTemp;
	}

	public Integer getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(Integer lowTemp) {
		this.lowTemp = lowTemp;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
