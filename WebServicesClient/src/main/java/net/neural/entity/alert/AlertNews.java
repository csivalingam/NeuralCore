package net.zfp.entity.alert;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.Status;
import net.zfp.entity.User;
import net.zfp.entity.community.Community;
import net.zfp.entity.newscontent.NewsContentType;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="AlertNews")
@XmlRootElement(name="AlertNews")
@XmlAccessorType(XmlAccessType.FIELD)
public class AlertNews extends DomainEntity {
	
	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlAttribute(name="created")
	private Date created;
	
	@XmlAttribute(name="news")
	private String news;
	
	@XmlAttribute(name="heading")
	private String heading;
	
	@XmlElement(name="NewsContentType")
	@OneToOne
	@JoinColumn(name="typeId")
	private NewsContentType type;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="memberId")
	private User user;
	
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
	
	@XmlAttribute(name="isViewed")
	private Boolean isViewed;
	
	@XmlAttribute(name="supressedMobile")
	private Boolean supressedMobile;
	
	@XmlAttribute(name="trackerTypeId")
	private Long trackerTypeId;
	
	@XmlAttribute(name="reference")
	private String reference;
	
	public AlertNews() {}

	
	public Long getTrackerTypeId() {
		return trackerTypeId;
	}


	public void setTrackerTypeId(Long trackerTypeId) {
		this.trackerTypeId = trackerTypeId;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}
	
	

	public Boolean getSupressedMobile() {
		return supressedMobile;
	}

	public void setSupressedMobile(Boolean supressedMobile) {
		this.supressedMobile = supressedMobile;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Boolean getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Boolean isViewed) {
		this.isViewed = isViewed;
	}
	
	
}
