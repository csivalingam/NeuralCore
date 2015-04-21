package net.zfp.entity.product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="ProductSKU")
@XmlRootElement(name="ProductSKU")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSKU extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;

	@XmlElement(name="Product")
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;
	
	@XmlAttribute(name="SKUId")
	private String SKUId;
	
	public ProductSKU() { }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSKUId() {
		return SKUId;
	}

	public void setSKUId(String sKUId) {
		SKUId = sKUId;
	}

	
	
	
}