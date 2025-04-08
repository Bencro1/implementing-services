package ch.hevs.bankservice;

import java.util.List;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Player;
import jakarta.ejb.Local;

@Local
public interface TeamService {
	List<Object[]> getAllTeams();
	List<Player> getAllPlayers();
	List<Coach> getAllCoaches();
	List<Object[]> findTeamByName(String TeamName);
	void addTeam(String newTeamName, String newSponsor, double newSalary, Long newBankId, Long selectedCoachId, List<Long> selectedPlayerIds);
	void addPlayer(Player player);
	void addCoach(Coach coach);
	void updatePlayer(Player player);
	void updateCoach(Coach coach);
	void updateTeam(EsportTeam team);
	EsportTeam getTeamById(Long id);
}
