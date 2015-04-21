package net.zfp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.util.EntityClassUtil;
import net.zfp.util.exception.NullIdentifierException;

import org.hibernate.annotations.GenericGenerator;

//@Entity(name = "Domain")
@NamedQueries({
		@NamedQuery(name = "domain.findLocale", query = "select d from Domain d where d.name = :domainName"),
//		@NamedQuery(name = "domain.findCommunityIdByDomainName", query = "select d.community.id from Domain d where d.name = :domainName"),
		@NamedQuery(name = "community.findDomainById", query = "select d from Domain d where d.id = :id")

})
@XmlRootElement(name = "Domain")
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainTable extends BaseAuditEntity {

	// private transient static final Logger LOG =
	// LoggerFactory.getLogger(Domain.class);

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fallback")
	@GenericGenerator(name = "fallback", strategy = "net.zerofootprint.velo.util.hibernate.AssignedFallbackIdGenerator")
	private Long id;

	@Version
	@Column(nullable = false)
	private Date lastModified;

	@Column(nullable = false)
	private Date created = new Date();

	// Constructors
	// ----------------------------------------------------------------------------------------
	// Constructors

	// Public Methods
	// ------------------------------------------------------------------------------------
	// Public Methods

	/**
	 * A standard, efficient, and foolproof implementation of Object.equals().
	 * This method can be overridden.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof BaseAuditEntity)) {
			return false;
		}

		BaseAuditEntity other = (BaseAuditEntity) o;

		// if the id is missing, throw an exception
		if (getId() == null) {
			throw new NullIdentifierException(
					"equals cannot be computed because the identifier is null for object "
							+ toString());
		}

		if (!EntityClassUtil.getEntityClass(this).isAssignableFrom(
				EntityClassUtil.getEntityClass(other))
				&& !EntityClassUtil.getEntityClass(other).isAssignableFrom(
						EntityClassUtil.getEntityClass(this))) {
			return false;
		}

		// equivalence by id
		return getId().equals(other.getId());
	}

	/**
	 * A standard, efficient, and foolproof implementation of Object.hashCode().
	 * This method can be overridden.
	 */
	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		throw new NullIdentifierException(
				"hashcode cannot be computed because the identifier is null for object "
						+ toString());
	}

	// Protected Methods
	// ------------------------------------------------------------------------------
	// Protected Methods

	// Private Methods
	// ----------------------------------------------------------------------------------
	// Private Methods

	// Getters & Setters
	// ------------------------------------------------------------------------------
	// Getters & Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column
	private String name;

	@Column
	private String locale;

	// TODO Relationship
	// @ManyToOne(optional = false)
	// private Community community;

	@Column
	private Long community_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Long getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Long community_id) {
		this.community_id = community_id;
	}

}