package net.zfp.entity.feature;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.TravelType;
import net.zfp.entity.community.CommunityFeature;

@Entity(name="FeatureTravelType")
@XmlRootElement(name="FeatureTravelType")
@XmlAccessorType(XmlAccessType.FIELD)
public class FeatureTravelType extends DomainEntity{

	private static final long serialVersionUID = -1317675426837027468L;

	@XmlElement(name="CommunityFeature")
	@ManyToOne
	@JoinColumn(name="communityFeatureId")
	private CommunityFeature communityFeature;
	
	@XmlAttribute(name="TravelType")
	@ManyToOne
	@JoinColumn(name="travelTypeId")
	private TravelType travelType;
		
	public FeatureTravelType() {
	}

	public CommunityFeature getCommunityFeature() {
		return communityFeature;
	}

	public void setCommunityFeature(CommunityFeature communityFeature) {
		this.communityFeature = communityFeature;
	}

	public TravelType getTravelType() {
		return travelType;
	}

	public void setTravelType(TravelType travelType) {
		this.travelType = travelType;
	}

		
}
