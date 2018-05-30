package sly.javaee7.commons.crud;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import sly.javaee7.commons.crud.lazy.Criterias;
import sly.javaee7.commons.crud.lazy.LoadResult;
import sly.javaee7.demo.flights.model.Airport;
import sly.javaee7.demo.flights.test.ContainerTestBase;

@RunWith(Arquillian.class)
public class QueriesTest extends ContainerTestBase {

	public static Criterias<Airport> CRIT_TEST = new Criterias<Airport>() {

		@Override
		public Predicate getCriterias(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<Airport> from) {
			Path<String> codeField = from.get("code");
			return cb.and(cb.isNotNull(codeField), cb.gt(cb.length(codeField), 1));
		}
	};

	@Inject
	private Queries q;

	@Test
	public void testGetAllByCriteria() {
		LoadResult<Airport> result = q.getAllByCriteria(Airport.class, CRIT_TEST);
		assertNotNull(result);
		assertTrue(result.rowCount > 0);
	}

	@Test
	public void testGetRecordCount() {
		long cnt = q.getRecordCount(Airport.class);
		assertTrue(cnt > 0);
	}

	@Test
	public void testGetRecordIds() {
		List<Object> ids = q.getRecordIds(Airport.class);
		assertNotNull(ids);
		assertTrue(ids.size() > 0);
	}

}
