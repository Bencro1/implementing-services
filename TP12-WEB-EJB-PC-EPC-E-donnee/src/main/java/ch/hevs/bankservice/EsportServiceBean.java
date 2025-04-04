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
	public List<Object[]> getAllTournaments() {
    	return em.createQuery(
                "SELECT t.tournamentName, t.startDate, t.endDate, t.location, g.gameName " +
                "FROM Tournament t " +
                "LEFT JOIN t.game g ", Object[].class)
                .getResultList();
	}

	@Override
    public List<Object[]> getTournamentsWithDetails() {
        return em.createQuery(
            "SELECT t.tournamentName, e.teamName, g.gameName " +
            "FROM Tournament t " +
            "LEFT JOIN t.esportTeamList e " +
            "LEFT JOIN t.game g", Object[].class)
            .getResultList();
    }
    
    @Override
    public List<Object[]> findTournamentByName(String tournamentName) {
        return em.createQuery(
            "SELECT t.tournamentName, t.startDate, t.endDate, t.location, g.gameName " +
            "FROM Tournament t " +
            "LEFT JOIN t.game g " +
            "WHERE LOWER(t.tournamentName) LIKE LOWER(:name)", Object[].class)
            .setParameter("name", "%" + tournamentName + "%")
            .getResultList();
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
