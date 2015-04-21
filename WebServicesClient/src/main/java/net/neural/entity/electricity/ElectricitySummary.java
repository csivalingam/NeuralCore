package net.zfp.entity.electricity;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Source;


/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ElectricitySummary")
@XmlRootElement(name="ElectricitySummary")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectricitySummary extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="endDate")
	@Column(nullable = false)
	private Date endDate = new Date();

	@XmlAttribute(name="startDate")
	@Column(nullable = false)
	private Date startDate = new Date();
	
	@XmlElement(name="Source")
	@OneToOne
	@JoinColumn(name="sourceId")
	private Source source;

	@XmlAttribute(name="consumption")
	private Double consumption;

	public ElectricitySummary() { }

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Double getConsumption() {
		return consumption;
	}

	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

