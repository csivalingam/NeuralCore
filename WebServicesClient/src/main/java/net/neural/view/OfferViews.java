package net.zfp.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.offer.Offer;
import net.zfp.util.AppConstants;
import net.zfp.util.DateUtil;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;


@XmlRootElement(name="OfferViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class OfferViews {
	
	
	@XmlElement
	private List<OfferView> offerViews;
	
	@XmlElement
	private ResultView result;
	
	public OfferViews(){}
	
	
	public List<OfferView> getOfferViews() {
		return offerViews;
	}


	public void setOfferViews(List<OfferView> offerViews) {
		this.offerViews = offerViews;
	}


	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
