package ch.hevs.bankservice;

import ch.hevs.Entitys.Game;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface GameService {
    void addGame(Game game);
    List<Game> getAllGames();
    List<Game> findGameByName(String gameName);
    Game getGameById(Long id);
}