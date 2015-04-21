package net.zfp.entity.product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;


@Entity(name="ProductBanner")
@XmlRootElement(name="ProductBanner")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductBanner extends DomainEntity {


	private static final long serialVersionUID = 7913769140950533298L;
	
	@XmlElement(name="Product")
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;
	
	@XmlAttribute(name="bannerImageUrl")
	private String bannerImageUrl;
	
	public ProductBanner() { }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getBannerImageUrl() {
		return bannerImageUrl;
	}

	public void setBannerImageUrl(String bannerImageUrl) {
		this.bannerImageUrl = bannerImageUrl;
	}
	
	
}