package sly.javaee7.demo.flights.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sly.javaee7.commons.crud.Identifiable;
import sly.javaee7.demo.flights.push.FlightAudit;

@Entity
@EntityListeners(FlightAudit.class)
public class Flight extends Identifiable {

	@ManyToOne
	private Aircraft aircraft;

	@ManyToOne
	private Airport from, to;

	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduledTimeDeparture, actualTimeDeparture, scheduledTimeArrival, actualTimeArrival;

	private String status;

	public Date getActualTimeArrival() {
		return actualTimeArrival;
	}

	public Date getActualTimeDeparture() {
		return actualTimeDeparture;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public Airport getFrom() {
		return from;
	}

	public Long getMinutes() {
		if (actualTimeArrival != null && actualTimeDeparture != null) {
			return (actualTimeArrival.getTime() - actualTimeDeparture.getTime()) / (1000 * 60);
		}
		return null;
	}

	public Date getScheduledTimeArrival() {
		return scheduledTimeArrival;
	}

	public Date getScheduledTimeDeparture() {
		return scheduledTimeDeparture;
	}

	public String getStatus() {
		return status;
	}

	public Airport getTo() {
		return to;
	}

	public void setActualTimeArrival(Date actualTimeArrival) {
		this.actualTimeArrival = actualTimeArrival;
	}

	public void setActualTimeDeparture(Date actualTimeDeparture) {
		this.actualTimeDeparture = actualTimeDeparture;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public void setFrom(Airport from) {
		this.from = from;
	}

	public void setScheduledTimeArrival(Date scheduledTimeArrival) {
		this.scheduledTimeArrival = scheduledTimeArrival;
	}

	public void setScheduledTimeDeparture(Date scheduledTimeDeparture) {
		this.scheduledTimeDeparture = scheduledTimeDeparture;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTo(Airport to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Flight [id=" + getId() + ", from=" + from + ", STD=" + scheduledTimeDeparture + ", status=" + status
				+ "]";
	}
}
