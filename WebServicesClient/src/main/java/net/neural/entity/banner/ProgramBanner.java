package net.zfp.entity.banner;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.community.Community;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ProgramBanner")
@XmlRootElement(name="ProgramBanner")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramBanner extends BaseEntity {
	
	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlAttribute(name="leftImageUrl")
	private String leftImageUrl;
	
	@XmlAttribute(name="leftTitle")
	private String leftTitle;
	@XmlAttribute(name="leftDescription")
	private String leftDescription;
	@XmlAttribute(name="rightImageUrl")
	private String rightImageUrl;
	@XmlAttribute(name="rightTitle")
	private String rightTitle;
	@XmlAttribute(name="rightDescription")
	private String rightDescription;
	@XmlAttribute(name="rank")
	private Integer rank;
	
	public ProgramBanner() {}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public String getLeftImageUrl() {
		return leftImageUrl;
	}

	public void setLeftImageUrl(String leftImageUrl) {
		this.leftImageUrl = leftImageUrl;
	}

	public String getLeftTitle() {
		return leftTitle;
	}

	public void setLeftTitle(String leftTitle) {
		this.leftTitle = leftTitle;
	}

	public String getLeftDescription() {
		return leftDescription;
	}

	public void setLeftDescription(String leftDescription) {
		this.leftDescription = leftDescription;
	}

	public String getRightImageUrl() {
		return rightImageUrl;
	}

	public void setRightImageUrl(String rightImageUrl) {
		this.rightImageUrl = rightImageUrl;
	}

	public String getRightTitle() {
		return rightTitle;
	}

	public void setRightTitle(String rightTitle) {
		this.rightTitle = rightTitle;
	}

	public String getRightDescription() {
		return rightDescription;
	}

	public void setRightDescription(String rightDescription) {
		this.rightDescription = rightDescription;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
	
}
