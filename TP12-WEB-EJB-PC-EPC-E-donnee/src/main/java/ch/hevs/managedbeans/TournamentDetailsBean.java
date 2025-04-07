package ch.hevs.managedbeans;

import ch.hevs.bankservice.EsportService;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Tournament;
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

    private List<Object[]> tournamentDetails;

    private Tournament tournament;

    @PostConstruct
    public void init() {
        tournamentDetails = esportService.getAllTournaments();
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
            esportService.addTournament(newTournamentName, newStartDate, newEndDate, newLocation, newCashPrize, newBankId);
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
}