package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="MerchandisingCategorys")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class MerchandisingCategoryViews {
	
	@XmlElement
	private List<MerchandisingCategoryView> merchandisingCategoryViews;
	
	@XmlElement
	private ResultView result;
	
	public MerchandisingCategoryViews(){}
	
	
	public List<MerchandisingCategoryView> getMerchandisingCategoryViews() {
		return merchandisingCategoryViews;
	}


	public void setMerchandisingCategoryViews(List<MerchandisingCategoryView> merchandisingCategoryViews) {
		this.merchandisingCategoryViews = merchandisingCategoryViews;
	}


	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
}
