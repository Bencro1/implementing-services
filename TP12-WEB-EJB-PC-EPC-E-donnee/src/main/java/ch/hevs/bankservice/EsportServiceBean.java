package ch.hevs.bankservice;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Game;
import ch.hevs.Entitys.Tournament;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class EsportServiceBean implements EsportService {
    
    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;

 /*   @Override
    public void addTeams(Long tournamentId, List<Long> teamIds) {
        Tournament tournament = em.find(Tournament.class, tournamentId);
        List<EsportTeam> teams = em.createQuery(
        		"SELECT t FROM EsportTeam t WHERE t.id IN :ids", EsportTeam.class)
        		.setParameter("ids", teamIds)
        		.getResultList();
        
        		for (EsportTeam team : teams) {
        			tournament.getTeams().add(team);
        		}
    }	*/

    @Override
    @Transactional
    public void addTournament(String newTournamentName, String newStartDate, String newEndDate, String newLocation, double cashPrize, long bankId, Long gameId, List<Long> teamsIds) {
    	Tournament tournament = new Tournament();
    	tournament.setTournamentName(newTournamentName);
    	tournament.setStartDate(newStartDate);
    	tournament.setEndDate(newEndDate);
    	tournament.setLocation(newLocation);
    	tournament.setCashPrize(cashPrize);
    	tournament.setBankId(bankId);
    	
    	Game selected = em.find(Game.class, gameId);
    	tournament.setGame(selected);
    	
    	// Attach again entities to entity manager    	
    	List<EsportTeam> managedTeams = getTeamsByIds(teamsIds);
    	
    	tournament.setTeams(managedTeams);
    	
        em.persist(tournament);
    }
    
    public List<EsportTeam> getTeamsByIds(List<Long> teamIds) {
    	List<EsportTeam> teams = new ArrayList<>();
    	for (Long teamId : teamIds) {
    		EsportTeam team = em.find(EsportTeam.class, teamId);
    		if (team != null) {
    			teams.add(team);
    		}
    	}
    	return teams;
    }

 /*   @Override
    public void addGame(Game game) {
        em.persist(game);
    }	*/

    @Override
    public List<EsportTeam> getAllTeams() {
        return em.createQuery("SELECT e FROM EsportTeam e", EsportTeam.class).getResultList();
    }

    @Override
    public List<Object[]> getAllTournaments() {
        List<Object[]> tournaments = em.createQuery(
            "SELECT t.id, t.tournamentName, t.startDate, t.endDate, t.location, g.gameName " +
            "FROM Tournament t " +
            "LEFT JOIN t.game g", Object[].class)
            .getResultList();
        
        System.out.println("Number of tournaments found: " + tournaments.size());
        
        // Log each tournament for debugging
        for (Object[] tournament : tournaments) {
            System.out.println("Tournament: " + Arrays.toString(tournament));
        }
        
        return tournaments;
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
            "SELECT t.id, t.tournamentName, t.startDate, t.endDate, t.location, g.gameName " +
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
    
    @Override
    public Tournament getTournamentById(Long id) {
        System.out.println("Searching for tournament with ID: " + id);
        Tournament tournament = em.find(Tournament.class, id);
        if (tournament == null) {
            System.err.println("Tournament not found for ID: " + id);
        }
        return tournament;
    }
}
