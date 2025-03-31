package ch.hevs.service;

import ch.hevs.businessobject.Coach;
import ch.hevs.businessobject.EsportTeam;
import ch.hevs.businessobject.Game;
import ch.hevs.businessobject.Tournament;

import jakarta.ejb.Local;

import java.util.List;

@Local
public interface EsportService {
    void addTeam(EsportTeam team);
    void addTournament(Tournament tournament);
    void addGame(Game game);
    List<EsportTeam> getAllTeams();
    List<Tournament> getAllTournaments();
    List<Game> getAllGames();
    void addCoach(Coach coach);
}
