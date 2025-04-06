package ch.hevs.Entitys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "coach")
@PrimaryKeyJoinColumn(name = "idNumber")
public class Coach extends Person {
    private int yearsOfExperience;
    
    // Constructors
    
    public Coach() {
    	
    }
    
    public Coach(String firstname, String lastname, String nationality, int yearsOfExperience) {
    	setFirstname(firstname);
    	setLastname(lastname);
    	setNationality(nationality);
    	this.yearsOfExperience = yearsOfExperience;
    }
    
    // Relations
    
    @OneToOne(mappedBy="coach", cascade = CascadeType.ALL)
    private EsportTeam team;	//relation with EsportTeam

    // Getters and Setters
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public EsportTeam getTeam() {
    	return team;
    }
    
    public void setTeam(EsportTeam team) {
    	this.team = team;
    }
}
