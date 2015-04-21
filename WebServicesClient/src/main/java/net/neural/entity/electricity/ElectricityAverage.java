package net.zfp.entity.electricity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.AverageType;
import net.zfp.entity.BaseEntity;
import net.zfp.entity.Source;
import net.zfp.entity.User;

@Entity(name="ElectricityAverage" )
@XmlRootElement(name="ElectricityAverage")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricityAverage extends BaseEntity {
	
	private static final long serialVersionUID = 819164429415146950L;
	
	@XmlElement(name="AverageType")
	@OneToOne
	@JoinColumn(name="averageType")
	private AverageType averageType;
	
	@XmlAttribute(name="hour00Average")
	private Double hour00Average;
	
	@XmlAttribute(name="hour01Average")
	private Double hour01Average;
	
	@XmlAttribute(name="hour02Average")
	private Double hour02Average;
	
	@XmlAttribute(name="hour03Average")
	private Double hour03Average;
	
	@XmlAttribute(name="hour04Average")
	private Double hour04Average;
	
	@XmlAttribute(name="hour05Average")
	private Double hour05Average;
	
	@XmlAttribute(name="hour06Average")
	private Double hour06Average;
	
	@XmlAttribute(name="hour07Average")
	private Double hour07Average;
	
	@XmlAttribute(name="hour08Average")
	private Double hour08Average;
	
	@XmlAttribute(name="hour09Average")
	private Double hour09Average;
	
	@XmlAttribute(name="hour10Average")
	private Double hour10Average;
	
	@XmlAttribute(name="hour11Average")
	private Double hour11Average;
	
	@XmlAttribute(name="hour12Average")
	private Double hour12Average;
	
	@XmlAttribute(name="hour13Average")
	private Double hour13Average;
	
	@XmlAttribute(name="hour14Average")
	private Double hour14Average;
	
	@XmlAttribute(name="hour15Average")
	private Double hour15Average;
	
	@XmlAttribute(name="hour16Average")
	private Double hour16Average;
	
	@XmlAttribute(name="hour17Average")
	private Double hour17Average;
	
	@XmlAttribute(name="hour18Average")
	private Double hour18Average;
	
	@XmlAttribute(name="hour19Average")
	private Double hour19Average;
	
	@XmlAttribute(name="hour20Average")
	private Double hour20Average;
	
	@XmlAttribute(name="hour21Average")
	private Double hour21Average;
	
	@XmlAttribute(name="hour22Average")
	private Double hour22Average;
	
	@XmlAttribute(name="hour23Average")
	private Double hour23Average;
	
	public ElectricityAverage() {
	}

	public AverageType getAverageType() {
		return averageType;
	}

	public void setAverageType(AverageType averageType) {
		this.averageType = averageType;
	}

	public Double getHour00Average() {
		return hour00Average;
	}

	public void setHour00Average(Double hour00Average) {
		this.hour00Average = hour00Average;
	}

	public Double getHour01Average() {
		return hour01Average;
	}

	public void setHour01Average(Double hour01Average) {
		this.hour01Average = hour01Average;
	}

	public Double getHour02Average() {
		return hour02Average;
	}

	public void setHour02Average(Double hour02Average) {
		this.hour02Average = hour02Average;
	}

	public Double getHour03Average() {
		return hour03Average;
	}

	public void setHour03Average(Double hour03Average) {
		this.hour03Average = hour03Average;
	}

	public Double getHour04Average() {
		return hour04Average;
	}

	public void setHour04Average(Double hour04Average) {
		this.hour04Average = hour04Average;
	}

	public Double getHour05Average() {
		return hour05Average;
	}

	public void setHour05Average(Double hour05Average) {
		this.hour05Average = hour05Average;
	}

	public Double getHour06Average() {
		return hour06Average;
	}

	public void setHour06Average(Double hour06Average) {
		this.hour06Average = hour06Average;
	}

	public Double getHour07Average() {
		return hour07Average;
	}

	public void setHour07Average(Double hour07Average) {
		this.hour07Average = hour07Average;
	}

	public Double getHour08Average() {
		return hour08Average;
	}

	public void setHour08Average(Double hour08Average) {
		this.hour08Average = hour08Average;
	}

	public Double getHour09Average() {
		return hour09Average;
	}

	public void setHour09Average(Double hour09Average) {
		this.hour09Average = hour09Average;
	}

	public Double getHour10Average() {
		return hour10Average;
	}

	public void setHour10Average(Double hour10Average) {
		this.hour10Average = hour10Average;
	}

	public Double getHour11Average() {
		return hour11Average;
	}

	public void setHour11Average(Double hour11Average) {
		this.hour11Average = hour11Average;
	}

	public Double getHour12Average() {
		return hour12Average;
	}

	public void setHour12Average(Double hour12Average) {
		this.hour12Average = hour12Average;
	}

	public Double getHour13Average() {
		return hour13Average;
	}

	public void setHour13Average(Double hour13Average) {
		this.hour13Average = hour13Average;
	}

	public Double getHour14Average() {
		return hour14Average;
	}

	public void setHour14Average(Double hour14Average) {
		this.hour14Average = hour14Average;
	}

	public Double getHour15Average() {
		return hour15Average;
	}

	public void setHour15Average(Double hour15Average) {
		this.hour15Average = hour15Average;
	}

	public Double getHour16Average() {
		return hour16Average;
	}

	public void setHour16Average(Double hour16Average) {
		this.hour16Average = hour16Average;
	}

	public Double getHour17Average() {
		return hour17Average;
	}

	public void setHour17Average(Double hour17Average) {
		this.hour17Average = hour17Average;
	}

	public Double getHour18Average() {
		return hour18Average;
	}

	public void setHour18Average(Double hour18Average) {
		this.hour18Average = hour18Average;
	}

	public Double getHour19Average() {
		return hour19Average;
	}

	public void setHour19Average(Double hour19Average) {
		this.hour19Average = hour19Average;
	}

	public Double getHour20Average() {
		return hour20Average;
	}

	public void setHour20Average(Double hour20Average) {
		this.hour20Average = hour20Average;
	}

	public Double getHour21Average() {
		return hour21Average;
	}

	public void setHour21Average(Double hour21Average) {
		this.hour21Average = hour21Average;
	}

	public Double getHour22Average() {
		return hour22Average;
	}

	public void setHour22Average(Double hour22Average) {
		this.hour22Average = hour22Average;
	}

	public Double getHour23Average() {
		return hour23Average;
	}

	public void setHour23Average(Double hour23Average) {
		this.hour23Average = hour23Average;
	}

	
}

