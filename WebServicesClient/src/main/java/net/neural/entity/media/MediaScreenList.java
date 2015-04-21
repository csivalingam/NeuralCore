package net.zfp.entity.media;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.DomainEntity;

@Entity(name="MediaScreenList" )
@XmlRootElement(name="MediaScreenList")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaScreenList extends DomainEntity{

	private static final long serialVersionUID = 3829827906813212113L;

	//private Long screenId;
	private Integer screenOrder;
	private Long streamId;

//	@ManyToOne (fetch=FetchType.EAGER)
//	@JoinColumn(name="streamId")
//	private MediaStream mediaStream;

	@XmlAttribute(name="mediaScreen")
	@ManyToOne
	@JoinColumn(name="screenId")
	private MediaScreen mediaScreen;
	
	public MediaScreenList() { }

	public Long getStreamId() {
		return streamId;
	}
	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}
	public Integer getScreenOrder() {
		return screenOrder;
	}
	public void setScreenOrder(Integer screenOrder) {
		this.screenOrder = screenOrder;
	}

	public MediaScreen getMediaScreen() {
		return mediaScreen;
	}

	public void setMediaScreen(MediaScreen mediaScreen) {
		this.mediaScreen = mediaScreen;
	}
	
}
