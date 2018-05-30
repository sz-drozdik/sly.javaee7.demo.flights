package sly.javaee7.demo.flights.mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FlightBuilderUnitTest {

	@Test
	public void FlightCode_Validation_Simple() throws Exception {
		String code = FlightBuilder.createFlightCode();
		assertNotNull(code);
		assertTrue(code.length() == 7);
		assertTrue(code.toUpperCase().equals(code));
	}
}
