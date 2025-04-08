package ch.hevs.bankservice;

import java.util.List;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.Player;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PersonServiceBean implements PersonService {
	
	@PersistenceContext(unitName="bankPU")
	private EntityManager em;
	
	public List<Object[]> getAllPlayers() {
		return em.createQuery(
				"SELECT p.idNumber, p.firstname, p.lastname, p.nationality, p.age, p.injured " +
				"FROM Player p", Object[].class)
				.getResultList();
	}
	
	public List<Object[]> findPlayerByName(String personName) {
		return em.createQuery(
				"SELECT p.idNumber, p.firstname, p.lastname, p.nationality, p.age, p.injured " +
				"FROM Player p " +
				"WHERE LOWER(p.firstname) LIKE LOWER(:name)", Object[].class)
				.setParameter("name", "%" + personName + "%")
				.getResultList();
	}
	
	public List<Object[]> getAllCoaches() {
		return em.createQuery(
				"SELECT c.idNumber, c.firstname, c.lastname, c.nationality, c.yearsOfExperience " +
				"FROM Coach c", Object[].class)
				.getResultList();
	}
	
	public List<Object[]> findCoachByName(String personName) {
		return em.createQuery(
				"SELECT c.idNumber, c.firstname, c.lastname, c.nationality, c.yearsOfExperience " +
				"FROM Coach c " +
				"WHERE LOWER(c.firstname) LIKE LOWER(:name)", Object[].class)
				.setParameter("name", "%" + personName + "%")
				.getResultList();
	}
	
	public void addPlayer(String playerFirstname, String playerLastname, String playerNationality, int playerAge, boolean playerInjured) {
		Player player = new Player();
		player.setFirstname(playerFirstname);
		player.setLastname(playerLastname);
		player.setNationality(playerNationality);
		player.setAge(playerAge);
		player.setInjured(playerInjured);
		
		em.persist(player);
	}
	
	public void addCoach(String coachFirstname, String coachLastname, String coachNationality, int coachExperience) {
		Coach coach = new Coach();
		coach.setFirstname(coachFirstname);
		coach.setLastname(coachLastname);
		coach.setNationality(coachNationality);
		coach.setYearsOfExperience(coachExperience);
		
		em.persist(coach);
	}
}