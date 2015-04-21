package net.zfp.view.survey;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;

/**
 * View implementation class for Entity: SurveyViews
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@XmlRootElement(name="TipsViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class TipsViews {
	
	@XmlElement
	private List<TipsView> tipsViews;
	@XmlElement
	private ResultView result;
	
	public TipsViews(){}
	
	
	public List<TipsView> getTipsViews() {
		return tipsViews;
	}

	public void setTipsViews(List<TipsView> tipsViews) {
		this.tipsViews = tipsViews;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
