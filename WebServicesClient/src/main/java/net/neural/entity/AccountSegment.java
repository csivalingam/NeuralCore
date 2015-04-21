package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.segment.Segment;


@Entity(name="AccountSegment" )
@XmlRootElement(name="AccountSegment")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountSegment extends DomainEntity{

	private static final long serialVersionUID = 1110417722183271203L;

	@XmlElement(name="Segment")
	@ManyToOne
	@JoinColumn(name="segmentId")
	private Segment segment;

	@XmlElement(name="user")
	@ManyToOne
	@JoinColumn(name="accountId")
	private User user;
	
	public AccountSegment() {
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	
}
