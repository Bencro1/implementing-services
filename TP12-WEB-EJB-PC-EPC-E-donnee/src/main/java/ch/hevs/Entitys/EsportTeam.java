package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class EsportTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String teamName;

    // Relations
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> playerList;

    private String sponsor;
    
    @ManyToOne
    private Coach coach; // Relation avec Coach
    
    private double salary;
    private Long bankId;

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
