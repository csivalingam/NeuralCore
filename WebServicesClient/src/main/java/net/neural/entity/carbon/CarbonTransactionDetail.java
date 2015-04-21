package net.zfp.entity.carbon;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="CarbonTransactionDetail" )
@XmlRootElement(name="carbonTransaction")
@XmlAccessorType(XmlAccessType.FIELD)

public class CarbonTransactionDetail extends BaseEntity{

	private static final long serialVersionUID = 6839277385951612500L;
	
	@XmlAttribute(name="UPC")
	private String UPC;
	
	@XmlAttribute(name="quantity")
	private Double quantity;
	
	@XmlAttribute(name="unitListPrice")
	private Double unitListPrice;
	
	@XmlAttribute(name="unitSalePrice")
	private Double unitSalePrice;
	
	@XmlAttribute(name="totalSalePrice")
	private Double totalSalePrice;
	
	@XmlElement(name="CarbonTransaction")
	@OneToOne
	@JoinColumn(name="carbonTransactionId")
	private CarbonTransaction carbonTransaction;
	
	public CarbonTransactionDetail(){}
	
	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitListPrice() {
		return unitListPrice;
	}

	public void setUnitListPrice(Double unitListPrice) {
		this.unitListPrice = unitListPrice;
	}

	public Double getUnitSalePrice() {
		return unitSalePrice;
	}

	public void setUnitSalePrice(Double unitSalePrice) {
		this.unitSalePrice = unitSalePrice;
	}

	public Double getTotalSalePrice() {
		return totalSalePrice;
	}

	public void setTotalSalePrice(Double totalSalePrice) {
		this.totalSalePrice = totalSalePrice;
	}

	public CarbonTransaction getCarbonTransaction() {
		return carbonTransaction;
	}

	public void setCarbonTransaction(CarbonTransaction carbonTransaction) {
		this.carbonTransaction = carbonTransaction;
	}

	
}
