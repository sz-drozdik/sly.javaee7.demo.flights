package sly.javaee7.demo.flights.mock;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import sly.javaee7.commons.crud.Identifiable;
import sly.javaee7.commons.crud.Operations;
import sly.javaee7.commons.crud.Queries;
import sly.javaee7.demo.flights.model.Aircraft;
import sly.javaee7.demo.flights.model.Airport;
import sly.javaee7.demo.flights.model.Flight;
import sly.javaee7.demo.flights.model.Statuses;

@Stateless
public class FlightBuilder {

	protected static String createAircraftCode() {
		return RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomNumeric(2)
				+ RandomStringUtils.randomAlphabetic(1).toUpperCase();
	}

	protected static String createAirportCode() {
		return RandomStringUtils.randomAlphabetic(3).toUpperCase();
	}

	protected static String createFlightCode() {
		return RandomStringUtils.randomAlphabetic(3).toUpperCase() + RandomStringUtils.randomNumeric(4);
	}

	public static Date createRandomDateTime(Date start, long rangeMinutes) {
		long t1 = start.getTime();
		long t2 = start.getTime() + rangeMinutes * 60 * 1000;
		return new Date(RandomUtils.nextLong(Math.min(t1, t2), Math.max(t1, t2)));
	}

	@Inject
	private Operations ops;

	@Inject
	private Queries q;

	public <T extends Identifiable> T createIdentifiable(Class<T> clazz, Supplier<String> supp) {
		T item = null;
		try {
			item = clazz.newInstance();
		} catch (InstantiationException e) {
			//
		} catch (IllegalAccessException e) {
			//
		}
		item.setCode(supp.get());
		ops.create(item);
		return item;
	}

	public Aircraft createRandomAircraft() {
		return createIdentifiable(Aircraft.class, FlightBuilder::createAircraftCode);
	}

	public Airport createRandomAirport() {
		return createIdentifiable(Airport.class, FlightBuilder::createAirportCode);
	}

	public Flight createRandomFlight(Date scheduledTimeDeparture) {
		Flight f = createRandomFlightStub(scheduledTimeDeparture);
		ops.create(f);
		return f;
	}

	public Flight createRandomFlightStub(Date scheduledTimeDeparture) {
		Airport from = getAnotherRandomAirport(null);
		Airport to = getAnotherRandomAirport(from);
		Aircraft aircraft = getRandomAircraft();

		int scheduledFlightMinutes = RandomUtils.nextInt(60, 12 * 60);
		Date scheduledTimeArrival = new Date(scheduledTimeDeparture.getTime() + 1000 * 60 * scheduledFlightMinutes);

		Flight f = new Flight();
		f.setCode(createFlightCode());

		f.setFrom(from);
		f.setTo(to);
		f.setAircraft(aircraft);
		f.setScheduledTimeDeparture(scheduledTimeDeparture);
		f.setScheduledTimeArrival(scheduledTimeArrival);

		f.setStatus(Statuses.ST_SCHEDULED);

		return f;
	}

	private Airport getAnotherRandomAirport(Airport first) {
		List<Object> ids = q.getRecordIds(Airport.class);
		if (first != null) {
			ids.remove(first.getId());
		}
		Object id = ids.get(RandomUtils.nextInt(0, ids.size()));
		return ops.find(Airport.class, id);
	}

	private Aircraft getRandomAircraft() {
		List<Object> ids = q.getRecordIds(Aircraft.class);
		Object id = ids.get(RandomUtils.nextInt(0, ids.size()));
		return ops.find(Aircraft.class, id);
	}

}
