package sly.javaee7.commons.crud.lazy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import sly.javaee7.commons.crud.QueriesTest;
import sly.javaee7.demo.flights.model.Airport;
import sly.javaee7.demo.flights.test.ContainerTestBase;

@RunWith(Arquillian.class)
public class LazyDataLoaderTest extends ContainerTestBase {

	@Inject
	private LazyDataLoader loader;

	@Test
	public void LazyLoading_PageSize() {
		LoadResult<Airport> result = loader.load(Airport.class, QueriesTest.CRIT_TEST, 0, 2, null, 0);
		assertNotNull(result);
		assertTrue(result.rowCount > 2);
		assertTrue(!result.resultList.isEmpty());
		assertTrue(result.resultList.size() == 2);

	}

}
