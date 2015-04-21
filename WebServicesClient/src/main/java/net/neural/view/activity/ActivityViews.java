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

@XmlRootElement(name="ActivityViews")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class ActivityViews {

	@XmlElement
	private List<ActivityView> activityList = new ArrayList<ActivityView>();
	
	@XmlElement
	private ResultView result;
	
	public ActivityViews() {
	}
	
	public List<ActivityView> getActivityList() {
		return activityList;
	}



	public void setActivityList(List<ActivityView> activityList) {
		this.activityList = activityList;
	}



	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
