package net.zfp.entity.community;

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
import net.zfp.entity.newscontent.NewsContentType;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="CommunityNews")
@XmlRootElement(name="CommunityNews")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityNews extends DomainEntity {
	
	private static final long serialVersionUID = 819164129415846950L;
	
	@XmlAttribute(name="news")
	private String news;
	
	@XmlAttribute(name="heading")
	private String heading;
	
	@XmlElement(name="NewsContentType")
	@OneToOne
	@JoinColumn(name="typeId")
	private NewsContentType type;
		
	@XmlAttribute(name="created")
	private Date created;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlElement(name="Community")
	@OneToOne
	@JoinColumn(name="communityId")
	private Community community;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="mobileImageUrl")
	private String mobileImageUrl;
	
	@XmlAttribute(name="referenceUrl")
	private String referenceUrl;
	
	@XmlAttribute(name="supressedMobile")
	private Boolean supressedMobile;
	
	public CommunityNews() {}
	
	
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


	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}
	
	public NewsContentType getType() {
		return type;
	}

	public void setType(NewsContentType type) {
		this.type = type;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	
}
