package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.product.ProductBanner;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;



@XmlRootElement(name="ProductBanner")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class ProductBannerView {
	
	@XmlElement
	private String productUPC;
	
	@XmlElement
	private String bannerImageUrl;
	
	@XmlElement
	private ResultView result;
	
	public ProductBannerView(){}
	
	public ProductBannerView(ProductBanner productBanner){
		if (productBanner != null){
			if(productBanner.getProduct() != null) this.productUPC = productBanner.getProduct().getUPC();
			this.bannerImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(productBanner.getBannerImageUrl());
		}
	}
	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
