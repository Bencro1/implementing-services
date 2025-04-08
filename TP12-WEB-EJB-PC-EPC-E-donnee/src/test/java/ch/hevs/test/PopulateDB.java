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
            


           // Création des joueurs pour Team 1
            Player p1 = new Player("John", "Smith", "USA", 28, false);
            Player p2 = new Player("Lucas", "Miller", "Canada", 22, false);
            Player p3 = new Player("Ethan", "Brown", "UK", 23, false);
            Player p4 = new Player("Noah", "Wilson", "Australia", 21, true);
            Player p5 = new Player("Liam", "Taylor", "New Zealand", 22, false);

            // Création des joueurs pour Team 2
            Player p6 = new Player("Emma", "Anderson", "USA", 24, false);
            Player p7 = new Player("Olivia", "Thomas", "Canada", 25, false);
            Player p8 = new Player("Sophia", "Jackson", "UK", 26, false);
            Player p9 = new Player("Isabella", "White", "Australia", 27, false);
            Player p10 = new Player("Mia", "Harris", "New Zealand", 23, false);

            // Création des joueurs non assignés
            Player p11 = new Player("James", "Martin", "USA", 20, false);
            Player p12 = new Player("Benjamin", "Lee", "Canada", 21, false);
            Player p13 = new Player("Alexander", "Walker", "UK", 22, false);
            Player p14 = new Player("Charlotte", "Hall", "Australia", 23, false);
            Player p15 = new Player("Amelia", "Young", "New Zealand", 24, false);

            // Création des coachs
            Coach c1 = new Coach("Michael", "Scott", "USA", 15); // Coach pour Team 1
            Coach c2 = new Coach("David", "Clark", "Canada", 10); // Coach pour Team 2
            Coach c3 = new Coach("Sarah", "Lewis", "UK", 8);     // Coach non assigné

            // Persistance des joueurs et coachs
            em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4); em.persist(p5);
            em.persist(p6); em.persist(p7); em.persist(p8); em.persist(p9); em.persist(p10);
            em.persist(p11); em.persist(p12); em.persist(p13); em.persist(p14); em.persist(p15);
            em.persist(c1); em.persist(c2); em.persist(c3);
            em.flush();
            System.out.println("Persisted players and coaches.");

            // Assignation des joueurs et coachs aux équipes
            List<Player> team1Players = List.of(p1, p2, p3, p4, p5);
            List<Player> team2Players = List.of(p6, p7, p8, p9, p10);

            for (Player player : team1Players) {
                player.setTeam(team1);
            }
            for (Player player : team2Players) {
                player.setTeam(team2);
            }

            team1.setPlayerList(team1Players);
            team2.setPlayerList(team2Players);

            team1.setCoach(c1);
            team2.setCoach(c2);

            // Persistance des équipes
            em.persist(team1);
            em.persist(team2);
            em.flush();
            System.out.println("Persisted teams with players and coaches.");

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