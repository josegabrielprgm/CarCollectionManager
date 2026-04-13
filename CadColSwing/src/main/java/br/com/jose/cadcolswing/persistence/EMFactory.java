package br.com.jose.cadcolswing.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFactory {

	private static EntityManagerFactory factory;
	private static EntityManager em;

	public static EntityManager getEm() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("cadcolPU");
		}

		if (em == null || !em.isOpen()) {
			em = factory.createEntityManager();
		}

		return em;
	}
}