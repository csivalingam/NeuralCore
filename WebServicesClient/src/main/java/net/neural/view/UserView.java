package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.User;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;


@XmlRootElement(name="UserView")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserView  {
	
	@XmlElement
	private Long id;
	@XmlElement
	private String email;
	
	@XmlElement
	private String firstName;
	@XmlElement
	private String lastName;
	@XmlElement
	private String middleName;
	
	@XmlElement
	private String mobilePhone;
	@XmlElement
	private String homePhone;
	@XmlElement
	private String workPhone;
	
	@XmlElement
	private String identifier;
	
	@XmlElement
	private String communityCode;
	
	@XmlElement
	private String address;
	@XmlElement
	private String address2;
	@XmlElement
	private String company;
	@XmlElement
	private String city;
	@XmlElement
	private String province;
	@XmlElement
	private String country;
	@XmlElement
	private String postalCode;
	
	@XmlElement
	private String communicationEmail;
	
	@XmlElement
	private String alternativeEmail;
	
	@XmlElement
	private String language;
	
	@XmlElement
	private String profileImageUrl;
	
	@XmlElement
	private ResultView result;
	
	@XmlElement
	private Integer mode;  /* VELO=1 & FACEBOOK = 2 */
	@XmlElement
	private String authenticateId;
	
	@XmlElement
	private Integer communicationMode;
	
	@XmlElement
	private Integer birthYear;
	
	@XmlElement
	private Integer gender;
	
	@XmlElement
	private Boolean emailAlert;
	
	@XmlElement
	private Boolean emailNewsletter;
	
	@XmlElement
	private Boolean emailOffer;
	
	@XmlElement
	private String houseArea;
	
	@XmlElement
	private String occupants;
	
	@XmlElement
	private String houseHeating;
	
	@XmlElement
	private String waterHeating;
	
	public UserView() {}
	
	public UserView(User user) {
		this.id = user.getId();
		this.email=user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();

		this.mobilePhone=user.getMobilePhone();
		this.homePhone=user.getHomePhone();
		this.workPhone = user.getWorkPhone();

		this.firstName=user.getFirstName();
		this.lastName=user.getLastName();
		this.middleName=user.getMiddleName();
		
		if (user.getLanguage() == null || user.getLanguage().equals("")) this.language = "English";
		else this.language = user.getLanguage();
		
		if (user.getCommunicationEmail() == null || user.getCommunicationEmail().equals("")) this.communicationEmail = user.getEmail();
		else this.communicationEmail = user.getCommunicationEmail();
		
		if (user.getAlternativeEmail() != null) this.alternativeEmail = user.getAlternativeEmail();
		
		this.address = user.getAddress1();
		this.address2 = user.getAddress2();
		this.company = user.getCompany();
		this.city = user.getCity();
		this.province = user.getProvince();
		this.country = user.getCountry();
		this.postalCode = user.getPostalCode();
		
		this.birthYear = user.getBirthYear();
		this.gender = user.getGender();
		
		this.mode = user.getMode();
		this.authenticateId = user.getAuthenticateId();
		if (user.getCommunicationMode() != null) this.communicationMode = user.getCommunicationMode();
		else this.communicationMode = 0;
		
		if (user.getProfileImageUrl() != null) this.profileImageUrl = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(user.getProfileImageUrl());
		else this.profileImageUrl = AppConstants.APACHE_IMAGE_LINK + "/images/rewards/upload.png";
		
	}
	
	public User getModel() {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		
		user.setMobilePhone(mobilePhone);
		user.setHomePhone(homePhone);
		user.setWorkPhone(workPhone);
		
		user.setCompany(company);
		user.setAddress1(address);
		user.setCity(city);
		user.setProvince(province);
		user.setCountry(country);
		user.setPostalCode(postalCode);
		
		user.setMode(mode);
		user.setAuthenticateId(authenticateId);
		
		return user;
	}
	
	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public String getAuthenticateId() {
		return authenticateId;
	}
	public void setAuthenticateId(String authenticateId) {
		this.authenticateId = authenticateId;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCommunicationEmail() {
		return communicationEmail;
	}
	public void setCommunicationEmail(String communicationEmail) {
		this.communicationEmail = communicationEmail;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAlternativeEmail() {
		return alternativeEmail;
	}
	public void setAlternativeEmail(String alternativeEmail) {
		this.alternativeEmail = alternativeEmail;
	}
	public Integer getCommunicationMode() {
		return communicationMode;
	}
	public void setCommunicationMode(Integer communicationMode) {
		this.communicationMode = communicationMode;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Boolean getEmailAlert() {
		return emailAlert;
	}
	public void setEmailAlert(Boolean emailAlert) {
		this.emailAlert = emailAlert;
	}
	public Boolean getEmailNewsletter() {
		return emailNewsletter;
	}
	public void setEmailNewsletter(Boolean emailNewsletter) {
		this.emailNewsletter = emailNewsletter;
	}
	public Boolean getEmailOffer() {
		return emailOffer;
	}
	public void setEmailOffer(Boolean emailOffer) {
		this.emailOffer = emailOffer;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ResultView getResult() {
		return result;
	}
	public void setResult(ResultView result) {
		this.result = result;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getOccupants() {
		return occupants;
	}

	public void setOccupants(String occupants) {
		this.occupants = occupants;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getHouseHeating() {
		return houseHeating;
	}

	public void setHouseHeating(String houseHeating) {
		this.houseHeating = houseHeating;
	}

	public String getWaterHeating() {
		return waterHeating;
	}

	public void setWaterHeating(String waterHeating) {
		this.waterHeating = waterHeating;
	}

}
