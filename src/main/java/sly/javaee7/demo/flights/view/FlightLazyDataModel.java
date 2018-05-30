package sly.javaee7.demo.flights.view;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import sly.javaee7.commons.crud.lazy.Criterias;
import sly.javaee7.commons.crud.lazy.LazyDataLoader;
import sly.javaee7.commons.crud.lazy.LoadResult;
import sly.javaee7.demo.flights.model.Flight;

public abstract class FlightLazyDataModel extends LazyDataModel<Flight> {
	private static final long serialVersionUID = 1L;

	private List<Flight> lastResult;

	protected abstract Criterias<Flight> getCriterias();

	protected abstract LazyDataLoader getLazyDataLoader();

	@Override
	public Flight getRowData(String rowKey) {
		for (Flight f : lastResult) {
			if (rowKey.equals(getRowKey(f))) {
				return f;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Flight f) {
		return "" + f.getId();
	}

	@Override
	public List<Flight> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		int so = sortOrder.equals(SortOrder.DESCENDING) ? 1 : -1;
		String sf = "scheduledTimeDeparture";
		LoadResult<Flight> result = getLazyDataLoader().load(Flight.class, getCriterias(), first, pageSize, sf, so);

		lastResult = result.resultList;
		setRowCount(result.rowCount);

		return lastResult;
	}
}
