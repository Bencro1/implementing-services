package ch.hevs.bankservice;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Game;
import ch.hevs.Entitys.Tournament;

import jakarta.ejb.Local;

import java.util.List;

@Local
public interface EsportService {
    void addTeam(EsportTeam team);
    void addTournament(Tournament tournament);
    void addGame(Game game);
    List<Object[]> findTournamentByName(String tournamentName);
    List<EsportTeam> getAllTeams();
    List<Object[]> getAllTournaments();
    List<Game> getAllGames();
    List<Object[]> getTournamentsWithDetails();
    void addCoach(Coach coach);
    Tournament getTournamentById(Long id);
}
