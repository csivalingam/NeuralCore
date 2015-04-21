package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;

/**
 * View implementation class for Entity: CommunityViews
 * 
 * @author Youngwook Yoo
 * @since 2014-09-12
 *
 */
@XmlRootElement(name="CommunityViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class CommunityViews {
	
	@XmlElement
	private List<CommunityView> communityViews;
	@XmlElement
	private ResultView result;
	
	public CommunityViews(){}
	
	public List<CommunityView> getCommunityViews() {
		return communityViews;
	}

	public void setCommunityViews(List<CommunityView> communityViews) {
		this.communityViews = communityViews;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
	
}
