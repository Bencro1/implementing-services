package ch.hevs.businessobject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long idNumber;
    private String gameName;
    private String category;
    private String developer;
    
    // Relations
    
    @ManyToMany(mappedBy="gameList")
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
}
