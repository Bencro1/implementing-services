package ch.hevs.businessobject;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "coach")
@PrimaryKeyJoinColumn(name = "idNumber")
public class Coach extends Person {
    private int yearsOfExperience;

    // Getters and Setters
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
