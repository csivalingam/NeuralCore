package net.zfp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import net.zfp.util.EntityClassUtil;
import net.zfp.util.exception.NullIdentifierException;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseAuditEntity implements Entity {

  // Constants ---------------------------------------------------------------------------------------------- Constants

  // Instance Variables ---------------------------------------------------------------------------- Instance Variables

  @Id
  //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fallback")
  @GenericGenerator(name = "fallback", strategy = "net.zerofootprint.velo.util.hibernate.AssignedFallbackIdGenerator")
  private Long id;

  @Version
  @Column(nullable = false)
  private Date lastModified = new Date();

  @Column(nullable = false)
  private Date created = new Date();

  // Constructors ---------------------------------------------------------------------------------------- Constructors

  // Public Methods ------------------------------------------------------------------------------------ Public Methods

  /**
   * A standard, efficient, and foolproof implementation of Object.equals(). This method can be overridden.
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
      throw new NullIdentifierException("equals cannot be computed because the identifier is null for object " + toString());
    }

    if (!EntityClassUtil.getEntityClass(this).isAssignableFrom(EntityClassUtil.getEntityClass(other))
            && !EntityClassUtil.getEntityClass(other).isAssignableFrom(EntityClassUtil.getEntityClass(this))) {
      return false;
    }

    // equivalence by id
    return getId().equals(other.getId());
  }

  /**
   * A standard, efficient, and foolproof implementation of Object.hashCode(). This method can be overridden.
   */
  @Override
  public int hashCode() {
    if (getId() != null) {
      return getId().hashCode();
    }
    throw new NullIdentifierException("hashcode cannot be computed because the identifier is null for object " + toString());
  }
  
}
  