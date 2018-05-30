package sly.javaee7.demo.flights.view;

import javax.faces.validator.ValidatorException;

import org.junit.Test;

import sly.javaee7.demo.flights.model.Statuses;

public class StatusValidatorTest {

	@Test(expected = ValidatorException.class)
	public void Flight_Status_Invalid_OutOf_List() {
		StatusValidator validator = new StatusValidator();
		validator.validate(null, null, Statuses.ST_CANCELED + "-INVALID");
	}
	
	@Test
	public void Flight_Status_Valid_From_List() {
		StatusValidator validator = new StatusValidator();
		validator.validate(null, null, Statuses.ST_CANCELED);
	}

}
