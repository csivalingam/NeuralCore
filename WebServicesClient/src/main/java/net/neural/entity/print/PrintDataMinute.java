package net.zfp.entity.print;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.FactorAttribute;
import net.zfp.entity.Source;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="PrintDataMinute" )
@XmlRootElement(name="PrintDataMinute")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrintDataMinute extends BaseEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	
	@XmlAttribute(name="paperUsage")
	private Integer paperUsage;
	
	@XmlAttribute(name="tonerUsage")
	private Double tonerUsage;
	
	@XmlAttribute(name="pagePrinted")
	private Integer pagePrinted;
	
	@XmlAttribute(name="pagePerPaper")
	private Integer pagePerPaper;
	
	@XmlElement(name="PrintType")
	@OneToOne
	@JoinColumn(name="printTypeId")
	private PrintType printTypeId;
	
	@XmlElement(name="PrintQuality")
	@OneToOne
	@JoinColumn(name="printQualityId")
	private PrintQuality printQualityId;
	
	@XmlAttribute(name="estimated")
	private Boolean estimated;
		
	public PrintDataMinute() { }

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Integer getPaperUsage() {
		return paperUsage;
	}

	public void setPaperUsage(Integer paperUsage) {
		this.paperUsage = paperUsage;
	}

	public Integer getPagePrinted() {
		return pagePrinted;
	}

	public void setPagePrinted(Integer pagePrinted) {
		this.pagePrinted = pagePrinted;
	}

	public Integer getPagePerPaper() {
		return pagePerPaper;
	}

	public void setPagePerPaper(Integer pagePerPaper) {
		this.pagePerPaper = pagePerPaper;
	}

	public Double getTonerUsage() {
		return tonerUsage;
	}

	public void setTonerUsage(Double tonerUsage) {
		this.tonerUsage = tonerUsage;
	}

	public PrintType getPrintTypeId() {
		return printTypeId;
	}

	public void setPrintTypeId(PrintType printTypeId) {
		this.printTypeId = printTypeId;
	}

	public PrintQuality getPrintQualityId() {
		return printQualityId;
	}

	public void setPrintQualityId(PrintQuality printQualityId) {
		this.printQualityId = printQualityId;
	}

	public Boolean getEstimated() {
		return estimated;
	}

	public void setEstimated(Boolean estimated) {
		this.estimated = estimated;
	}



   
}
