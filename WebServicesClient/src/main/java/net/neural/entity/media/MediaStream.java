package net.zfp.entity.media;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.BaseEntity;

@Entity(name="MediaStream" )
@XmlRootElement(name="MediaStream")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaStream extends BaseEntity {

	private static final long serialVersionUID = -1457577674815323473L;

	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="streamCode")
	private String streamCode;
	@XmlAttribute(name="status")
	private Integer status;
	@XmlAttribute(name="communityId")
	private Long communityId;
	
	@XmlElement(name="MediaTransitionType")
	@ManyToOne
	@JoinColumn(name="transitionTypeId")
	private MediaTransitionType mediaTransitionType;
		
	@XmlElement(name="mediaResolutionType")
	@ManyToOne
	@JoinColumn(name="resolutionTypeId")
	private MediaResolutionType mediaResolutionType;
	
	@XmlElement(name="header")
	@ManyToOne
	@JoinColumn(name="headerTemplateId")
	private MediaHeader header;
	
	@XmlElement(name="footer")
	@ManyToOne
	@JoinColumn(name="footerTemplateId")
	private MediaFooter footer;
	
	@XmlAttribute(name="rotationInterval")
	private Integer rotationInterval;
//	@XmlElement(name="screenList")
//	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
//	@JoinTable(
//			name="MediaScreenList",
//			joinColumns={@JoinColumn(name="streamID")},
//			inverseJoinColumns={@JoinColumn(name="screenId")}
//	)
//	private List<MediaScreen> screenList;

//	@XmlElement(name="screenOrder")
//	@OneToMany (mappedBy="mediaStream")
//	private List<MediaScreenList> screenOrder;

	public MediaStream() {
	}

	public MediaStream(String name, String streamCode,
			Integer rotationInternal, Integer status, MediaTransitionType mediaTransitionType,
			MediaResolutionType mediaResolutionType, MediaHeader header, MediaFooter footer,
			List<MediaScreen> screens) {
		super();
		this.name = name;
		this.streamCode = streamCode;
		//this.rotationInternal = rotationInternal;
		this.status = status;
		this.mediaTransitionType = mediaTransitionType;
		this.mediaResolutionType = mediaResolutionType;
		this.header = header;
		this.footer = footer;
		//this.screenList = screens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreamCode() {
		return streamCode;
	}

	public void setStreamCode(String streamCode) {
		this.streamCode = streamCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public MediaTransitionType getMediaTransitionType() {
		return mediaTransitionType;
	}

	public void setMediaTransitionType(MediaTransitionType mediaTransitionType) {
		this.mediaTransitionType = mediaTransitionType;
	}

	public MediaResolutionType getMediaResolutionType() {
		return mediaResolutionType;
	}

	public void setMediaResolutionType(MediaResolutionType mediaResolutionType) {
		this.mediaResolutionType = mediaResolutionType;
	}

	public MediaHeader getHeader() {
		return header;
	}

	public void setHeader(MediaHeader header) {
		this.header = header;
	}

	public MediaFooter getFooter() {
		return footer;
	}

	public void setFooter(MediaFooter footer) {
		this.footer = footer;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Integer getRotationInterval() {
		return rotationInterval;
	}

	public void setRotationInterval(Integer rotationInterval) {
		this.rotationInterval = rotationInterval;
	}

//	public List<MediaScreen> getScreenList() {
//		return screenList;
//	}
//
//	public void setScreenList(List<MediaScreen> screenList) {
//		this.screenList = screenList;
//	}

//	public List<MediaScreenList> getScreenOrder() {
//		return screenOrder;
//	}
//
//	public void setScreenOrder(List<MediaScreenList> screenOrder) {
//		this.screenOrder = screenOrder;
//	}

}
