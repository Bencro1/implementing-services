package ch.hevs.bankservice;

import java.util.Arrays;
import java.util.List;

import ch.hevs.Entitys.EsportTeam;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class TeamServiceBean implements TeamService {
	
	@PersistenceContext(unitName = "bankPU")
	private EntityManager em;

	@Override
	public List<Object[]> getAllTeams() {
		List<Object[]> teams = em.createQuery(
				"SELECT t.id, t.teamName, t.sponsor " +
				"FROM EsportTeam t", Object[].class)
				.getResultList();
		System.out.println("number of teams found: " + teams.size());
		
		for (Object[] team : teams) {
			System.out.println("Team: " + Arrays.toString(team));
		}
		
		return teams;
	}
	
	@Override
	public List<Object[]> findTeamByName(String TeamName) {
		return em.createQuery(
				"SELECT e.Id, e.teamName, e.sponsor " +
				"FROM EsportTeam e " +
				"WHERE LOWER(e.teamName) LIKE LOWER(:name)", Object[].class)
				.setParameter("name", "%" + TeamName + "%")
				.getResultList();
	}
	
	@Override
	public void addTeam(String newTeamName, String newSponsor, double newSalary, long newBankId) {
		EsportTeam team = new EsportTeam();
		team.setTeamName(newTeamName);
		team.setSponsor(newSponsor);
		team.setSalary(newSalary);
		team.setBankId(newBankId);
		
		em.persist(team);
	}
	
}
