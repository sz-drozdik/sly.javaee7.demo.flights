package sly.javaee7.commons.crud.lazy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Criterias<T> {

	public Predicate getCriterias(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> from);

}
