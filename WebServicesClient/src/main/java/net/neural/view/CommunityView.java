package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.community.Community;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;

@XmlRootElement(name="CommunityView")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommunityView {
	
	@XmlElement
	private Long id;
	@XmlElement
	private String name;
	@XmlElement
	private String code;
	@XmlElement
	private Double latitude;
	@XmlElement
	private Double longitude;
	@XmlElement
	private String imageUrl;
	@XmlElement
	private String domain;
	@XmlElement
	private String national;
	@XmlElement
	private String communityType;
	@XmlElement
	private ResultView result;
	
	public CommunityView() {}

	public CommunityView(Community community){
		this.id = community.getId();
		this.name = community.getName();
		this.code = community.getCode();
		this.longitude = community.getLongitude();
		this.latitude = community.getLatitude();
		if (community.getCommunityType() != null) this.communityType = community.getCommunityType().getType();
		else this.communityType = AppConstants.COMMUNITY_TYPE_CONSUMER;
		if (community.getImageUrl() != null) this.imageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(community.getImageUrl());
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCommunityType() {
		return communityType;
	}

	public void setCommunityType(String communityType) {
		this.communityType = communityType;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
	
}
