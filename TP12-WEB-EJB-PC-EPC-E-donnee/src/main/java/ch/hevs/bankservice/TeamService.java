package ch.hevs.bankservice;

import java.util.List;

import ch.hevs.Entitys.EsportTeam;
import jakarta.ejb.Local;

@Local
public interface TeamService {
	List<Object[]> getAllTeams();
	List<Object[]> findTeamByName(String TeamName);
}
