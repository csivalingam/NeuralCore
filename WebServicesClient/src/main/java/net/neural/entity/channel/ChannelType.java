package net.zfp.entity.channel;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="ChannelType")
@XmlRootElement(name="ChannelType")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelType extends DomainEntity{

	private static final long serialVersionUID = -518297474284710014L;
	@XmlAttribute(name="type")
	private String type;
	
	public ChannelType() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
