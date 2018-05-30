package sly.javaee7.demo.flights.view;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sly.javaee7.commons.crud.lazy.Criterias;
import sly.javaee7.demo.flights.model.Flight;

public class Filters {
	private Date dateFrom, dateTo;

	private Criterias<Flight> filteringCriterias = new Criterias<Flight>() {

		@Override
		public Predicate getCriterias(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Flight> from) {
			Path<Date> created = from.get("scheduledTimeDeparture");
			Predicate cFrom = getDateFrom() != null ? cb.greaterThanOrEqualTo(created, getDateFrom())
					: cb.isTrue(cb.literal(Boolean.TRUE));
			Predicate cTo = getDateTo() != null ? cb.lessThanOrEqualTo(created, getDateTo())
					: cb.isTrue(cb.literal(Boolean.TRUE));
			return cb.and(cFrom, cTo);
		}
	};

	public Date getDateFrom() {
		return dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public Criterias<Flight> getFilteringCriterias() {
		return filteringCriterias;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
}
