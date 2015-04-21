package net.zfp.entity.salesorder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="SalesOrderDetail" )
@XmlRootElement(name="SalesOrderDetail")
@XmlAccessorType(XmlAccessType.FIELD)

public class SalesOrderDetail extends BaseEntity{ 

	private static final long serialVersionUID = 6839277385951612500L;
	
	@XmlElement(name="SalesOrderType")
	@OneToOne
	@JoinColumn(name="type")
	private SalesOrderType salesOrderType;
	
	@XmlAttribute(name="UPC")
	private String UPC;
	
	@XmlAttribute(name="SKU")
	private String SKU;
	
	@XmlAttribute(name="quantity")
	private Double quantity;
	
	@XmlAttribute(name="unitListPrice")
	private Double unitListPrice;
	
	@XmlAttribute(name="unitSalePrice")
	private Double unitSalePrice;
	
	@XmlAttribute(name="totalSalePrice")
	private Double totalSalePrice;
	
	@XmlElement(name="SalesOrder")
	@OneToOne
	@JoinColumn(name="salesOrderNumber", referencedColumnName= "orderNumber")
	private SalesOrder salesOrder;
	
	@XmlAttribute(name="unitListPoints")
	private Integer unitListPoints;
	
	@XmlAttribute(name="unitSalePoints")
	private Integer unitSalePoints;
	
	@XmlAttribute(name="totalSalePoints")
	private Integer totalSalePoints;
	
	public SalesOrderDetail() {
	}
	

	public SalesOrderType getSalesOrderType() {
		return salesOrderType;
	}


	public void setSalesOrderType(SalesOrderType salesOrderType) {
		this.salesOrderType = salesOrderType;
	}


	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
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
	
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}


	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}


	public Integer getUnitListPoints() {
		return unitListPoints;
	}

	public void setUnitListPoints(Integer unitListPoints) {
		this.unitListPoints = unitListPoints;
	}

	public Integer getUnitSalePoints() {
		return unitSalePoints;
	}

	public void setUnitSalePoints(Integer unitSalePoints) {
		this.unitSalePoints = unitSalePoints;
	}

	public Integer getTotalSalePoints() {
		return totalSalePoints;
	}

	public void setTotalSalePoints(Integer totalSalePoints) {
		this.totalSalePoints = totalSalePoints;
	}
	
	
}
