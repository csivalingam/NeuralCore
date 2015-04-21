package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.alert.AlertNews;
import net.zfp.entity.community.CommunityNews;
import net.zfp.entity.membership.MembershipNews;
import net.zfp.util.AppConstants;

@XmlRootElement(name="NewsContentView")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsContentView {

	@XmlElement
	private String id;
	
	@XmlElement
	private String news;
	
	@XmlElement
	private String heading;
	
	@XmlElement
	private String remaining;
	
	@XmlElement
	private Boolean viewed;
	
	@XmlElement
	private Date date;
	
	@XmlElement
	private String imageUrl;
	
	@XmlElement
	private String mobileImageUrl;
	
	
	public NewsContentView() {
	}
	
	public NewsContentView(CommunityNews news) {
		this.id = "c" + news.getId();
		this.news = news.getNews();
		this.date = news.getCreated();
		this.heading = news.getHeading();
		if (news.getImageUrl() != null && !news.getImageUrl().equals("") && !news.getImageUrl().equals("null")) this.imageUrl = AppConstants.APACHE_IMAGE_LINK + news.getImageUrl();
		else this.imageUrl = AppConstants.APACHE_IMAGE_LINK + AppConstants.NO_IMAGE_WHATS_UP;
		
		if (news.getMobileImageUrl() != null && !news.getMobileImageUrl().equals("") && !news.getImageUrl().equals("null")) this.mobileImageUrl = AppConstants.APACHE_IMAGE_LINK + news.getMobileImageUrl();
		else this.mobileImageUrl = AppConstants.APACHE_IMAGE_LINK + AppConstants.NO_IMAGE_WHATS_UP;
		
	}
	
	public NewsContentView(AlertNews news) {
		this.id = "a" +news.getId();
		this.news = news.getNews();
		this.date = news.getCreated();
		this.heading = news.getHeading();
		if (news.getIsViewed()) this.viewed = true;
		else this.viewed = false;
		if (news.getImageUrl() != null && !news.getImageUrl().equals("") && !news.getImageUrl().equals("null")) this.imageUrl = AppConstants.APACHE_IMAGE_LINK + news.getImageUrl();
		else this.imageUrl = AppConstants.APACHE_IMAGE_LINK + AppConstants.NO_IMAGE_WHATS_UP;
		
		if (news.getMobileImageUrl() != null && !news.getMobileImageUrl().equals("") && !news.getImageUrl().equals("null")) this.mobileImageUrl = AppConstants.APACHE_IMAGE_LINK + news.getMobileImageUrl();
		else this.mobileImageUrl = AppConstants.APACHE_IMAGE_LINK + AppConstants.NO_IMAGE_WHATS_UP;
		
	}
	
	public NewsContentView(MembershipNews news) {
		this.id = "m" + news.getId();
		this.news = news.getNews();
		this.date = news.getPublished();
		this.heading = news.getHeading();
		if (news.getImageUrl() != null && !news.getImageUrl().equals("") && !news.getImageUrl().equals("null")) this.imageUrl = AppConstants.APACHE_IMAGE_LINK + news.getImageUrl();
		else this.imageUrl = AppConstants.APACHE_IMAGE_LINK + AppConstants.NO_IMAGE_WHATS_UP;
		
		if (news.getMobileImageUrl() != null && !news.getMobileImageUrl().equals("") && !news.getImageUrl().equals("null")) this.mobileImageUrl = AppConstants.APACHE_IMAGE_LINK + news.getMobileImageUrl();
		else this.mobileImageUrl = AppConstants.APACHE_IMAGE_LINK + AppConstants.NO_IMAGE_WHATS_UP;
		
	}
	

	public Boolean getViewed() {
		return viewed;
	}

	public void setViewed(Boolean viewed) {
		this.viewed = viewed;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNews() {
		return news;
	}


	public void setNews(String news) {
		this.news = news;
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
