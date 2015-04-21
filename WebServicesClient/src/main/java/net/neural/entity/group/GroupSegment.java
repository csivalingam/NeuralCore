package net.zfp.entity.group;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;
import net.zfp.entity.group.Groups;
import net.zfp.entity.segment.Segment;

/**
 * Entity implementation class for Entity: GroupSegment
 *
 */
@Entity(name="GroupSegment")
@XmlRootElement(name="GroupSegment")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupSegment extends DomainEntity {

	private static final long serialVersionUID = 819164429415846950L;
	
	@XmlElement(name="Groups")
	@ManyToOne
	@JoinColumn(name="groupId")
	private Groups groups;
	
	@XmlElement(name="Segment")
	@ManyToOne
	@JoinColumn(name="segmentId")
	private Segment segment;
	
	public GroupSegment() {}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	
	
}
