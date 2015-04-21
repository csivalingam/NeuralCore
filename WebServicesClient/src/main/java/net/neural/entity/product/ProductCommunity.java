package net.zfp.entity.product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.community.Community;

@Entity(name="ProductCommunity")
@XmlRootElement(name="ProductCommunity")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductCommunity extends DomainEntity {


	private static final long serialVersionUID = 7913769140950533298L;
	
	@XmlElement(name="Product")
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;
	
	@XmlElement(name="Community")
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	public ProductCommunity() { }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}
	
	
}