package net.zfp.view.survey;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;

/**
 * View implementation class for Entity: HistoryViews
 * 
 * @author Youngwook Yoo
 * @since 2014-09-05
 *
 */
@XmlRootElement(name="HistoryViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class HistoryViews {
	
	@XmlElement
	private List<HistoryView> historyViews;
	@XmlElement
	private ResultView result;
	
	public HistoryViews(){}

	public List<HistoryView> getHistoryViews() {
		return historyViews;
	}

	public void setHistoryViews(List<HistoryView> historyViews) {
		this.historyViews = historyViews;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
	
}
