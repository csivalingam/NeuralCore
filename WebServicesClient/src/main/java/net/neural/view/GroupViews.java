package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="Groups")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class GroupViews {
	
	@XmlElement
	private List<GroupView> group;
	
	@XmlElement
	private ResultView result;
	
	public GroupViews() {}

	public List<GroupView> getGroup() {
		return group;
	}

	public void setGroup(List<GroupView> group) {
		this.group = group;
	}

	public ResultView getResult() {
		return result;
	}


	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
}
