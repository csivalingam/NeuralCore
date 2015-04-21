package net.zfp.dao.wallet;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.BaseDao;
import net.zfp.entity.wallet.Wallet;

public class WalletDao<T>{
	
	private EntityManager entityManager;

	private Class<T> type;
	
	public WalletDao(){}
	
	public WalletDao(Class<T> type) {
		this.type = type;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Wallet getWallet(Long memberId) {
		Query query = entityManager.createQuery(
				"select w from Wallet w where w.member.id = :memberId");
		query.setParameter("memberId",  memberId);
		try {
			return (Wallet) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { IllegalArgumentException.class })
	public T findById(long id) {
		T object = (T) entityManager.find(type, id);
		if (object == null) {
			throw new IllegalArgumentException(String.format("Can't find domain object (%s) for id : %s", type.getName(), id));
		}
		return object;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T save(T obj) {

		try {
			entityManager.merge(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T save(T obj, boolean flush) {

		try {
			entityManager.merge(obj);
			if (flush) {
				entityManager.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
