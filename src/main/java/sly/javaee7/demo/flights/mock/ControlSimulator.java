package sly.javaee7.demo.flights.mock;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sly.javaee7.commons.crud.Queries;
import sly.javaee7.commons.crud.lazy.Criterias;
import sly.javaee7.demo.flights.model.Flight;
import sly.javaee7.demo.flights.model.Statuses;

@Stateless
public class ControlSimulator {

	private static Criterias<Flight> CRIT_ARRIVING = new Criterias<Flight>() {

		@Override
		public Predicate getCriterias(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Flight> from) {
			Expression<Boolean> stillNotArrived = cb.isNull(from.get("actualTimeArrival"));
			Path<String> statusField = from.get("status");
			Expression<Boolean> isDeparted = cb.or(cb.equal(statusField, Statuses.ST_DEPARTED), cb.isNull(statusField));
			Path<Date> staField = from.get("scheduledTimeArrival");
			Expression<Boolean> isArriving = cb.lessThanOrEqualTo(staField, new Date());
			return cb.and(stillNotArrived, cb.and(isDeparted, isArriving));
		}
	};

	private static Criterias<Flight> CRIT_DEPARTING = new Criterias<Flight>() {

		@Override
		public Predicate getCriterias(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Flight> from) {
			Expression<Boolean> stillNotStarted = cb.isNull(from.get("actualTimeDeparture"));
			Path<String> statusField = from.get("status");
			Expression<Boolean> isScheduled = cb.or(cb.equal(statusField, Statuses.ST_SCHEDULED),
					cb.isNull(statusField));
			Path<Date> stdField = from.get("scheduledTimeDeparture");
			Expression<Boolean> isStarting = cb.lessThanOrEqualTo(stdField, new Date());
			return cb.and(stillNotStarted, cb.and(isScheduled, isStarting));
		}
	};

	@Inject
	private Queries queries;

	private void checkArrivals() {
		collectAndAct(this::getAllByCriteria, CRIT_ARRIVING, this::land);
	}

	private void collectAndAct(Function<Criterias<Flight>, List<Flight>> lister, Criterias<Flight> criterias,
			Consumer<Flight> op) {
		List<Flight> flights = lister.apply(criterias);
		flights.forEach(f -> {
			op.accept(f);
		});
	}

	private void completePendingDepartures() {
		collectAndAct(this::getAllByCriteria, CRIT_DEPARTING, this::launch);
	}

	private List<Flight> getAllByCriteria(Criterias<Flight> criterias) {
		return queries.getAllByCriteria(Flight.class, criterias).resultList;
	}

	private void land(Flight f) {
		f.setActualTimeArrival(new Date());
		f.setStatus(Statuses.ST_LANDED);
	}

	private void launch(Flight f) {
		f.setActualTimeDeparture(new Date());
		f.setStatus(Statuses.ST_DEPARTED);
	}

	@Schedule(minute = "*", hour = "*", persistent = false)
	private void simulateFlightControlStep() {
		completePendingDepartures();
		checkArrivals();
	}
}
