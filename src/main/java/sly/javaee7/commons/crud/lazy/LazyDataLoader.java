package sly.javaee7.commons.crud.lazy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import sly.javaee7.commons.exc.ExceptionController;

@Dependent
public class LazyDataLoader {

	private final static String DOT = ".";

	@SuppressWarnings("rawtypes")
	public static Path getField(Path from, String field) {
		if (!isNestedField(field)) {
			return from == null ? null : from.get(field);
		}
		Path subPath = getField(from, StringUtils.substringBefore(field, DOT));
		return getField(subPath, StringUtils.substringAfter(field, DOT));
	}

	public static boolean isNestedField(String field) {
		return field != null && field.contains(DOT);
	}

	@PersistenceContext
	private EntityManager em;

	protected <T> List<Order> getOrderBy(String sortField, int sortOrder, CriteriaBuilder cb, Root<T> from) {
		List<Order> orders = null;
		{
			orders = new LinkedList<>();
			@SuppressWarnings("rawtypes")
			Path path = getField(from, sortField);
			Order o = (sortOrder > 0) ? cb.desc(path) : cb.asc(path);
			orders.add(o);
		}
		return orders;
	}

	protected <T> Long getRecordCount(Class<T> clazz, Criterias<T> rest) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> from = cq.from(clazz);

		cq.select(cb.count(from)).distinct(true);

		Predicate predicate = getWhere(rest, cb, cq, from);
		if (predicate != null) {
			cq.where(predicate);
		}

		Long result = em.createQuery(cq).getSingleResult();

		return result;

	}

	protected <T> Predicate getWhere(Criterias<T> rest, CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> from) {
		return rest.getCriterias(cb, cq, from);
	}

	@Inject
	private ExceptionController exc;

	public <T> LoadResult<T> load(Class<T> clazz, Criterias<T> rest, int first, int pageSize, String sortField,
			int sortOrder) {
		try {
			return loadTry(clazz, rest, first, pageSize, sortField, sortOrder);
		} catch (Exception e) {
			throw exc.wrappedException("Error loading data: " + (clazz == null ? "missing datatype" : clazz.getName()), e);
		}
	}

	private <T> LoadResult<T> loadTry(Class<T> clazz, Criterias<T> rest, int first, int pageSize, String sortField,
			int sortOrder) {

		LoadResult<T> loadResult = new LoadResult<>();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> from = cq.from(clazz);
		boolean distinct = true;
		if (distinct && isNestedField(sortField)) {
			distinct = false;
		}
		CriteriaQuery<T> select = cq.select(from).distinct(distinct);

		Predicate predicate = getWhere(rest, cb, cq, from);
		if (predicate != null) {
			cq.where(predicate);
		}

		if (sortOrder != 0) {
			List<Order> orders = getOrderBy(sortField, sortOrder, cb, from);
			if (orders != null) {
				cq.orderBy(orders);
			}
		}

		List<T> r = new ArrayList<>();

		TypedQuery<T> tq = em.createQuery(select);

		loadResult.rowCount = getRecordCount(clazz, rest).intValue();

		if (loadResult.rowCount > 0) {
			r = tq.setFirstResult(first).setMaxResults(pageSize).getResultList();
		}

		loadResult.resultList = r;
		return loadResult;
	}

}
