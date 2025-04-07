package ch.hevs.bankservice;

import java.util.List;

import ch.hevs.Entitys.EsportTeam;
import jakarta.ejb.Local;

@Local
public interface TeamService {
	List<Object[]> getAllTeams();
	List<Object[]> findTeamByName(String TeamName);
	void addTeam(String newTeamName, String newSponsor, double newSalary, long newBankId);
}
