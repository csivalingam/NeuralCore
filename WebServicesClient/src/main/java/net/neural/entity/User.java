package net.zfp.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="Account" )
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends BaseEntity {
	
	private static final long serialVersionUID = 6085502311608069396L;
	public static final int ACCOUNT_ZEROFOORPRINT = 1;
	public static final int ACCOUNT_FACEBOOK = 2;
	public static final int ACCOUNT_CORPORATE = 3;
	
	@XmlAttribute(name="activationCode")
	private String activationCode;

	@XmlAttribute(name="address1")
	private String address1;
	@XmlAttribute(name="address2")
	private String address2;
	@XmlAttribute(name="company")
	private String company;
	@XmlAttribute(name="city")
	private String city;
	@XmlAttribute(name="province")
	private String province;
	@XmlAttribute(name="country")
	private String country;
	@XmlAttribute(name="postalCode")
	private String postalCode;
	@XmlAttribute(name="birthYear")
	private Integer birthYear;
	
	@XmlAttribute(name="gender")
	private Integer gender; // 1 == male && 2 == female
	
	@XmlAttribute(name="createdBy")
	private String createdBy;
	@XmlAttribute(name="currency")
	private String currency;
	@XmlAttribute(name="enabled")
	private Boolean enabled;
	@XmlAttribute(name="faxNumber")
	private String faxNumber;
	@XmlAttribute(name="language")
	private String language;
	@XmlAttribute(name="lastLoggedInto")
	private String lastLoggedInto;
	@XmlAttribute(name="suffix")
	private String suffix;
	@XmlAttribute(name="unitSystem")
	private String unitSystem;
	
	@XmlAttribute(name="defaultCategory")
	private Integer defaultCategory;
	
	@XmlAttribute(name="communicationMode")
	private Integer communicationMode;
	
	@XmlAttribute(name="defaultCommunity")
	private Long defaultCommunity;
	

	@XmlAttribute(name="email")
	private String email;
	@XmlAttribute(name="communicationEmail")
	private String communicationEmail;
	@XmlAttribute(name="alternativeEmail")
	private String alternativeEmail;
	@XmlAttribute(name="firstName")
	private String firstName;	
	@XmlAttribute(name="lastName")
	private String lastName;
	@XmlAttribute(name="middleName")
	private String middleName;

	@XmlAttribute(name="uid")
	private String uid;
	
	@XmlAttribute(name="homePhone")
	private String homePhone;
	@XmlAttribute(name="mobilePhone")
	private String mobilePhone;
	@XmlAttribute(name="workPhone")
	private String workPhone;
	
	@XmlAttribute(name="password")
	private String password;
	@XmlAttribute(name="passwordSalt")
	private String passwordSalt;
	@XmlAttribute(name="passwordResetCode")
    private String passwordResetCode;
	@XmlAttribute(name="passwordResetCodeExpiresOn")
    private Date passwordResetCodeExpiresOn;

	@XmlAttribute(name="mode")
	private Integer mode = 1;  /* VELO=1 & FACEBOOK = 2 */
	@XmlAttribute(name="authenticateId")
	private String authenticateId;
			
	@XmlElement(name="Status")
	@OneToOne
	@JoinColumn(name="statusId")
	private Status status;
	
	@XmlAttribute(name="profileImageUrl")
	private String profileImageUrl;
	
	public User() {
	}
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*public Set<PlantSiteDetail> getDetail() {
		return  plantSiteDetail;
	}
	public void setSites(Set<PlantSiteDetail> plantSiteDetail) {
		this.plantSiteDetail = plantSiteDetail;
	}

	
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="siteuser",joinColumns={@JoinColumn(name="id")},inverseJoinColumns={@JoinColumn(name="partyId")})
	
	private Set<PlantSiteDetail> plantSiteDetail = new HashSet<PlantSiteDetail>();*/

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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLastLoggedInto() {
		return lastLoggedInto;
	}
	public void setLastLoggedInto(String lastLoggedInto) {
		this.lastLoggedInto = lastLoggedInto;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getUnitSystem() {
		return unitSystem;
	}
	public void setUnitSystem(String unitSystem) {
		this.unitSystem = unitSystem;
	}
	public Integer getDefaultCategory() {
		return defaultCategory;
	}
	public void setDefaultCategory(Integer defaultCategory) {
		this.defaultCategory = defaultCategory;
	}
	public Long getDefaultCommunity() {
		return defaultCommunity;
	}
	public void setDefaultCommunity(Long defaultCommunity) {
		this.defaultCommunity = defaultCommunity;
	}
	
	public String getCommunicationEmail() {
		return communicationEmail;
	}
	public void setCommunicationEmail(String communicationEmail) {
		this.communicationEmail = communicationEmail;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
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
	public String getPasswordResetCode() {
		return passwordResetCode;
	}
	public void setPasswordResetCode(String passwordResetCode) {
		this.passwordResetCode = passwordResetCode;
	}
	public Date getPasswordResetCodeExpiresOn() {
		return passwordResetCodeExpiresOn;
	}
	public void setPasswordResetCodeExpiresOn(Date passwordResetCodeExpiresOn) {
		this.passwordResetCodeExpiresOn = passwordResetCodeExpiresOn;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
}
