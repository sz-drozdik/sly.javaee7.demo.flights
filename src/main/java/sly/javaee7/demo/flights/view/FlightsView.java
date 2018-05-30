package sly.javaee7.demo.flights.view;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import sly.javaee7.commons.crud.Operations;
import sly.javaee7.commons.crud.Operations.LCOperation;
import sly.javaee7.commons.crud.lazy.Criterias;
import sly.javaee7.commons.crud.lazy.LazyDataLoader;
import sly.javaee7.demo.flights.mock.FlightBuilder;
import sly.javaee7.demo.flights.model.Flight;

@Named
@ViewScoped
public class FlightsView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private FlightBuilder builder;

	private transient FlightLazyDataModel dataModel;

	private boolean editing;

	private Filters filters;
	@Inject
	private transient LazyDataLoader loader;
	private LCOperation operationMode = LCOperation.Update;

	private Flight operationTarget;

	@Inject
	private Operations ops;

	private Flight selectedFlight;

	private void commitLCOperation() {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage();
		try {
			switch (operationMode) {
			case New:
				msg.setDetail("Created!");
				ops.create(operationTarget);
				break;
			case Update:
				msg.setDetail("Updated!");
				ops.update(operationTarget);
				break;
			case Delete:
				msg.setDetail("Deleted!");
				ops.delete(operationTarget);
				break;
			default:
				throw new IllegalArgumentException("Unknown operation: " + operationMode);
			}
			msg.setSummary("Flight '" + operationTarget.getCode() + "'");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			msg.setDetail("Error: " + e.getMessage());
		} finally {
			fc.addMessage(null, msg);
			operationTarget = null;
			setEditing(false);
			setSelectedFlight(null);
		}
	}

	public void delete() {
		operationTarget = getSelectedFlight();
		operationMode = LCOperation.Delete;
		commitLCOperation();
	}

	public Flight getEditingFlight() {
		if (operationTarget == null) {
			if (LCOperation.Update.equals(operationMode) && getSelectedFlight() != null) {
				operationTarget = ops.find(getSelectedFlight());
			} else if (LCOperation.New.equals(operationMode)) {
				operationTarget = builder.createRandomFlightStub(new Date());
			}
		}
		return operationTarget;
	}

	public Filters getFilters() {
		return filters;
	}

	public FlightLazyDataModel getFlights() {
		return dataModel;
	}

	public Flight getSelectedFlight() {
		return selectedFlight;
	}

	@PostConstruct
	private void init() {
		dataModel = new FlightLazyDataModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected Criterias<Flight> getCriterias() {
				return filters.getFilteringCriterias();
			}

			@Override
			protected LazyDataLoader getLazyDataLoader() {
				return loader;
			}
		};

		filters = new Filters();
	}

	public boolean isEditing() {
		return editing;
	}

	public void prepareNew() {
		setEditing(true);
		operationMode = LCOperation.New;
	}

	public void prepareUpdate() {
		setEditing(true);
		operationMode = LCOperation.Update;
	}

	public void save() {
		commitLCOperation();
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public void setSelectedFlight(Flight selectedFlight) {
		this.selectedFlight = selectedFlight;
	}
}
