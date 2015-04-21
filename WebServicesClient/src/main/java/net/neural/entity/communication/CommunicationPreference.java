package net.zfp.entity.communication;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.User;

@Entity(name="CommunicationPreference" )
@XmlRootElement(name="CommunicationPreference")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunicationPreference extends BaseEntity {


	private static final long serialVersionUID = 7913469140950533298L;

	@XmlElement(name="User")
	@OneToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlAttribute(name="receiveEmailAlert")
	private Boolean receiveEmailAlert;
	
	@XmlAttribute(name="receiveEmailNewsletter")
	private Boolean receiveEmailNewsletter;
	
	@XmlAttribute(name="receiveEmailOffer")
	private Boolean receiveEmailOffer;
	
	public CommunicationPreference() { }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getReceiveEmailAlert() {
		return receiveEmailAlert;
	}

	public void setReceiveEmailAlert(Boolean receiveEmailAlert) {
		this.receiveEmailAlert = receiveEmailAlert;
	}

	public Boolean getReceiveEmailNewsletter() {
		return receiveEmailNewsletter;
	}

	public void setReceiveEmailNewsletter(Boolean receiveEmailNewsletter) {
		this.receiveEmailNewsletter = receiveEmailNewsletter;
	}

	public Boolean getReceiveEmailOffer() {
		return receiveEmailOffer;
	}

	public void setReceiveEmailOffer(Boolean receiveEmailOffer) {
		this.receiveEmailOffer = receiveEmailOffer;
	}

		
	
}