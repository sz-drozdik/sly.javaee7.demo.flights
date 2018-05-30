package sly.javaee7.commons.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import sly.javaee7.demo.flights.model.Airport;
import sly.javaee7.demo.flights.test.ContainerTestBase;

@RunWith(Arquillian.class)
public class OperationsTest extends ContainerTestBase {
	
	@Inject
	private Operations ops;

	@Test
	public void Entity_Creation() {
		Airport a = ops.create(new Airport());
		assertNotNull(a);
		assertNotNull(a.getId());		
	}
	
	@Test
	public void Entity_Update_Find() {
		final String code = "123";
		Airport a = ops.create(new Airport());
		a.setCode(code);
		Long id = a.getId();
		ops.update(a);
		Airport a2 = ops.find(Airport.class, id);
		assertNotNull(a2);
		assertEquals(code, a2.getCode());
	}

	@Test
	public void Entity_Delete_Find() {
		Airport a = ops.create(new Airport());
		Long id = a.getId();
		ops.delete(a);
		Airport a2 = ops.find(Airport.class, id);
		assertTrue(a2 == null);
	}

}
