package sly.javaee7.demo.flights.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import sly.javaee7.demo.flights.model.Statuses;

@FacesValidator("statusValidator")
public class StatusValidator implements Validator {
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (Statuses.STATUSES.contains(value)) {
			return;
		}
		String msg = "Invalid status! Accepted values : " + Statuses.STATUSES;
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
	}
}
