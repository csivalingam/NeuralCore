package net.zfp.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Airport" )
@XmlRootElement(name="airport")
@XmlAccessorType(XmlAccessType.FIELD)
public class Airport extends DomainEntity{

	private static final long serialVersionUID = 1110417722883271203L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="city")
	private String city;
	@XmlAttribute(name="country")
	private String country;
	@XmlAttribute(name="iata")
	private String iata;
	@XmlAttribute(name="icao")
	private String icao;
	@XmlAttribute(name="latitude")
	private double latitude;
	@XmlAttribute(name="longitude")
	private double longitude;
	@XmlAttribute(name="altitude")
	private double altitude;
	@XmlAttribute(name="timezone")
	private double timezone;
	@XmlAttribute(name="dst")
	private String dst;
	
	public Airport() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getTimezone() {
		return timezone;
	}

	public void setTimezone(double timezone) {
		this.timezone = timezone;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}
	
}
