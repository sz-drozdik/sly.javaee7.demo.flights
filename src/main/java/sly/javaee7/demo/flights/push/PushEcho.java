package sly.javaee7.demo.flights.push;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import sly.javaee7.commons.crud.Operations.LCOperation;

@RequestScoped
@Named("echo")
public class PushEcho {

	public void echo() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String flightCode = params.get("flightCode");
		LCOperation operationMode = Enum.valueOf(LCOperation.class, params.get("opCode"));

		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Flight Control Event",
				"" + operationMode + ": " + flightCode);
		fc.addMessage(null, msg);
	}
}
