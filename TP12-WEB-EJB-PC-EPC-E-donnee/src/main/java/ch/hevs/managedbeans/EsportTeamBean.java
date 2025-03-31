package ch.hevs.managedbeans;

import ch.hevs.businessobject.EsportTeam;
import ch.hevs.service.EsportService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;

@ManagedBean
public class EsportTeamBean implements Serializable {
    @Inject
    private EsportService esportService;

    private EsportTeam team = new EsportTeam();
    private List<EsportTeam> teams;

    @PostConstruct
    public void init() {
        teams = esportService.getAllTeams();
    }

    public void addTeam() {
        esportService.addTeam(team);
        team = new EsportTeam(); // Reset the form
        teams = esportService.getAllTeams(); // Refresh the list
    }

    public List<EsportTeam> getTeams() {
        return teams;
    }

    public EsportTeam getTeam() {
        return team;
    }

    public void setTeam(EsportTeam team) {
        this.team = team;
    }
}
