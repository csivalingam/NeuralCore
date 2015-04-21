package net.zfp.entity.membership;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.community.Community;
import net.zfp.entity.newscontent.NewsContentType;
import net.zfp.entity.segment.Segment;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="MembershipNews")
@XmlRootElement(name="MembershipNews")
@XmlAccessorType(XmlAccessType.FIELD)
public class MembershipNews extends DomainEntity {
	
	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlAttribute(name="created")
	private Date created;
	
	@XmlAttribute(name="published")
	private Date published;
	
	@XmlAttribute(name="news")
	private String news;
	
	@XmlAttribute(name="heading")
	private String heading;
	
	@XmlElement(name="NewsContentType")
	@OneToOne
	@JoinColumn(name="typeId")
	private NewsContentType type;
	
	@XmlElement(name="Segment")
	@OneToOne
	@JoinColumn(name="segmentId")
	private Segment segment;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="mobileImageUrl")
	private String mobileImageUrl;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlAttribute(name="referenceUrl")
	private String referenceUrl;
	
	@XmlAttribute(name="supressedMobile")
	private Boolean supressedMobile;
	
	public MembershipNews() {}

	public Boolean getSupressedMobile() {
		return supressedMobile;
	}

	public void setSupressedMobile(Boolean supressedMobile) {
		this.supressedMobile = supressedMobile;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}
	
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public NewsContentType getType() {
		return type;
	}

	public void setType(NewsContentType type) {
		this.type = type;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getMobileImageUrl() {
		return mobileImageUrl;
	}

	public void setMobileImageUrl(String mobileImageUrl) {
		this.mobileImageUrl = mobileImageUrl;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	
	
}
