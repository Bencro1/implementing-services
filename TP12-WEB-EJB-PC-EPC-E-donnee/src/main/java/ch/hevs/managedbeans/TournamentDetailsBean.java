package ch.hevs.managedbeans;

import ch.hevs.bankservice.EsportService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("TournamentDetailsBean")
public class TournamentDetailsBean implements Serializable {

    @Inject
    private EsportService esportService;
    
    private String tournamentName;

    private List<Object[]> tournamentDetails;

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

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
    
    
}