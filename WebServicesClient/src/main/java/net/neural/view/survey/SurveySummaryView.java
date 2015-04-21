package net.zfp.view.survey;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.GaugeView;
import net.zfp.view.LeaderboardView;
import net.zfp.view.ResultView;

/**
 * View implementation class for Entity: SurveySummaryViews
 * 
 * @author Youngwook Yoo
 * @since 2014-09-11
 *
 */
@XmlRootElement(name="SurveySummaryView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class SurveySummaryView {
	
	@XmlElement
	private List<CategoryView> categoryViews;
	
	@XmlElement
	private ResultView result;

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	public List<CategoryView> getCategoryViews() {
		return categoryViews;
	}

	public void setCategoryViews(List<CategoryView> categoryViews) {
		this.categoryViews = categoryViews;
	}

}
