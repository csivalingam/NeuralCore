package net.zfp.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CarbonDetailForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class CarbonDetailForm {
	
	@XmlElement(name="productId")
	private Long productId;
	
	@XmlElement(name="sku")
	private String sku;
	@XmlElement(name="quantity")
	private Double quantity;
	@XmlElement(name="unitPrice")
	private Double unitPrice;
	@XmlElement(name="totalPrice")
	private Double totalPrice;
	
	@XmlElement(name="domainName")
	private String domainName;
	@XmlElement(name="accountEmail")
	private String accountEmail;
	
	public CarbonDetailForm() {
	}

	
	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}
	
	
}
