package net.zfp.form;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="NewsForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class NewsForm {
		
	@XmlElement(name="news")
	private String news;
	@XmlElement(name="heading")
	private String heading;
	@XmlElement(name="typeId")
	private Long typeId;
	@XmlElement(name="memberId")
	private Long memberId;
	@XmlElement(name="domain")
	private String domain;
	@XmlElement(name="imageUrl")
	private String imageUrl;
	@XmlElement(name="mobileImageUrl")
	private String mobileImageUrl;
	@XmlElement(name="reference")
	private String reference;
	@XmlElement(name="date")
	private Date date;
	@XmlElement(name="trackerTypeId")
	private Long trackerTypeId;
	
	public NewsForm() {
	}

	public Long getTrackerTypeId() {
		return trackerTypeId;
	}

	public void setTrackerTypeId(Long trackerTypeId) {
		this.trackerTypeId = trackerTypeId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
}
