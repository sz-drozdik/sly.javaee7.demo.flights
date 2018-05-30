package sly.javaee7.demo.flights.push;

import sly.javaee7.commons.crud.Operations.LCOperation;
import sly.javaee7.demo.flights.model.Flight;

public class PushEvent {

	private String flightCode, opCode;

	public PushEvent(Flight f, LCOperation op) {
		this.setFlightCode(f.getCode());
		this.setOpCode(op.name());
	}

	public String getFlightCode() {
		return flightCode;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

}
