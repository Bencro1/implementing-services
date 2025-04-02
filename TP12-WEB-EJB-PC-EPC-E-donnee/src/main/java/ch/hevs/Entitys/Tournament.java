package ch.hevs.Entitys;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String tournamentName;
    private String startDate;
    private String endDate;
    private String location;
    private double cashPrize;
    private Long bankId;
    
    // Constructors
    
    public Tournament() {
    	
    }
    
	    public Tournament(String tournamentName, String startDate, String endDate, String location, 
	            double cashPrize, long bankId, List<EsportTeam> esportTeamList, List<Game> gameList) {
	this.tournamentName = tournamentName;
	this.startDate = startDate;
	this.endDate = endDate;
	this.location = location;
	this.cashPrize = cashPrize;
	this.bankId = bankId;
	this.esportTeamList = esportTeamList;
	this.gameList = gameList;
	}

    
    // Relations
    
	@ManyToMany(fetch = FetchType.EAGER)
    private List<EsportTeam> esportTeamList;
    
	@ManyToMany(fetch = FetchType.EAGER)
    private List<Game> gameList;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCashPrize() {
        return cashPrize;
    }

    public void setCashPrize(double cashPrize) {
        this.cashPrize = cashPrize;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
    
    public List<EsportTeam> getTeams() {
    	return esportTeamList;
    }
    
    public void setTeams(List<EsportTeam> esportTeamList) {
    	this.esportTeamList = esportTeamList;
    }
    
    public List<Game> getGames() {
    	return gameList;
    }
    
    public void setGames(List<Game> gameList) {
    	this.gameList = gameList;
    }
}
