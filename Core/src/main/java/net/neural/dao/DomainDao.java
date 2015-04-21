package net.zfp.dao;

import net.zfp.entity.DomainTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.persistence.Query;
import java.util.List;

@Named
public class DomainDao extends BaseDao {

	private transient static final Logger LOG = LoggerFactory.getLogger(DomainDao.class);

	public String findLocaleByDomainName(String domainName) {
		Query namedQuery = entityManager.createNamedQuery("domain.findLocale");
		namedQuery.setParameter("domainName", domainName);
		List<DomainTable> resultList = namedQuery.getResultList();
		return resultList.size() > 0 ? resultList.get(0).getLocale() : null;
	}

	public Long findCommunityIdByDomainName(String domainName) {
		Query namedQuery = entityManager.createNamedQuery("domain.findCommunityIdByDomainName");
		namedQuery.setParameter("domainName", domainName);
		List<Long> resultList = namedQuery.getResultList();
		return resultList.size() > 0 ? resultList.get(0) : null;
	}

	public DomainTable findById(long id) {
		return entityManager.find(DomainTable.class, id);
	}
}