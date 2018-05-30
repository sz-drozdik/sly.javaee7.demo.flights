package sly.javaee7.demo.flights.push;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import sly.javaee7.commons.crud.Operations;
import sly.javaee7.demo.flights.model.Flight;

public class FlightAudit {

	@Inject
	private BeanManager beanManager;

	private void fire(PushEvent event) {
		beanManager.fireEvent(event);
	}

	@PostUpdate
	@PostPersist
	@PostRemove
	public void onChange(Flight f) {
		fire(new PushEvent(f, Operations.LCOperation.Update));
	}
}
