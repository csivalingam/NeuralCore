package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="PersonalMotionDailyView")
@XmlAccessorType(XmlAccessType.FIELD)
public class SponsorView {
	
	@XmlAttribute(name="headName")
	private String headName;
	
	@XmlAttribute(name="groupId")
	private Long groupId;
	
	@XmlElement(name="GroupDetailView")
	private List<GroupDetailView> GroupDetailView;
	
	public SponsorView() {
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<GroupDetailView> getGroupDetailView() {
		return GroupDetailView;
	}

	public void setGroupDetailView(List<GroupDetailView> groupDetailView) {
		GroupDetailView = groupDetailView;
	}

	
}
