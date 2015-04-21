package net.zfp.view.activity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.view.ResultView;

@XmlRootElement(name="ActivityView")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class ActivityView {
	
	@XmlElement
	private String heading;

	@XmlElement
	private Double progress;
	
	@XmlElement
	private String unitImageUrl;
	
	@XmlElement
	private String unitDescription;
	
	@XmlElement
	private String unitName;

	@XmlElement
	private Double actual;
	
	@XmlElement
	private Double goal;
	
	@XmlElement
	private Integer sourceType;
	
	@XmlElement
	private List<ActivitySubView> activitySubViews = new ArrayList<ActivitySubView>();
	
	@XmlElement
	private ResultView result;
	
	public ActivityView() {
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public String getUnitImageUrl() {
		return unitImageUrl;
	}

	public void setUnitImageUrl(String unitImageUrl) {
		this.unitImageUrl = unitImageUrl;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getActual() {
		return actual;
	}

	public void setActual(Double actual) {
		this.actual = actual;
	}

	public Double getGoal() {
		return goal;
	}

	public void setGoal(Double goal) {
		this.goal = goal;
	}

	public List<ActivitySubView> getActivitySubViews() {
		return activitySubViews;
	}

	public void setActivitySubViews(List<ActivitySubView> activitySubViews) {
		this.activitySubViews = activitySubViews;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
