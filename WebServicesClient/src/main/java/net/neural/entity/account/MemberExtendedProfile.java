package net.zfp.entity.account;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.Location;
import net.zfp.entity.User;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="MemberExtendedProfile")
@XmlRootElement(name="MemberExtendedProfile")
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberExtendedProfile extends BaseEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="User")
	@OneToOne
	@JoinColumn(name="memberId")
	private User user;
	
	@XmlElement(name="Location")
	@OneToOne
	@JoinColumn(name="locationIdForWeather")
	private Location location;
	
	@XmlAttribute(name="profileImageUrl")
	private String profileImageUrl;
	
	@XmlAttribute(name="occupants")
	private Integer occupants;
	
	@XmlAttribute(name="rooms")
	private Double rooms;
	
	public MemberExtendedProfile() {}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Integer getOccupants() {
		return occupants;
	}

	public void setOccupants(Integer occupants) {
		this.occupants = occupants;
	}

	public Double getRooms() {
		return rooms;
	}

	public void setRooms(Double rooms) {
		this.rooms = rooms;
	}
	
	
	
}
