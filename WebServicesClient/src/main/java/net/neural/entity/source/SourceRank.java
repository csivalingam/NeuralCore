package net.zfp.entity.source;

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
@Entity(name="SourceRank" )
@XmlRootElement(name="SourceRank")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceRank extends DomainEntity {
	
	private static final long serialVersionUID = 809164429415146950L;

	@XmlAttribute(name="date")
	private Date date;
	
	@XmlAttribute(name="rankModelId")
	private Long rankModelId;
	
	@XmlElement(name="Source")
	@ManyToOne
	@JoinColumn(name="sourceId")
	private Source source;
	
	@XmlAttribute(name="rank")
	private Integer rank;
	
	public SourceRank(){}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRankModelId() {
		return rankModelId;
	}

	public void setRankModelId(Long rankModelId) {
		this.rankModelId = rankModelId;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	

}
