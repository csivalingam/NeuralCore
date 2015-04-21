package net.zfp.view.survey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;


/**
 * View implementation class for Entity: SurveyView
 * 
 * @author Youngwook Yoo
 * @since 2014-09-03
 *
 */
@XmlRootElement(name="TipsView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class TipsView {
	
	@XmlElement
	private String header;
	
	@XmlElement
	private String tips;
	
	@XmlElement
	private String imageUrl;
	
	public TipsView() {
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
