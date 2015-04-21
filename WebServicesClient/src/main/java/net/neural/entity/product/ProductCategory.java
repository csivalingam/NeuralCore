package net.zfp.entity.product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.category.Category;
import net.zfp.entity.category.MerchandisingCategory;

@Entity(name="ProductCategory")
@XmlRootElement(name="ProductCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductCategory extends DomainEntity {


	private static final long serialVersionUID = 7913769140950533298L;
	
	@XmlElement(name="Product")
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;
	
	@XmlElement(name="Category")
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	@XmlElement(name="MerchandisingCategory")
	@ManyToOne
	@JoinColumn(name="merchandisingCategoryId")
	private MerchandisingCategory merchandisingCategory;
	
	public ProductCategory() { }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public MerchandisingCategory getMerchandisingCategory() {
		return merchandisingCategory;
	}

	public void setMerchandisingCategory(MerchandisingCategory merchandisingCategory) {
		this.merchandisingCategory = merchandisingCategory;
	}
	
	
}