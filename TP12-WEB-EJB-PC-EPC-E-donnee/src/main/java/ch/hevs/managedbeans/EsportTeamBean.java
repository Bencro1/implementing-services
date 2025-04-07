package ch.hevs.managedbeans;

import ch.hevs.Entitys.EsportTeam;
import ch.hevs.bankservice.EsportService;
import ch.hevs.bankservice.TeamService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("EsportTeamBean")
public class EsportTeamBean implements Serializable {
    
	@Inject
    private TeamService teamService;

	private String teamName;
	private String newTeamName;
	private String newSponsor;
	private double newSalary;
	private Long newBankId;
	
    private EsportTeam team = new EsportTeam();
    private List<Object[]> teams;

    @PostConstruct
    public void init() {
        teams = teamService.getAllTeams();
        if (teams == null || teams.isEmpty()) {
        	System.out.println("No teams found.");
        	teams = List.of();
        } else {
        	System.out.println("Teams loaded: " + teams.size());
        	for (Object[] team : teams) {
        		System.out.println("Team: " + Arrays.toString(team));
        	}
        }
    }
    
    public void searchTeam() {
    	if (teamName != null && !teamName.trim().isEmpty()) {
    		teams = teamService.findTeamByName(teamName);
    		if (teams == null || teams.isEmpty()) {
    			FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "No tournaments found with the given name.", null));
    		}
    	} else {
    		FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Please enter a tournament name to search.", null));
    	}
    }
    
    public void resetSearch() {
    	teams = teamService.getAllTeams();
    	FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Search reset successfully.", null));
    }
    
    public void createTeam() {
    	try {
    		teamService.addTeam(newTeamName, newSponsor, newSalary, newBankId);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Team created successfully!", null));
            resetForm();
    	} catch (Exception e) {
    		FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create new team: " + e.getMessage(), null));
            e.printStackTrace();
    	}
    }
    
    private void resetForm() {
    	newTeamName = "";
    	newSponsor = "";
    	newSalary = 0;
    	newBankId = 0L;
    }

  /*  public void addTeam() {
        esportService.addTeam(team);
        team = new EsportTeam(); // Reset the form
        teams = esportService.getAllTeams(); // Refresh the list
    }	*/
    
    // Getters and setters

    public EsportTeam getTeam() {
        return team;
    }

    public void setTeam(EsportTeam team) {
        this.team = team;
    }

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<Object[]> getTeams() {
		return teams;
	}

	public void setTeams(List<Object[]> teams) {
		this.teams = teams;
	}

	public String getNewTeamName() {
		return newTeamName;
	}

	public void setNewTeamName(String newTeamName) {
		this.newTeamName = newTeamName;
	}

	public String getNewSponsor() {
		return newSponsor;
	}

	public void setNewSponsor(String newSponsor) {
		this.newSponsor = newSponsor;
	}

	public double getNewSalary() {
		return newSalary;
	}

	public void setNewSalary(double newSalary) {
		this.newSalary = newSalary;
	}

	public Long getNewBankId() {
		return newBankId;
	}

	public void setNewBankId(Long newBankId) {
		this.newBankId = newBankId;
	}
    
    
}
