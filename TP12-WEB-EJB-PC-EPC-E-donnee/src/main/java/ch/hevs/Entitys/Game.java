package ch.hevs.Entitys;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long idNumber;
    private String gameName;
    private String category;
    private String developer;
    
    // Constructor
    
    public Game() {
    	
    }
    
    public Game(String gameName, String category, String developer) {
    	this.gameName = gameName;
    	this.category = category;
    	this.developer = developer;
    }
    
    // Relations
    
    @OneToMany(mappedBy="game")
    private List<Tournament> tournamentList;

    // Getters and Setters
    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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
    
    public List<Tournament> getTournaments() {
    	return tournamentList;
    }
    
    public void setTournaments(List<Tournament> tournamentList) {
    	this.tournamentList = tournamentList;
    }
    
    // Methods
    public void addTournament(Tournament tournament) {
    	if (tournamentList == null) {
    		this.tournamentList = new ArrayList<>();
    		this.tournamentList.add(tournament);
    	} else {
    		this.tournamentList.add(tournament);
    	}
    }
}
