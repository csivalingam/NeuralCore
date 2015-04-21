package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.Translation;

@XmlRootElement(name="translation")
@XmlAccessorType(XmlAccessType.FIELD)
public class TranslationView {

	@XmlAttribute(name="id")
	private Long id;
	@XmlAttribute(name="translationKey")
	private String translationKey;
	@XmlAttribute(name="translation")
	private String translation;
	@XmlAttribute(name="communityId")
	private Long communityId;
	@XmlAttribute(name="locale")
	private String locale;
	@XmlAttribute(name="componentId")
	private Long componentId;
	@XmlAttribute(name="componentName")
	private String componentName;

	public TranslationView() {
	}

	public TranslationView(Translation translation) {
		this.id = translation.getId();
		this.translationKey = translation.getTranslationKey();
		this.translation = translation.getTranslation();
		this.locale = translation.getLocale();
		this.communityId =  (translation.getCommunity() == null) ? null : translation.getCommunity().getId();
		this.componentId = translation.getComponent().getId();
		this.componentName = translation.getComponent().getComponentName();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTranslationKey() {
		return translationKey;
	}
	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	
	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
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

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

}
