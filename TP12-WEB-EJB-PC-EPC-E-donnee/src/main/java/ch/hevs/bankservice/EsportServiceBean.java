package ch.hevs.bankservice;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Game;
import ch.hevs.Entitys.Tournament;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class EsportServiceBean implements EsportService {
    
    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;

    @Override
    public void addTeam(EsportTeam team) {
        em.persist(team);
    }

    @Override
    public void addTournament(Tournament tournament) {
        em.persist(tournament);
    }

    @Override
    public void addGame(Game game) {
        em.persist(game);
    }

    @Override
    public List<EsportTeam> getAllTeams() {
        return em.createQuery("SELECT e FROM EsportTeam e", EsportTeam.class).getResultList();
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return em.createQuery("SELECT t FROM Tournament t", Tournament.class).getResultList();
    }

    @Override
    public List<Game> getAllGames() {
        return em.createQuery("SELECT g FROM Game g", Game.class).getResultList();
    }

    @Override
    public void addCoach(Coach coach) {
        em.persist(coach);
    }
}
