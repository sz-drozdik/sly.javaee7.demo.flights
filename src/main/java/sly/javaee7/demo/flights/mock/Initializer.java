package sly.javaee7.demo.flights.mock;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import sly.javaee7.commons.crud.Queries;
import sly.javaee7.commons.exc.ExceptionController;
import sly.javaee7.demo.flights.model.Flight;

@Startup
@Singleton
public class Initializer {

	@Inject
	private FlightBuilder builder;

	@Inject
	private Queries q;

	@Resource
	private TimerService timerService;

	@Resource
	private ExceptionController exc;

	@Timeout
	private void check() {
		if (modelIsEmpty()) {
			createMockAsset();
		}
	}

	private void createMockAsset() {
		int n = getNumberOfAirports();
		for (int i = 0; i < n; i++) {
			builder.createRandomAirport();
		}

		n = getNumberOfAircrafts();
		for (int i = 0; i < n; i++) {
			builder.createRandomAircraft();
		}

		n = getNumberOfFlights();
		int timeBetween = 10;
		Date scheduledTimeDeparture = FlightBuilder.createRandomDateTime(new Date(), -timeBetween * n / 2);
		for (int i = 0; i < n; i++) {
			scheduledTimeDeparture = FlightBuilder.createRandomDateTime(scheduledTimeDeparture, timeBetween);
			builder.createRandomFlight(scheduledTimeDeparture);
		}
	}

	private int getNumberOfAircrafts() {
		return 120;
	}

	private int getNumberOfAirports() {
		return 48;
	}

	private int getNumberOfFlights() {
		return 12;
	}

	@PostConstruct
	private void init() {
		// delayed execution to avoid startup race conditions
		timerService.createSingleActionTimer(3 * 1000, new TimerConfig(null, false));
	}

	private boolean modelIsEmpty() {
		long cnt = q.getRecordCount(Flight.class);
		return cnt < 1;
	}
}
