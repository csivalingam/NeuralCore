package net.zfp.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="flightForm")
@XmlAccessorType(XmlAccessType.FIELD)

public class FlightForm {

	@XmlAttribute(name="communityId")
	private long communityId;
	@XmlAttribute(name="communityName")
	private String communityName;
	@XmlAttribute(name="userId")
	private long userId;
	@XmlAttribute(name="email")
	private String email;
	@XmlAttribute(name="passengers")
	private int passengers;
	@XmlAttribute(name="flightType")
	private int flightType = 2;  // 1 is one way, 2 is return
	@XmlAttribute(name="departure")
	private String departure;
	@XmlAttribute(name="departureId")
	private long departureId;
	@XmlAttribute(name="arrival")
	private String arrival;
	@XmlAttribute(name="arrivalId")
	private long arrivalId;
	@XmlAttribute(name="payment")
	private Double payment;
	@XmlAttribute(name="paymentUnit")
	private String paymentUnit = "CAD";
	public FlightForm() {
	}
	public long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(long communityId) {
		this.communityId = communityId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getPassengers() {
		return passengers;
	}
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	public int getFlightType() {
		return flightType;
	}
	public void setFlightType(int flightType) {
		this.flightType = flightType;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public long getDepartureId() {
		return departureId;
	}
	public void setDepartureId(long departureId) {
		this.departureId = departureId;
	}
	public long getArrivalId() {
		return arrivalId;
	}
	public void setArrivalId(long arrivalId) {
		this.arrivalId = arrivalId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	public String getPaymentUnit() {
		return paymentUnit;
	}
	public void setPaymentUnit(String paymentUnit) {
		this.paymentUnit = paymentUnit;
	}
}
