package ch.hevs.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.hevs.Entitys.*;
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

            // Création des entités de base
            System.out.println("Starting database population...");

            // Création des jeux
            Game g1 = new Game("League Of Legends", "MOBA", "Riot Games");
            Game g2 = new Game("Rocket League", "Sports", "Psyonix");

            // Création des équipes esport
            EsportTeam team1 = new EsportTeam("Vitality", "Magnum", 300000.0, 111111);
            EsportTeam team2 = new EsportTeam("T1", "SK telecom", 700000.0, 222222);

            // Ajout des équipes à une liste
            List<EsportTeam> teams = new ArrayList<>();
            teams.add(team1);
            teams.add(team2);

            // Création des tournois
            Tournament t1 = new Tournament("LEC", "2025-03-22", "2025-03-28",
                    "Rue des poiriers 24, Lille 33640", 500000.0, 555555L);
            Tournament t2 = new Tournament("LFI", "2025-06-18", "2025-08-14",
                    "Rue du chignon, Paris 44000", 1000000.0, 666666L);

            // Association des jeux et des tournois
            t1.setGame(g1);
            g1.addTournament(t1);

            t2.setGame(g2);
            g2.addTournament(t2);

            // Association des équipes et des tournois
            t1.setTeams(teams);
            t2.setTeams(teams);

            team1.addTournament(t1);
            team1.addTournament(t2);

            team2.addTournament(t1);
            team2.addTournament(t2);
            

            // Création des joueurs
            Player p1 = new Player("Lee", "Sang-hyeok", "South-Korea", 28, false);
            Player p2 = new Player("Moon", "Hyeon-jun", "South-Korea", 22, false);
            Player p3 = new Player("Lee", "Min-hyeong", "South-Korea", 23, false);
            Player p4 = new Player("Choi", "Woo-je", "South-Korea", 21, true);
            Player p5 = new Player("Ryu", "Min-seok", "South-Korea", 22, false);
            Player p6 = new Player("Choi", "Hyoeon-joon", "South-Korea", 24, false);

            // Création d'un coach
            Coach c1 = new Coach("Kim", "Jeong-gyun", "South-Korea", 15);
            
            team1.setCoach(c1);
            
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
            em.persist(p6);
            em.flush();
            System.out.println("Persisted players.");
            
            List<Player> players = new ArrayList<>();
            players.add(p1);
            players.add(p2);
            
            for (Player player : players) {
                player.setTeam(team1);
            }
            team1.setPlayerList(players);

            // Persistance des entités
            em.persist(team1);
            em.persist(team2);
            em.flush(); // Force la génération des IDs pour les équipes
            System.out.println("Persisted teams with IDs: " + team1.getId() + ", " + team2.getId());

            em.persist(t1);
            em.persist(t2);
            em.flush(); // Force la génération des IDs pour les tournois
            System.out.println("Persisted tournaments with IDs: " + t1.getId() + ", " + t2.getId());

            em.persist(g1);
            em.persist(g2);
            System.out.println("Persisted games with IDs: " + g1.getIdNumber() + ", " + g2.getIdNumber());

            em.persist(c1);
            System.out.println("Persisted coach with ID: " + c1.getIdNumber());

            // Validation de la transaction
            tx.commit();
            System.out.println("Database population completed successfully.");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}