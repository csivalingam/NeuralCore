package net.zfp.entity.channel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.User;
import net.zfp.entity.memberactivity.MemberActivityType;

@Entity(name="ChannelActivity")
@XmlRootElement(name="ChannelActivity")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelActivity extends DomainEntity{

	private static final long serialVersionUID = -518297474284710014L;
	
	@XmlElement(name="ChannelType")
	@OneToOne
	@JoinColumn(name="channelTypeId")
	private ChannelType channelType;
	
	@XmlElement(name="user")
	@OneToOne
	@JoinColumn(name="memberId")
	private User member;
	
	@XmlAttribute(name="date")
	private Date date;
	
	@XmlElement(name="MemberActivityType")
	@OneToOne
	@JoinColumn(name="activityTypeId")
	private MemberActivityType memberActivityType;
	
	public ChannelActivity() {}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MemberActivityType getMemberActivityType() {
		return memberActivityType;
	}

	public void setMemberActivityType(MemberActivityType memberActivityType) {
		this.memberActivityType = memberActivityType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
