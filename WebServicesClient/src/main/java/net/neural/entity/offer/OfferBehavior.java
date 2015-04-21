package net.zfp.entity.offer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BehaviorAttribute;
import net.zfp.entity.DomainEntity;
import net.zfp.entity.behavior.BehaviorAction;
import net.zfp.entity.behavior.BehaviorTrackerType;

/**
 * Entity implementation class for Entity: Image
 *
 */
@Entity(name="OfferBehavior")
@XmlRootElement(name="OfferBehavior")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferBehavior extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="BehaviorAction")
	@ManyToOne
	@JoinColumn(name="actionId")
	private BehaviorAction behaviorAction;
	
	@XmlElement(name="BehaviorTrackerType")
	@ManyToOne
	@JoinColumn(name="trackerTypeId")
	private BehaviorTrackerType behaviorTrackerType;
		
	public OfferBehavior() {}

	public BehaviorAction getBehaviorAction() {
		return behaviorAction;
	}

	public void setBehaviorAction(BehaviorAction behaviorAction) {
		this.behaviorAction = behaviorAction;
	}

	public BehaviorTrackerType getBehaviorTrackerType() {
		return behaviorTrackerType;
	}

	public void setBehaviorTrackerType(BehaviorTrackerType behaviorTrackerType) {
		this.behaviorTrackerType = behaviorTrackerType;
	}
	
	
}
