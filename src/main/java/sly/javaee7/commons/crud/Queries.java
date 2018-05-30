package sly.javaee7.commons.crud;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import sly.javaee7.commons.crud.lazy.Criterias;
import sly.javaee7.commons.crud.lazy.LazyDataLoader;
import sly.javaee7.commons.crud.lazy.LoadResult;

@Stateless
public class Queries {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private LazyDataLoader loader;

	public <T> LoadResult<T> getAllByCriteria(Class<T> clazz, Criterias<T> criterias) {
		return loader.load(clazz, criterias, 0, Integer.MAX_VALUE, null, 0);
	}

	public long getRecordCount(Class<?> clazz) {
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(clazz)));
		long result = 0;
		try {
			result = em.createQuery(cq).getSingleResult();
		} catch (Exception e) {
			//
		}
		return result;
	}

	public List<Object> getRecordIds(Class<?> clazz) {
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Object> cq = qb.createQuery(Object.class);
		cq.select(cq.from(clazz).get("id"));
		try {
			return em.createQuery(cq).getResultList();
		} catch (Exception e) {
			//
		}
		return Collections.emptyList();
	}
}
