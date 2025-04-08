package ch.hevs.managedbeans;

import java.io.Serializable;
import java.util.List;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.Player;
import ch.hevs.bankservice.PersonService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ManagedBean
@SessionScoped
@Named("PersonBean")
public class PersonBean implements Serializable {
	
	@Inject
	private PersonService personService;
	
	private String personName;
	private List<Object[]> playerDetails;
	private List<Object[]> coachDetails;
	
	// Field for player
	private String playerFirstname;
	private String playerLastname;
	private String playerNationality;
	private int playerAge;
	private boolean playerInjured;
	
	// Field for coach
	private String coachFirstname;
	private String coachLastname;
	private String coachNationality;
	private int coachExperience;
	
	@PostConstruct
	public void init() {
		playerDetails = personService.getAllPlayers();
		coachDetails = personService.getAllCoaches();
	}
	
	public void searchPerson() {
		if (personName != null && !personName.trim().isEmpty()) {
			playerDetails = personService.findPlayerByName(personName);
			coachDetails = personService.findCoachByName(personName);
			if (playerDetails == null && coachDetails == null) {
				FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "No person found with the given name.", null));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Please enter a person name to search.", null));
		}
	}
	
	public void resetSearch() {
		playerDetails = personService.getAllPlayers();
		coachDetails = personService.getAllCoaches();
		FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Search reset successfully.", null));
	}
	
	public void resetForm() {
		playerFirstname="";
		playerLastname="";
		playerNationality="";
		playerAge=0;
		playerInjured=false;
		
		coachFirstname="";
		coachLastname="";
		coachNationality="";
		coachExperience=0;
	}
	
	public void createPlayer() {
		try {
			personService.addPlayer(playerFirstname, playerLastname, playerNationality, playerAge, playerInjured);
			resetForm();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create player: " + e.getMessage(), null));
			e.printStackTrace();
		}
	}
	
	public void createCoach() {
		try {
			personService.addCoach(coachFirstname, coachLastname, coachNationality, coachExperience);
			resetForm();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create coach: " + e.getMessage(), null));
			e.printStackTrace();
		}
	}
	
	// Getters and Setters

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public List<Object[]> getPlayerDetails() {
		return playerDetails;
	}

	public void setPlayerDetails(List<Object[]> playerDetails) {
		this.playerDetails = playerDetails;
	}

	public List<Object[]> getCoachDetails() {
		return coachDetails;
	}

	public void setCoachDetails(List<Object[]> coachDetails) {
		this.coachDetails = coachDetails;
	}

	public String getPlayerFirstname() {
		return playerFirstname;
	}

	public void setPlayerFirstname(String playerFirstname) {
		this.playerFirstname = playerFirstname;
	}

	public String getPlayerLastname() {
		return playerLastname;
	}

	public void setPlayerLastname(String playerLastname) {
		this.playerLastname = playerLastname;
	}

	public String getPlayerNationality() {
		return playerNationality;
	}

	public void setPlayerNationality(String playerNationality) {
		this.playerNationality = playerNationality;
	}

	public int getPlayerAge() {
		return playerAge;
	}

	public void setPlayerAge(int playerAge) {
		this.playerAge = playerAge;
	}

	public boolean isPlayerInjured() {
		return playerInjured;
	}

	public void setPlayerInjured(boolean playerInjured) {
		this.playerInjured = playerInjured;
	}

	public String getCoachFirstname() {
		return coachFirstname;
	}

	public void setCoachFirstname(String coachFirstname) {
		this.coachFirstname = coachFirstname;
	}

	public String getCoachLastname() {
		return coachLastname;
	}

	public void setCoachLastname(String coachLastname) {
		this.coachLastname = coachLastname;
	}

	public String getCoachNationality() {
		return coachNationality;
	}

	public void setCoachNationality(String coachNationality) {
		this.coachNationality = coachNationality;
	}

	public int getCoachExperience() {
		return coachExperience;
	}

	public void setCoachExperience(int coachExperience) {
		this.coachExperience = coachExperience;
	}
	
}