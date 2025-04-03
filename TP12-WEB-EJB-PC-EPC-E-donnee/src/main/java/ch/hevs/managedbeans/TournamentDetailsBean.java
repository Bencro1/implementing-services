package ch.hevs.managedbeans;

import ch.hevs.bankservice.EsportService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;

@ManagedBean
public class TournamentDetailsBean implements Serializable {

    @Inject
    private EsportService esportService;

    private List<Object[]> tournamentDetails;

    @PostConstruct
    public void init() {
        tournamentDetails = esportService.getTournamentsWithDetails();
    }

    public List<Object[]> getTournamentDetails() {
        return tournamentDetails;
    }
}