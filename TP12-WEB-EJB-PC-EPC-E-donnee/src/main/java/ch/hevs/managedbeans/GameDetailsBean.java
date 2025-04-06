package ch.hevs.managedbeans;

import ch.hevs.bankservice.GameService;
import ch.hevs.Entitys.Game;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("GameDetailsBean")
public class GameDetailsBean implements Serializable {

    @Inject
    private GameService gameService;

    private String searchGameName; // For searching games
    private String newGameName;    // For creating a new game
    private String category;
    private String developer;

    private List<Game> gameDetails;

    private Game game;

    @PostConstruct
    public void init() {
        gameDetails = gameService.getAllGames();
        if (gameDetails == null || gameDetails.isEmpty()) {
            System.out.println("No games found.");
            gameDetails = List.of();
        } else {
            System.out.println("Games loaded: " + gameDetails.size());
            for (Game game : gameDetails) {
                System.out.println("Game: " + game.getGameName());
            }
        }
    }

    public void searchGame() {
        if (searchGameName != null && !searchGameName.trim().isEmpty()) {
            gameDetails = gameService.findGameByName(searchGameName);
            if (gameDetails == null || gameDetails.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "No games found with the given name.", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Please enter a game name to search.", null));
        }
    }

    public void resetSearch() {
        gameDetails = gameService.getAllGames();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Search reset successfully.", null));
    }

    public void createGame() {
        try {
            Game newGame = new Game(newGameName, category, developer);
            gameService.addGame(newGame);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Game created successfully!", null));
            resetForm();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create game: " + e.getMessage(), null));
            e.printStackTrace();
        }
    }

    private void resetForm() {
        newGameName = "";
        category = "";
        developer = "";
    }

    public List<Game> getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(List<Game> gameDetails) {
        this.gameDetails = gameDetails;
    }

    public String getSearchGameName() {
        return searchGameName;
    }

    public void setSearchGameName(String searchGameName) {
        this.searchGameName = searchGameName;
    }

    public String getNewGameName() {
        return newGameName;
    }

    public void setNewGameName(String newGameName) {
        this.newGameName = newGameName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}