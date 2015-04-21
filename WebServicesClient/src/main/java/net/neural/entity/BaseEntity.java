package net.zfp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseEntity extends DomainEntity {

	private static final long serialVersionUID = 6085508311608069396L;

	@Column(nullable = false)
	private Date created = new Date();

	@Column(nullable = false)
	private Date lastModified = new Date();

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
