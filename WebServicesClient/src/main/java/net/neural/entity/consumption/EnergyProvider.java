package net.zfp.entity.consumption;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="EnergyProvider")
@Table(name="ft_Provider")
@XmlRootElement(name="EnergyProvider")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnergyProvider extends DomainEntity{

	private static final long serialVersionUID = -5488878388736333305L;

	@XmlAttribute(name="code")
	private String code;
	@XmlAttribute(name="name")
	private String name;

	@XmlAttribute(name="clientId")
	private String clientId;
	@XmlAttribute(name="clientsecret")
	private String clientsecret;
	
	public EnergyProvider() {
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientsecret() {
		return clientsecret;
	}
	public void setClientsecret(String clientsecret) {
		this.clientsecret = clientsecret;
	}
	
	
	
	
}
