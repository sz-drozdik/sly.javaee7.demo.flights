package sly.javaee7.demo.flights.mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import sly.javaee7.commons.crud.Queries;
import sly.javaee7.demo.flights.model.Flight;
import sly.javaee7.demo.flights.test.ContainerTestBase;

@RunWith(Arquillian.class)
public class FlightBuilderTest extends ContainerTestBase {

	@Inject
	private FlightBuilder builder;

	@Inject
	private Queries q;

	@Test
	public void Flight_Creation_Random() throws Exception {
		int n = 3;
		for (int i = 0; i < n; i++) {
			Flight flight = builder.createRandomFlight(new Date());
			assertNotNull(flight.getId());
		}
		long cnt = q.getRecordCount(Flight.class);
		assertTrue(n <= cnt);
	}
}
