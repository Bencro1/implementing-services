package ch.hevs.managedbeans;

import ch.hevs.Entitys.Coach;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Player;
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
	
	// Fields for coach
	private String coachFirstname;
	private String coachLastname;
	private String coachNationality;
	private int coachExperience;
	private Long selectedCoachId;

	// Fields for player
	private String playerFirstname;
	private String playerLastname;
	private String playerNationality;
	private int playerAge;
	private boolean playerInjured;
	private Long selectedPlayerId;

    private EsportTeam team = new EsportTeam();
    private List<Object[]> teams;

	private List<Player> existingPlayers;
	private List<Coach> existingCoaches;

    @PostConstruct
	public void init() {
		teams = teamService.getAllTeams();
		existingPlayers = teamService.getAllPlayers(); // Fetch all players
		existingCoaches = teamService.getAllCoaches(); // Fetch all coaches
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

	public void refreshTeams() {
		teams = teamService.getAllTeams();
		System.out.println("Teams refreshed: " + teams.size());
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

	public String loadTeamDetails(Long teamId) {
		team = teamService.getTeamById(teamId);
		if (team != null) {
			return "teamDetails.xhtml?faces-redirect=true";
		}
		return null;
	}
	
	public void loadTeamDetailsFromRequest() {
		String teamIdParam = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap()
				.get("teamId");
		if (teamIdParam != null) {
			try {
				Long teamId = Long.valueOf(teamIdParam);
				team = teamService.getTeamById(teamId);
			} catch (NumberFormatException e) {
				System.err.println("Invalid teamId: " + teamIdParam);
			}
		}
	}

	public void addPlayerToTeam() {
		if (selectedPlayerId != null && team != null) {
			Player player = existingPlayers.stream()
										   .filter(p -> p.getIdNumber().equals(selectedPlayerId))
										   .findFirst()
										   .orElse(null);
			if (player != null) {
				if (player.getTeam() != null && !player.getTeam().equals(team)) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "This player is already assigned to another team.", null));
					return;
				}
	
				player.setTeam(team);
				team.getPlayerList().add(player);
				teamService.updatePlayer(player);
	
				// Rafraîchissez la liste des équipes
				refreshTeams();
	
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Player added to the team successfully!", null));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Player not found.", null));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid player or team.", null));
		}
	}

	public void addCoachToTeam() {
		if (selectedCoachId != null && team != null) {
			Coach coach = existingCoaches.stream()
										 .filter(c -> c.getIdNumber().equals(selectedCoachId))
										 .findFirst()
										 .orElse(null);
			if (coach != null) {
				if (team.getCoach() != null) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "This team already has a coach.", null));
					return;
				}
	
				if (coach.getTeam() != null) {
					// Dissociez le coach de son équipe actuelle
					EsportTeam previousTeam = coach.getTeam();
					previousTeam.setCoach(null);
					teamService.updateTeam(previousTeam);
				}
	
				coach.setTeam(team);
				team.setCoach(coach);
				teamService.updateCoach(coach);
	
				// Rafraîchissez la liste des équipes
				refreshTeams();
	
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Coach added to the team successfully!", null));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Coach not found.", null));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid coach or team.", null));
		}
	}

	// Method to create a coach
	public void createCoach() {
		try {
			Coach coach = new Coach(coachFirstname, coachLastname, coachNationality, coachExperience);
			teamService.addCoach(coach);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Coach created successfully!", null));
			resetCoachForm();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create coach: " + e.getMessage(), null));
			e.printStackTrace();
		}
	}

	// Method to create a player
	public void createPlayer() {
		try {
			Player player = new Player(playerFirstname, playerLastname, playerNationality, playerAge, playerInjured);
			teamService.addPlayer(player);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Player created successfully!", null));
			resetPlayerForm();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create player: " + e.getMessage(), null));
			e.printStackTrace();
		}
	}

	// Reset methods
	private void resetCoachForm() {
		coachFirstname = "";
		coachLastname = "";
		coachNationality = "";
		coachExperience = 0;
	}

	private void resetPlayerForm() {
		playerFirstname = "";
		playerLastname = "";
		playerNationality = "";
		playerAge = 0;
		playerInjured = false;
	}
    
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

	// Getters and setters for player fields
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
    
	public List<Player> getExistingPlayers() {
		return existingPlayers;
	}
	
	public List<Coach> getExistingCoaches() {
		return existingCoaches;
	}

	public Long getSelectedPlayerId() {
		return selectedPlayerId;
	}
	
	public void setSelectedPlayerId(Long selectedPlayerId) {
		this.selectedPlayerId = selectedPlayerId;
	}

	public Long getSelectedCoachId() {
		return selectedCoachId;
	}
	
	public void setSelectedCoachId(Long selectedCoachId) {
		this.selectedCoachId = selectedCoachId;
	}
}
