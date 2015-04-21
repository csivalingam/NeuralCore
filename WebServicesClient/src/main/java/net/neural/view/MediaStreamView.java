package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.media.MediaResolutionType;

@XmlRootElement(name="MediaStreamView")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaStreamView {

	@XmlAttribute(name="id")
	private Long id;
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="streamCode")
	private String streamCode;
	@XmlAttribute(name="transitionType")
	private String transitionType;
	
	@XmlAttribute(name="resolutionWidth")
	private Integer resolutionWidth;
	@XmlAttribute(name="resolutionHeight")
	private Integer resolutionHeight;
	
	@XmlAttribute(name="status")
	private Integer status;
	
	@XmlAttribute(name="headerName")
	private String headerName;
	@XmlAttribute(name="headerUrl")
	private String headerUrl;
	@XmlAttribute(name="footerName")
	private String footerName;
	@XmlAttribute(name="footerUrl")
	private String footerUrl;
	
	@XmlElement(name="result")
	private ResultView result;
	
	@XmlElement(name="screenList")
	private List<MediaScreenView> mediaScreenView;

	public MediaStreamView() {
	}
	
	public MediaStreamView(Long id, String name, String streamCode,
			Integer rotationInternal, String transitionType, Integer resolutionWidth, Integer resolutionHeight,
			Integer status, String headerName, String headerUrl,
			String footerName, String footerUrl,
			List<MediaScreenView> mediaScreenView) {
		super();
		this.id = id;
		this.name = name;
		this.streamCode = streamCode;
		//this.rotationInternal = rotationInternal;
		this.transitionType = transitionType;
		this.resolutionWidth = resolutionWidth;
		this.resolutionHeight = resolutionHeight;
		this.status = status;
		this.headerName = headerName;
		this.headerUrl = headerUrl;
		this.footerName = footerName;
		this.footerUrl = footerUrl;
		this.mediaScreenView = mediaScreenView;
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
	public String getStreamCode() {
		return streamCode;
	}
	public void setStreamCode(String streamCode) {
		this.streamCode = streamCode;
	}
	public String getTransitionType() {
		return transitionType;
	}

	public void setTransitionType(String transitionType) {
		this.transitionType = transitionType;
	}

	public Integer getResolutionWidth() {
		return resolutionWidth;
	}

	public void setResolutionWidth(Integer resolutionWidth) {
		this.resolutionWidth = resolutionWidth;
	}

	public Integer getResolutionHeight() {
		return resolutionHeight;
	}

	public void setResolutionHeight(Integer resolutionHeight) {
		this.resolutionHeight = resolutionHeight;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	public String getHeaderUrl() {
		return headerUrl;
	}
	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}
	public String getFooterName() {
		return footerName;
	}
	public void setFooterName(String footerName) {
		this.footerName = footerName;
	}
	public String getFooterUrl() {
		return footerUrl;
	}
	public void setFooterUrl(String footerUrl) {
		this.footerUrl = footerUrl;
	}
	public List<MediaScreenView> getMediaScreenView() {
		return mediaScreenView;
	}
	public void setMediaScreenView(List<MediaScreenView> mediaScreenView) {
		this.mediaScreenView = mediaScreenView;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
}
