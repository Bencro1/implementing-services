package ch.hevs.test;

import java.sql.SQLException;

import org.junit.Test;

import ch.hevs.Entitys.*;
import ch.hevs.businessobject.Client;
import ch.hevs.bankservice.EsportServiceBean;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

public class PopulateDB extends TestCase {

	@Test
	public void test() throws SQLException, ClassNotFoundException {
		
		EntityTransaction tx = null;
		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU_unitTest");
			EntityManager em = emf.createEntityManager();
			
			tx = em.getTransaction();
			tx.begin();
			
			// Creation of some entities as base data
			// Tournaments creation

			// Avant la création des tournois, initialise les listes
			List<EsportTeam> teams = new ArrayList<>();
			List<Game> games = new ArrayList<>();

			// Ajoute des équipes et des jeux aux listes
			teams.add(new EsportTeam("Vitality", "Magnum", 300000.0, 111111L));
			teams.add(new EsportTeam("T1", "SK telecom", 700000.0, 222222L));

			games.add(new Game("League Of Legends", "MOBA", "Riot Games"));
			games.add(new Game("Rocket League", "Sports", "Psyonix"));

			// Création des tournois avec les listes correctement initialisées
			Tournament t1 = new Tournament("LEC", "2025-03-22", "2025-03-28", 
			    "Rue des poiriers 24, Lille 33640", 500000.0, 555555L, teams, games);

			Tournament t2 = new Tournament("LFI", "2025-06-18", "2025-08-14", 
			    "Rue du chignon, Paris 44000", 1000000.0, 666666L, teams, games);

			
			// Game creation
			Game g1 = new Game("League Of Legends", "MOBA", "Riot Games");
			Game g2 = new Game("Rocket League", "Sports", "Psyonix");
			
			// Esport team creation
			EsportTeam team1 = new EsportTeam("Vitality", "Magnum", 300000.0, 111111);
			EsportTeam team2 = new EsportTeam("T1", "SK telecom", 700000.0, 222222);
			
			// Player creation
			Player p1 = new Player("Lee", "Sang-hyeok", "South-Korea", 28, false);
			Player p2 = new Player("Moon", "Hyeon-jun", "South-Korea", 22, false);
			Player p3 = new Player("Lee", "Min-hyeong", "South-Korea", 23, false);
			Player p4 = new Player("Choi", "Woo-je", "South-Korea", 21, true);
			Player p5 = new Player("Ryu", "Min-seok", "South-Korea", 22, false);
			Player p6 = new Player("Choi", "Hyoeon-joon", "South-Korea", 24, false);
			
			// Coach creation
			Coach c1 = new Coach("Kim", "Jeong-gyun", "South-Korea", 15);
			
			t1.setGame(g1);
			g1.addTournament(t1);
			
			em.persist(t1);
			em.persist(t2);
			
			em.persist(g1);
			em.persist(g2);
			
			em.persist(team1);
			em.persist(team2);
			
			em.persist(p1);
			em.persist(p2);
			em.persist(p3);
			em.persist(p4);
			em.persist(p5);
			em.persist(p6);
			
			em.persist(c1);
			
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
