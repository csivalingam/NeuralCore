package net.zfp.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.salesorder.SalesOrder;

@XmlRootElement(name="SalesOrder")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class SalesOrderView {

	@XmlElement
	private Long id;
	@XmlElement
	private Long orderNumber;
	@XmlElement
	private String referenceType;
	@XmlElement
	private Double subTotalCost;
	@XmlElement
	private Double tax;
	@XmlElement
	private String taxType;
	@XmlElement
	private Double totalCost;
	@XmlElement
	private String costUnit;
	@XmlElement
	private Integer totalPoints;
	@XmlElement
	private String receiptUrl;
	@XmlElement
	private String type;
	
	@XmlElement
	private String orderId;
	
	@XmlElement
	private Date timeStamp;
	
	@XmlElement
	private Double quantity;
	
	@XmlElement
	private String cardType;
	
	@XmlElement
	private String cardNumber;
	
	@XmlElement
	private String paymentReferenceId;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String address;
	
	@XmlElement
	private String pdfURL;
	
	@XmlElement(name="products")
	private List<ProductView> productViews;
	
	@XmlElement
	private ResultView result;
	
	public SalesOrderView() {
	}
	
	public SalesOrderView(SalesOrder t) {
		this.id = t.getId();
		this.orderNumber = t.getOrderNumber();
		this.referenceType = t.getSalesOrderReferenceType().getName();
		if (t.getSubTotalCost() != null) this.subTotalCost = t.getSubTotalCost();
		else this.subTotalCost = 0.0;
		if (t.getTax() != null) this.tax = t.getTax();
		else this.tax = 0.0;
		if (t.getTaxType() != null) this.taxType = t.getTaxType();
		else this.taxType = "";
		if (t.getTotalCost() != null) this.totalCost = t.getTotalCost();
		else this.totalCost = 0.0;
		if (t.getTotalPoints() != null) this.totalPoints = t.getTotalPoints();
		else this.totalPoints = 0;
		if (t.getCostUnit() != null) this.costUnit = t.getCostUnit();
		else this.costUnit = "CAD";
		
		if (t.getReceiptUrl() != null) this.receiptUrl = t.getReceiptUrl();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public Double getSubTotalCost() {
		return subTotalCost;
	}
	public void setSubTotalCost(Double subTotalCost) {
		this.subTotalCost = subTotalCost;
	}
	
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public String getCostUnit() {
		return costUnit;
	}
	public void setCostUnit(String costUnit) {
		this.costUnit = costUnit;
	}
	public Integer getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	public String getReceiptUrl() {
		return receiptUrl;
	}
	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPdfURL() {
		return pdfURL;
	}

	public void setPdfURL(String pdfURL) {
		this.pdfURL = pdfURL;
	}

	public List<ProductView> getProductViews() {
		return productViews;
	}

	public void setProductViews(List<ProductView> productViews) {
		this.productViews = productViews;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPaymentReferenceId() {
		return paymentReferenceId;
	}

	public void setPaymentReferenceId(String paymentReferenceId) {
		this.paymentReferenceId = paymentReferenceId;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
