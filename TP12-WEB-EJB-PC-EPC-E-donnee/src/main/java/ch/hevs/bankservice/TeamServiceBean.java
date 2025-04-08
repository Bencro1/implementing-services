package ch.hevs.bankservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Player;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class TeamServiceBean implements TeamService {
	
	@PersistenceContext(unitName = "bankPU")
	private EntityManager em;

	@Override
	public List<Object[]> getAllTeams() {
		return em.createQuery(
				"SELECT DISTINCT t.id, t.teamName, t.sponsor " +
				"FROM EsportTeam t", Object[].class)
				.getResultList();
	}
	
	@Override
	public List<Object[]> findTeamByName(String teamName) {
		return em.createQuery(
				"SELECT DISTINCT t.id, t.teamName, t.sponsor " +
				"FROM EsportTeam t " +
				"WHERE LOWER(t.teamName) LIKE LOWER(:name)", Object[].class)
				.setParameter("name", "%" + teamName + "%")
				.getResultList();
	}
	
	@Override
	public void addTeam(String newTeamName, String newSponsor, double newSalary, Long newBankId, Long selectedCoachId, List<Long> selectedPlayerIds) {
	/*	// Vérifiez si une équipe avec le même nom et sponsor existe déjà
		List<EsportTeam> existingTeams = em.createQuery(
				"SELECT t FROM EsportTeam t WHERE t.teamName = :teamName AND t.sponsor = :sponsor", EsportTeam.class)
				.setParameter("teamName", newTeamName)
				.setParameter("sponsor", newSponsor)
				.getResultList();
	
		if (!existingTeams.isEmpty()) {
			// Si une équipe existe déjà, ne rien faire ou lever une exception
			System.out.println("Team already exists: " + newTeamName);
			return;
		}
	
		// Sinon, créez une nouvelle équipe
		EsportTeam team = new EsportTeam();
		team.setTeamName(newTeamName);
		team.setSponsor(newSponsor);
		team.setSalary(newSalary);
		team.setBankId(newBankId);	*/
		
		EsportTeam team = new EsportTeam();
		team.setTeamName(newTeamName);
		team.setSponsor(newSponsor);
		team.setSalary(newSalary);
		team.setBankId(newBankId);
		
		Coach selected = em.find(Coach.class, selectedCoachId);
		team.setCoach(selected);
		
		List<Player> managedPlayers = getPlayersById(selectedPlayerIds);
		
		team.setPlayerList(managedPlayers);
	
		em.persist(team);
	}
	
	public List<Player> getPlayersById(List<Long> playerIds) {
		List<Player> players = new ArrayList<>();
		for (Long playerId : playerIds) {
			Player player = em.find(Player.class, playerId);
			if(player != null) {
				players.add(player);
			}
		}
		return players;
	}

	@Override
	public List<Player> getAllPlayers() {
		return em.createQuery("SELECT p FROM Player p", Player.class).getResultList();
	}

	@Override
	public List<Coach> getAllCoaches() {
		return em.createQuery("SELECT DISTINCT c FROM Coach c", Coach.class).getResultList();
	}

	@Override
    public void addPlayer(Player player) {
        em.persist(player);
    }

    @Override
    public void addCoach(Coach coach) {
        em.persist(coach);
    }

	@Override
	public EsportTeam getTeamById(Long id) {
		return em.createQuery(
				"SELECT t FROM EsportTeam t " +
				"LEFT JOIN FETCH t.playerList " +
				"LEFT JOIN FETCH t.coach " +
				"WHERE t.id = :id", EsportTeam.class)
				.setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public void updateCoach(Coach coach) {
		if (coach.getIdNumber() == null) {
			throw new IllegalArgumentException("Cannot update a coach without an ID.");
		}
		em.merge(coach);
	}

	@Override
	public void updatePlayer(Player player) {
		if (player.getIdNumber() == null) {
			throw new IllegalArgumentException("Cannot update a player without an ID.");
		}
		em.merge(player); // Utilise `merge` pour mettre à jour l'entité existante
	}

	@Override
	public void updateTeam(EsportTeam team) {
		if (team.getId() == null) {
			throw new IllegalArgumentException("Cannot update a team without an ID.");
		}
		em.merge(team);
		em.flush(); // Force la synchronisation avec la base de données
	}
}
