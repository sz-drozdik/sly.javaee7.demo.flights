package sly.javaee7.demo.flights.model;

import java.util.Arrays;
import java.util.List;

public class Statuses {

	public final static String ST_SCHEDULED = "scheduled", ST_DEPARTED = "departed", ST_LANDED = "landed",
			ST_CANCELED = "canceled";

	public final static List<String> STATUSES = Arrays.asList(ST_SCHEDULED, ST_DEPARTED, ST_LANDED, ST_CANCELED);
}
