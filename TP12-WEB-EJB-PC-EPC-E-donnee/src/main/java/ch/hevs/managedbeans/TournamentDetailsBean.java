package ch.hevs.managedbeans;

import ch.hevs.bankservice.EsportService;
import ch.hevs.Entitys.EsportTeam;
import ch.hevs.Entitys.Tournament;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
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

    private List<Object[]> tournamentDetails;

    // Ajout de la variable tournament
    private Tournament tournament;

    @PostConstruct
    public void init() {
        tournamentDetails = esportService.getAllTournaments();
        if (tournamentDetails == null) {
            tournamentDetails = List.of();
        }
    }
    
    public void searchTournament() {
        if (tournamentName != null && !tournamentName.trim().isEmpty()) {
            tournamentDetails = esportService.findTournamentByName(tournamentName);
        }
    }

    public List<Object[]> getTournamentDetails() {
        return tournamentDetails;
    }
    
    public void resetSearch() {
        tournamentDetails = esportService.getAllTournaments();
    }
    
    // Getter et setter pour tournament
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    // Getter et setter existants
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
				System.out.println("Received tournamentId: " + tournamentId);
				tournament = esportService.getTournamentById(tournamentId);
				if (tournament == null) {
					System.err.println("No tournament found for ID: " + tournamentId);
				} else {
					System.out.println("Tournament loaded: " + tournament.getTournamentName());
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid tournamentId: " + tournamentIdParam);
			}
		} else {
			System.err.println("tournamentId parameter is missing in the request.");
		}
	}
}