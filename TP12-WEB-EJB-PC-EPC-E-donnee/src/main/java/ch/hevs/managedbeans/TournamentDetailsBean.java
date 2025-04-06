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
    private String newTournamentName;
    private String newStartDate;
    private String newEndDate;
    private String newLocation;
    private double newCashPrize;
    private long newBankId;

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
    
    public void resetSearch() {
    	tournamentDetails = esportService.getAllTournaments();
    }
    
    public String createTournament() {
    	esportService.addTournament(newTournamentName, newStartDate, newEndDate, newLocation, newCashPrize, newBankId);	
    	
    	newTournamentName = "";
    	newStartDate = "";
    	newEndDate = "";
    	newLocation = "";
    	newCashPrize = 0;
    	newBankId = 0;
    	
    	return "confirmationTournamentCreation";
    }
    
    // Getter and setter

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

	public double getNewCashPrize() {
		return newCashPrize;
	}

	public void setNewCashPrize(double newCashPrize) {
		this.newCashPrize = newCashPrize;
	}

	public long getNewBankId() {
		return newBankId;
	}

	public void setNewBankId(long newBankId) {
		this.newBankId = newBankId;
	}
    
}