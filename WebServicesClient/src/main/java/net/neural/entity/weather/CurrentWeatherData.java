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

@Entity(name="CurrentWeatherData")
@XmlRootElement(name="CurrentWeatherData")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrentWeatherData extends DomainEntity {
	
	private static final long serialVersionUID = 123131545461L;

	@XmlElement(name="Location")
	@ManyToOne
	@JoinColumn(name="weatherLocationId")
	private Location location;

	@XmlAttribute(name="chill")
	private Integer chill;
	
	@XmlAttribute(name="condition")
	private String condition;
	
	@XmlAttribute(name="humidity")
	private Integer humidity;
	
	@XmlAttribute(name="visibility")
	private Double visibility;
	
	@XmlAttribute(name="temp")
	private Integer temp;
	
	@XmlAttribute(name="HDD")
	private Double HDD;
	
	@XmlAttribute(name="CDD")
	private Double CDD;
	
	@XmlAttribute(name="publishedDate")
	private Date publishedDate;
	
	public CurrentWeatherData() { }

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Integer getChill() {
		return chill;
	}

	public void setChill(Integer chill) {
		this.chill = chill;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public Double getVisibility() {
		return visibility;
	}

	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}

	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Double getHDD() {
		return HDD;
	}

	public void setHDD(Double hDD) {
		HDD = hDD;
	}

	public Double getCDD() {
		return CDD;
	}

	public void setCDD(Double cDD) {
		CDD = cDD;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	
	
}
