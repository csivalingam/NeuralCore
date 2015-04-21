package net.zfp.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.zfp.entity.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDao {

	// Constants ---------------------------------------------------------------------------------------------- Constants

	private transient static final Logger log = LoggerFactory.getLogger(BaseDao.class);

	// Instance Variables ---------------------------------------------------------------------------- Instance Variables

	@PersistenceContext(unitName = "data")
	protected EntityManager entityManager;

	// Constructors ---------------------------------------------------------------------------------------- Constructors

	// Public Methods ------------------------------------------------------------------------------------ Public Methods

	// TODO: Document what persist and merge do
	public void saveObject(Object o) {
		saveObject(o, true);
	}

	public void saveObject(Object o, boolean flush) {
		entityManager.persist(o);
		if (flush) {
			entityManager.flush();
		}
	}

	public <T> T updateObject(T o, boolean flush) {
		T merge = entityManager.merge(o);
		if (flush) {
			entityManager.flush();
		}
		return merge;
	}

	public <T> T updateObject(T o) {
		return updateObject(o, true);
	}

	public void deleteObject(Object o) {
		entityManager.remove(o);
		entityManager.flush();
	}

	public void flush() {
		entityManager.flush();
	}

	public <T extends Entity> List<T> getObjects(Class<T> clazz, Collection<? extends Serializable> ids) {

		if (ids.isEmpty()) {
			return Collections.emptyList();
		}

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
		Root entity_ = query.from(clazz);
		query.where(entity_.get("id").in(ids));
		return entityManager.createQuery(query).getResultList();
	}

	public void saveObjects(Collection<?> objects) {
		for (Object object : objects) {
			entityManager.persist(object);
		}
		entityManager.flush();
	}

	public void clear() {
		entityManager.clear();
	}

	public <T extends Entity> List<T> getByProperty(Class<T> clazz, String propertyName, Object value) {

		if (value == null) {
			throw new RuntimeException("value should not be null");
		}

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
		Root entity_ = query.from(clazz);
		query.where(criteriaBuilder.equal(entity_.get(propertyName), value));

		return entityManager.createQuery(query).getResultList();
	}

	public <T extends Entity> T findEntityById(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}

	@Deprecated
	public int sqlWrite(final String sql) {
		return entityManager.createNativeQuery(sql).executeUpdate();
	}

	// Protected Methods ------------------------------------------------------------------------------ Protected Methods

	// Private Methods ---------------------------------------------------------------------------------- Private Methods

	// Getters & Setters ------------------------------------------------------------------------------ Getters & Setters

} // end of class