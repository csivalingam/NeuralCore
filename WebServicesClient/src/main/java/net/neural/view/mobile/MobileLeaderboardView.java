package net.zfp.view.mobile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.view.LeaderboardView;

@XmlRootElement(name="MobileLeaderboardView")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileLeaderboardView {
	
	@XmlAttribute(name="target")
	private Integer target;
	
	@XmlAttribute(name="targetUnit")
	private String targetUnit;
	
	@XmlElement(name="lbvs")
	private List<LeaderboardView> lbvs = new ArrayList<LeaderboardView>();
	
	public MobileLeaderboardView(){}

	
	public String getTargetUnit() {
		return targetUnit;
	}


	public void setTargetUnit(String targetUnit) {
		this.targetUnit = targetUnit;
	}


	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public List<LeaderboardView> getLbvs() {
		return lbvs;
	}

	public void setLbvs(List<LeaderboardView> lbvs) {
		this.lbvs = lbvs;
	}
	
	
}
