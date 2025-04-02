package ch.hevs.Entitys;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class EsportTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String teamName;
    private String sponsor;
    private double salary;
    private Long bankId;
    
    // Constructors
    
    public EsportTeam() {
    	
    }
    
    public EsportTeam(String teamName, String sponsor, double salary, long bankId) {
    	this.teamName = teamName;
    	this.sponsor = sponsor;
    	this.salary = salary;
    	this.bankId = bankId;
    }

    // Relations
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> playerList;
    
    @OneToOne
    @JoinColumn(name = "coach_id")
    private Coach coach; // Relation avec Coach
    
    @ManyToMany(mappedBy="esportTeamList")
    private List<Tournament> tournamentList;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }
    
    public List<Tournament> getTournamentList() {
    	return tournamentList;
    }
    
    public void setTournamentList(List<Tournament> tournamentList) {
    	this.tournamentList = tournamentList;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}
