package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="SourceSummaryViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class SourceSummaryViews {
	
	@XmlElement
	private List<SourceSummaryView> sourceSummaryViews;
	
	@XmlElement
	private ResultView result;
	
	public SourceSummaryViews(){}

	public List<SourceSummaryView> getSourceSummaryViews() {
		return sourceSummaryViews;
	}

	public void setSourceSummaryViews(List<SourceSummaryView> sourceSummaryViews) {
		this.sourceSummaryViews = sourceSummaryViews;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
}