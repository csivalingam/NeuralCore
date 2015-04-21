package net.zfp.entity.upload;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.offer.Offer;

@Entity(name="UploadImage")
@XmlRootElement(name="UploadImage")
@XmlAccessorType(XmlAccessType.FIELD)
public class UploadImage extends DomainEntity {


	private static final long serialVersionUID = 7913469140950533298L;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="date")
	private Date date;
	
	@XmlElement(name="Offer")
	@ManyToOne
	@JoinColumn(name="offerCode")
	private Offer offer;
	
	@XmlAttribute(name="code")
	private Long code;
	
	public UploadImage() { }

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
	
	
	
	
}