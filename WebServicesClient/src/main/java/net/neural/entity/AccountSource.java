package net.zfp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="AccountSource" )
@XmlRootElement(name="AccountSource")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountSource extends BaseEntity {


	private static final long serialVersionUID = 7916469140950533298L;

	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="account_id")
	private User user;
	
	@XmlElement(name="source")
	@OneToOne
	@JoinColumn(name="source_id")
	private Source source;
	
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlAttribute(name="defaultSource")
	private boolean defaultSource;
	
	public AccountSource() { }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isDefaultSource() {
		return defaultSource;
	}

	public void setDefaultSource(boolean defaultSource) {
		this.defaultSource = defaultSource;
	}
	
	
}