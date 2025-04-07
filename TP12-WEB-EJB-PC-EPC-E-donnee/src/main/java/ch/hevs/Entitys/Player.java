package ch.hevs.Entitys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "player")
@PrimaryKeyJoinColumn(name = "idNumber")
public class Player extends Person {
    private int age;
    private boolean injured;
    
    // Constructors
    
    public Player() {
    	
    }
    
    public Player(String firstname, String lastname, String nationality, int age, boolean injured) {
    	setFirstname(firstname);
    	setLastname(lastname);
    	setNationality(nationality);
    	this.age = age;
    	this.injured = injured;
    }
    
    @ManyToOne
    @JoinColumn(name = "team_id")
    private EsportTeam team;

    // Getters and Setters
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public EsportTeam getTeam() {
    	return team;
    }
    
    public void setTeam(EsportTeam team) {
    	this.team = team;
    }

    public boolean isInjured() {
        return injured;
    }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }
}
