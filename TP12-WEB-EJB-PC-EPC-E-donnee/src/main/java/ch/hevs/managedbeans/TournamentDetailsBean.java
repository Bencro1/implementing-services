package ch.hevs.managedbeans;

import ch.hevs.bankservice.EsportService;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Game;
import ch.hevs.Entitys.Tournament;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("TournamentDetailsBean")
public class TournamentDetailsBean implements Serializable {

    @Inject
    private EsportService esportService;

    private String tournamentName;
    private String newTournamentName;
    private String newStartDate;
    private String newEndDate;
    private String newLocation;
    private double newCashPrize;
    private Long newBankId;
    
    private Long selectedGameId;
    private List<Game> allGames;
    private List<Long> selectedTeamIds;
    private List<EsportTeam> allTeams;

    private List<Object[]> tournamentDetails;

    private Tournament tournament;

    @PostConstruct
    public void init() {
        tournamentDetails = esportService.getAllTournaments();
        allGames = esportService.getAllGames();
        allTeams = esportService.getAllTeams();
        selectedTeamIds = new ArrayList<>();
        
        if (tournamentDetails == null || tournamentDetails.isEmpty()) {
            System.out.println("No tournaments found.");
            tournamentDetails = List.of();
        } else {
            System.out.println("Tournaments loaded: " + tournamentDetails.size());
            for (Object[] tournament : tournamentDetails) {
                System.out.println("Tournament: " + Arrays.toString(tournament));
            }
        }
    }

    public void searchTournament() {
        if (tournamentName != null && !tournamentName.trim().isEmpty()) {
            tournamentDetails = esportService.findTournamentByName(tournamentName);
            if (tournamentDetails == null || tournamentDetails.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "No tournaments found with the given name.", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Please enter a tournament name to search.", null));
        }
    }

    public void resetSearch() {
        tournamentDetails = esportService.getAllTournaments();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Search reset successfully.", null));
    }

    public void createTournament() {
        try {
            esportService.addTournament(newTournamentName, newStartDate, newEndDate, newLocation, newCashPrize, newBankId, selectedGameId, selectedTeamIds);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Tournament created successfully!", null));
            resetForm();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create tournament: " + e.getMessage(), null));
            e.printStackTrace();
        }
    }

    private void resetForm() {
        newTournamentName = "";
        newStartDate = "";
        newEndDate = "";
        newLocation = "";
        newCashPrize = 0;
        newBankId = 0L;
        selectedTeamIds = new ArrayList<>();
    }
    
    public String loadTournamentDetails(Long tournamentId) {
        tournament = esportService.getTournamentById(tournamentId);
        if (tournament != null) {
            return "tournamentDetails.xhtml?faces-redirect=true";
        }
        return null;
    }

    public void loadTournamentDetailsFromRequest() {
        String tournamentIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("tournamentId");
        if (tournamentIdParam != null) {
            try {
                Long tournamentId = Long.valueOf(tournamentIdParam);
                tournament = esportService.getTournamentById(tournamentId);
            } catch (NumberFormatException e) {
                System.err.println("Invalid tournamentId: " + tournamentIdParam);
            }
        }
    }
    
  /*  public void addTeamsToTournament(Long tournamentId) {
    	try {
    		esportService.addTeams(tournamentId, selectedTeamIds);
    	} catch (Exception e) {
    		FacesContext.getCurrentInstance().addMessage(null, 
    				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to add teams: " + e.getMessage(), null));
    		e.printStackTrace();
    	}
    }	*/
    
    // Getters and Setters

    public List<Object[]> getTournamentDetails() {
        return tournamentDetails;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getNewTournamentName() {
        return newTournamentName;
    }

    public void setNewTournamentName(String newTournamentName) {
        this.newTournamentName = newTournamentName;
    }

    public String getNewStartDate() {
        return newStartDate;
    }

    public void setNewStartDate(String newStartDate) {
        this.newStartDate = newStartDate;
    }

    public String getNewEndDate() {
        return newEndDate;
    }

    public void setNewEndDate(String newEndDate) {
        this.newEndDate = newEndDate;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    public List<String> getTeamNames() {
        if (tournament != null && tournament.getTeams() != null) {
            return tournament.getTeams().stream()
                    .map(EsportTeam::getTeamName)
                    .toList();
        }
        return List.of(); // Retourne une liste vide si aucune équipe n'est trouvée
    }

	public double getNewCashPrize() {
		return newCashPrize;
	}

	public void setNewCashPrize(double newCashPrize) {
		this.newCashPrize = newCashPrize;
	}

	public Long getNewBankId() {
		return newBankId;
	}

	public void setNewBankId(Long newBankId) {
		this.newBankId = newBankId;
	}

	public Long getSelectedGameId() {
		return selectedGameId;
	}

	public void setSelectedGameId(Long selectedGameId) {
		this.selectedGameId = selectedGameId;
	}

	public List<Game> getAllGames() {
		return allGames;
	}

	public void setAllGames(List<Game> allGames) {
		this.allGames = allGames;
	}

	public List<Long> getSelectedTeamIds() {
		return selectedTeamIds;
	}

	public void setSelectedTeamIds(List<Long> selectedTeamIds) {
		this.selectedTeamIds = selectedTeamIds;
	}

	public List<EsportTeam> getAllTeams() {
		return allTeams;
	}

	public void setAllTeams(List<EsportTeam> allTeams) {
		this.allTeams = allTeams;
	}
}