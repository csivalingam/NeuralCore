package net.zfp.entity.group;

import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import net.zfp.entity.BaseEntity;
import net.zfp.entity.User;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferType;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="Groups")
@XmlRootElement(name="Groups")
@XmlAccessorType(XmlAccessType.FIELD)
public class Groups extends BaseEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlAttribute(name="accountId")
	private Long accountId;
	
	@XmlAttribute(name="communityId")
	private Long communityId;
	
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	@XmlAttribute(name="name")
	private String name;
	
	@XmlAttribute(name="description")
	private String description;
	
	@XmlElement(name="GroupsType")
	@OneToOne
	@JoinColumn(name="type")
	private GroupsType groupsType;
	
	public Groups() {}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	

	public Long getAccountId() {
		return accountId;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public Long getCommunityId() {
		return communityId;
	}


	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public GroupsType getGroupsType() {
		return groupsType;
	}


	public void setGroupsType(GroupsType groupsType) {
		this.groupsType = groupsType;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
   
}
