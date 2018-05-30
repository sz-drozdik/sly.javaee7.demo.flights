package sly.javaee7.commons.crud;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Operations {

	public enum LCOperation {
		Delete, New, Update
	};

	@PersistenceContext
	private EntityManager em;

	public <T extends Identifiable> T create(T entity) {
		em.persist(entity);
		return entity;
	}

	public <T extends Identifiable> void delete(T entity) {
		em.remove(getRef(entity));
	}

	public <T extends Identifiable> T find(Class<T> clazz, Object id) {
		return em.find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <T extends Identifiable> T find(T entity) {
		return (T) em.find(entity.getClass(), entity.getId());
	}

	@SuppressWarnings("unchecked")
	private <T extends Identifiable> T getRef(T entity) {
		return (T) em.getReference(entity.getClass(), entity.getId());
	}

	public <T extends Identifiable> T update(T entity) {
		return em.merge(entity);
	}
}
