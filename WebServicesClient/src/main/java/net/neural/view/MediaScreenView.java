package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MediaScreenView")
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaScreenView {

	@XmlAttribute(name="id")
	private Long id;
	@XmlAttribute(name="name")
	private String name;
	@XmlAttribute(name="url")
	private String url;
	@XmlAttribute(name="order")
	private Integer order;
	@XmlAttribute(name="rotationInterval")
	private Integer rotationInterval;
	@XmlAttribute(name="screenTypeId")
	private Long screenTypeId;
	
	public MediaScreenView() { }
	public MediaScreenView(Long id, String name, String url, Long screenTypeId) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.screenTypeId = screenTypeId;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getScreenTypeId() {
		return screenTypeId;
	}
	public void setScreenTypeId(Long screenTypeId) {
		this.screenTypeId = screenTypeId;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getRotationInterval() {
		return rotationInterval;
	}
	public void setRotationInterval(Integer rotationInterval) {
		this.rotationInterval = rotationInterval;
	}
	
}
