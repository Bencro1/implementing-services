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
	public void initialize() throws NamingException {
		// Use JNDI to inject reference to tournament EJB
		InitialContext ctx = new InitialContext();
	//	tournamentService = (Tournament) ctx.lookup(null);
		
		// get tournaments
	//	List<Tournament> tournamentList = tournamentService.getTournaments();
	}
}