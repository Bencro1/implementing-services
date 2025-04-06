package ch.hevs.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.Entitys.Tournament;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@ManagedBean
@ViewScoped
@Named("tournamentBean")
public class TournamentBean implements Serializable {
	private List<Tournament> tournaments;
	private List<String> tournamentNames;
//	private TournamentService tournamentService;
	
@PostConstruct
public void initialize() {
    try {
        // Utilisez JNDI pour injecter une référence à l'EJB Tournament
        InitialContext ctx = new InitialContext();
        // Exemple : tournamentService = (TournamentService) ctx.lookup("java:global/...");

        // Chargez les tournois
        // Exemple : tournaments = tournamentService.getTournaments();
    } catch (NamingException e) {
        e.printStackTrace();
        // Gérez l'exception ici
    }
}
}