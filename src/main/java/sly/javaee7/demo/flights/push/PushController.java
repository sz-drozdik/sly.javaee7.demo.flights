package sly.javaee7.demo.flights.push;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

@ApplicationScoped
public class PushController {

	@Inject
	@Push(channel = "flightsEvents")
	private PushContext events;

	public void onPushEvent(@Observes PushEvent event) {
		Object msg = event;
		System.err.println("Push : " + msg);
		events.send(msg);
	}
}
