package net.zfp.view;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="TaxView")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxView {
	
	@XmlAttribute(name="subTotalAmount")
	private double subTotalAmount;
	
	@XmlAttribute(name="taxAmount")
	private double taxAmount;
	
	@XmlAttribute(name="totalAmount")
	private double totalAmount;
	
	@XmlAttribute(name="taxType")
	private String taxType;
	

	public TaxView() {
	}


	public double getSubTotalAmount() {
		return subTotalAmount;
	}


	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}


	public double getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getTaxType() {
		return taxType;
	}


	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	
}
