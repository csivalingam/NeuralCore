package net.zfp.view.personalmotion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.personalmotion.PersonalMotion;


@XmlRootElement(name="MonthlySummaryView")
@XmlAccessorType(XmlAccessType.FIELD)
public class MonthlySummaryView {
	
	@XmlAttribute(name="highestValue")
	private Integer highestValue;
	
	@XmlElement(name="firstQuarter")
	private List<MonthlyView> firstQuarter;
	
	@XmlElement(name="secondQuarter")
	private List<MonthlyView> secondQuarter;
	
	@XmlElement(name="thirdQuarter")
	private List<MonthlyView> thirdQuarter;
	
	@XmlElement(name="fourthQuarter")
	private List<MonthlyView> fourthQuarter;
	
	public MonthlySummaryView() {
		firstQuarter = new ArrayList<MonthlyView>();
		secondQuarter = new ArrayList<MonthlyView>();
		thirdQuarter = new ArrayList<MonthlyView>();
		fourthQuarter = new ArrayList<MonthlyView>();
	}
	
	public Integer getHighestValue() {
		return highestValue;
	}

	public void setHighestValue(Integer highestValue) {
		this.highestValue = highestValue;
	}

	public List<MonthlyView> getFirstQuarter() {
		return firstQuarter;
	}

	public void setFirstQuarter(List<MonthlyView> firstQuarter) {
		this.firstQuarter = firstQuarter;
	}

	public List<MonthlyView> getSecondQuarter() {
		return secondQuarter;
	}

	public void setSecondQuarter(List<MonthlyView> secondQuarter) {
		this.secondQuarter = secondQuarter;
	}

	public List<MonthlyView> getThirdQuarter() {
		return thirdQuarter;
	}

	public void setThirdQuarter(List<MonthlyView> thirdQuarter) {
		this.thirdQuarter = thirdQuarter;
	}

	public List<MonthlyView> getFourthQuarter() {
		return fourthQuarter;
	}

	public void setFourthQuarter(List<MonthlyView> fourthQuarter) {
		this.fourthQuarter = fourthQuarter;
	}
	
	
}
