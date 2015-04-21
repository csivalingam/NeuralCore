package net.zfp.form;

//@XmlRootElement(name="translationFrom")
//@XmlAccessorType(XmlAccessType.FIELD)
public class TranslationForm {

	private Long communityId;
	private String communityName;
	private String key;
	private String locale;
	private Long componentId;
	private String portalTypeName;
	
	public TranslationForm() {
	}
	public Long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public Long getComponentId() {
		return componentId;
	}
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
	public String getPortalTypeName() {
		return portalTypeName;
	}
	public void setPortalTypeName(String portalTypeName) {
		this.portalTypeName = portalTypeName;
	}


	
}
