package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="ProductBanners")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class ProductBannerViews {
	
	@XmlElement
	private List<ProductBannerView> productBanners;
	
	@XmlElement
	private ResultView result;
	
	public ProductBannerViews(){}
	
	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	public List<ProductBannerView> getProductBanners() {
		return productBanners;
	}

	public void setProductBanners(List<ProductBannerView> productBanners) {
		this.productBanners = productBanners;
	}
	
	
}
