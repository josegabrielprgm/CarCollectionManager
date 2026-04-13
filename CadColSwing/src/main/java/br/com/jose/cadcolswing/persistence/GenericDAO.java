package br.com.jose.cadcolswing.persistence;

import java.util.List;

import jakarta.persistence.EntityManager;

public class GenericDAO<T> {

	protected EntityManager em;

	private Class<T> clazz;

	public GenericDAO(Class<T> clazz) {

		this.clazz = clazz;
		this.em = EMFactory.getEm();
	}

	// READ
	public List<T> listar() {

		String jpql = "select o from " + clazz.getName() + " o";
		return em.createQuery(jpql, clazz).getResultList();
	}

	public List<T> listar(String sql) {

		String jpql = "select o from " + clazz.getName() + " o where ";
		jpql = jpql + sql;
		return em.createQuery(jpql, clazz).getResultList();
	}

	// READ(buscar)
	public T buscar(Object id) {

		return (T) em.find(clazz, id);
	}

	// CREATE
	public T criar(T t) {

		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		return t;
	}

	// UPDATE
	public T atualizar(T t) {

		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		return t;
	}

	// DELETE
	public void apagar(Object id) {
		try {

			T t = em.find(clazz, id);

			em.getTransaction().begin();
			em.remove(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
