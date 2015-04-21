package net.zfp.view;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.memberactivity.MemberActivity;

@XmlRootElement(name="memberActivity")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class MemberActivityView {
	@XmlElement
	private Long id;
	@XmlElement
	private Long membershipId;
	@XmlElement
	private Long memberId;
	@XmlElement
	private Long memberActivityType;
	@XmlElement
	private Date date;
	@XmlElement
	private String referenceCode;
	@XmlElement
	private Long reference;
	@XmlElement
	private Boolean processed;
	@XmlElement
	private String promoCode;
	@XmlElement
	private ResultView result;
	
	public MemberActivityView(){}
	
	public MemberActivityView(MemberActivity ma){
		this.id = ma.getId();
		this.membershipId = ma.getMembershipId();
		if (ma.getUser() != null) this.memberId = ma.getUser().getId();
		this.memberActivityType = ma.getMemberActivityType().getId();
		this.date = ma.getDate();
		this.referenceCode = ma.getReferenceCode();
		this.reference = ma.getReference();
		this.processed = ma.getProcessed();
		this.promoCode = ma.getPromoCode();
	}
	public Long getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(Long membershipId) {
		this.membershipId = membershipId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public Long getMemberActivityType() {
		return memberActivityType;
	}
	public void setMemberActivityType(Long memberActivityType) {
		this.memberActivityType = memberActivityType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	
	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
		this.reference = reference;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
