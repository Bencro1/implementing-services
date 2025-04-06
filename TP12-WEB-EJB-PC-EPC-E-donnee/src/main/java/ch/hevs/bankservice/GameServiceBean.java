package ch.hevs.bankservice;

import ch.hevs.Entitys.Game;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class GameServiceBean implements GameService {

    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;

    @Override
    public void addGame(Game game) {
        em.persist(game);
    }

    @Override
    public List<Game> getAllGames() {
        return em.createQuery("SELECT g FROM Game g", Game.class).getResultList();
    }

    @Override
    public List<Game> findGameByName(String gameName) {
        return em.createQuery("SELECT g FROM Game g WHERE LOWER(g.gameName) LIKE LOWER(:name)", Game.class)
                 .setParameter("name", "%" + gameName + "%")
                 .getResultList();
    }

    @Override
    public Game getGameById(Long id) {
        return em.find(Game.class, id);
    }
}