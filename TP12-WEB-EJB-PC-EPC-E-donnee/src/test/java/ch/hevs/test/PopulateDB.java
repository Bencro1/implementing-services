package ch.hevs.test;

import java.sql.SQLException;

import org.junit.Test;

import ch.hevs.Entitys.Tournament;
import ch.hevs.businessobject.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import junit.framework.TestCase;

public class PopulateDB extends TestCase {

	@Test
	public void test() throws SQLException, ClassNotFoundException {
		
		EntityTransaction tx = null;
		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU_unitTest");
			EntityManager em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			
			Tournament t1 = new Tournament("LEC", "2025-03-22", "2025-03-28", "Rue des poiriers 24, Lille 33640", 500000.0, 555555);
			Tournament t2 = new Tournament("LFI", "2025-06-18", "2025-08-14", "Rue du chignon, Paris 44000", 1000000.0, 666666);
			
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
