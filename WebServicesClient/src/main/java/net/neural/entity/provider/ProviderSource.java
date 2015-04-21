package net.zfp.entity.provider;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.Source;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="ProviderSource")
@XmlRootElement(name="ProviderSource")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderSource extends DomainEntity {
	
	@XmlElement(name="Provider")
	@OneToOne
	@JoinColumn(name="providerId")
	private Provider provider;
	
	@XmlElement(name="Source")
	@OneToOne
	@JoinColumn(name="sourceId")
	private Source source;
	
	@XmlAttribute(name="username")
	private String username;
	
	@XmlAttribute(name="password")
	private String password;
	
	public ProviderSource() {}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
   
	
}
