package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="rank")
@XmlAccessorType(XmlAccessType.FIELD)
public class RankView {
	
	@XmlAttribute(name="firstName")
	private String firstName;
	@XmlAttribute(name="lastName")
	private String lastName;
	@XmlAttribute(name="statusCode")
	private Integer statusCode;
	@XmlAttribute(name="status")
	private String status;
	@XmlAttribute(name="rank")
	private Integer rank;
	@XmlAttribute(name="division")
	private Integer division;
	
	@XmlAttribute(name="value")
	private Double value;
	@XmlAttribute(name="imageUrl")
	private String imageUrl;
	
	public RankView() {}
	
	

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

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public Integer getDivision() {
		return division;
	}



	public void setDivision(Integer division) {
		this.division = division;
	}
	
	
}
