package net.zfp.entity.source;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.Unit;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="SourceUnit" )
@XmlRootElement(name="SourceUnit")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceUnit extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;
	
	@XmlElement(name="SourceType")
	@ManyToOne
	@JoinColumn(name="sourceTypeId")
	private SourceType sourceType;
	
	@XmlElement(name="Unit")
	@ManyToOne
	@JoinColumn(name="unitId")
	private Unit unit;
	
	public SourceUnit(){}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	

}
