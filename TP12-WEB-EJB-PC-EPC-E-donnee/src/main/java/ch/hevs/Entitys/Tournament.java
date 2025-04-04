package ch.hevs.Entitys;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

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
    	this.esportTeamList = new ArrayList<>();
    	System.out.println("espor teams works: " + esportTeamList);
    }
    
    public Tournament(String tournamentName, String startDate, String endDate, String location, double cashPrize, long bankId) {
    	this.esportTeamList = new ArrayList<>();
    	
    	this.tournamentName = tournamentName;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.location = location;
    	this.cashPrize = cashPrize;
    	this.bankId = bankId;
    }
    
    // Relations
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<EsportTeam> esportTeamList;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

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
    
    public Game getGame() {
    	return game;
    }
    
    public void setGame(Game game) {
    	this.game = game;
    }
    
    // Methods
    
}
