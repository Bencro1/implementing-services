package ch.hevs.businessobject;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "player")
@PrimaryKeyJoinColumn(name = "idNumber")
public class Player extends Person {
    private int age;
    private boolean injured;
    
    @ManyToOne
    @JoinColumn(name = "team_id")
    private EsportTeam team;	// Relation with EsportTeam

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
