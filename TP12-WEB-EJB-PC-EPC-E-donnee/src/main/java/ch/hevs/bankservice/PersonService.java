package ch.hevs.bankservice;

import java.util.List;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.Player;
import jakarta.ejb.Local;

@Local
public interface PersonService {
	List<Object[]> getAllPlayers();
	List<Object[]> getAllCoaches();
	List<Object[]> findPlayerByName(String personName);
	List<Object[]> findCoachByName(String personName);
	void addPlayer(String playerFirstname, String playerLastname, String playerNationality, int playerAge, boolean playerInjured);
	void addCoach(String coachFirstname, String coachLastname, String coachNationality, int coachExperience);
}