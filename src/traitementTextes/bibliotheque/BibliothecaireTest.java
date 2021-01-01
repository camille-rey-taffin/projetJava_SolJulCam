package traitementTextes.bibliotheque;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BibliothecaireTest {
	
	private Bibliothecaire bibliothecaire;

	@BeforeEach
	void setUp() throws Exception {
		
		HashMap<Auteur, ArrayList<Livre>> catalogue = new HashMap<>();		
		bibliothecaire=new Bibliothecaire(catalogue);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testAfficherOeuvresAuteur() {
		
		//GIVEN
		Auteur auteur=new Auteur("nomAuteur");
		ArrayList<Livre> livres=new ArrayList<>();
		String titre = "un titre presomptueux";
		Livre premierLivre=new Livre(auteur, titre);
		livres.add(premierLivre);
		bibliothecaire.getCatalogue().put(auteur, livres);
		
		//WHEN
		String listeOeuvres = bibliothecaire.listerOeuvresAuteur(auteur);
		
		//THEN
		assertNotNull(listeOeuvres);
		assertTrue(listeOeuvres.contains(titre));
		System.out.println(listeOeuvres);
	}
	
	
	@Test
	void testAjouterLivre() {
		//GIVEN
		Auteur auteur=new Auteur("un auteur");
		String titre = "un titre";
		Livre livre=new Livre(auteur, titre);
		
		//WHEN
		bibliothecaire.ajouterLivre(livre);
		
		//THEN
		assertNotNull(livre.getAuteur());
		assertNotNull(bibliothecaire.getCatalogue().get(auteur));
		assertTrue(bibliothecaire.getCatalogue().get(auteur).contains(livre));
	}

	@Test
	void testEnleverLivre() {
		//GIVEN
		Auteur auteur = new Auteur("nomAuteur");
		String titre = "Un titre";
		Livre nouveauLivre = new Livre(auteur, titre);
		bibliothecaire.ajouterLivre(nouveauLivre);
		
		//WHEN
		bibliothecaire.enleverLivre(nouveauLivre);
		
		//THEN
		ArrayList<Livre> listeLivres = bibliothecaire.getCatalogue().get(auteur);
		assertFalse(listeLivres.contains(nouveauLivre));
		assertEquals(bibliothecaire.getCatalogue().get(nouveauLivre.getAuteur()).size(), 0);
	}


	
	
	/*
	 * 
	 * Partie concernee par le devoir
	 * Voici le d�compte des notes:
	 * 1pts par test OK==>10pts
	 * 2 pour pour la mis en place de l'heritage
	 * 1pt pour la javadoc
	 * 1pt pour le polymorphisme et la surchage
	 * 1pt pour la reutilisation de l'existant
	 * 1pt pour la gestion des exceptions
	 * 1pt pour la creation d'exceptions
	 * 1pt: utilisation de l'encapsulation
	 * 1pt: utilisation de git
	 * 1pt: lisibilit� du code
	 * -1pt: m�thode avec plus de 3 arguments
	 * -1pt: classe de plus de 200 lignes
	 * -1pt: plus de 2 boucles for
	 * -1pt: trop d'utilisation de if
	 * 
	 */
	
	@Test
	void testPreterUnLivre() {
		//GIVEN
		Auteur auteur=new Auteur("Romain");
		String titre = "La Vie devant soi";
		Livre livre=new Livre(auteur, titre);
		ArrayList<Livre> livres=new ArrayList<>();
		livres.add(livre);
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig", "�tudiante");
		LocalDate date_rendu = LocalDate.of(2020, Month.SEPTEMBER, 7); 
		
		//WHEN
		bibliothecaire.preterLivre(livre, emprunteur, date_rendu);
		
		//THEN
		assertNotNull(emprunteur.getLivresEmpruntes());
		assertNotNull(emprunteur.getLivresEmpruntes().get(livre));
		assertTrue(emprunteur.getLivresEmpruntes().get(livre).equals(date_rendu));
		assertNotNull(bibliothecaire.getEmprunteurs());
		assertTrue(bibliothecaire.getEmprunteurs().contains(emprunteur));
	}
	
	@Test
	void testRelancerEmprunteurEnRetard() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		String titre = "La Vie devant soi";
		Livre livre=new Livre(auteur, titre);
		ArrayList<Livre> livres=new ArrayList<>();
		livres.add(livre);
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig", "�tudiante");
		LocalDate date_rendu = LocalDate.of(2020, Month.SEPTEMBER, 7);
		bibliothecaire.preterLivre(livre, emprunteur, date_rendu);
		
		//WHEN
		HashMap<Emprunteur, ArrayList<Livre>> retards = bibliothecaire.RelancerEmprunteurEnRetard(bibliothecaire.getEmprunteurs());
		
		//THEN
		assertNotNull(retards);
		assertTrue(retards.get(emprunteur).contains(livre));
	}
	
	@Test
	void testListerPersonnesAyantEmpruntesUnLivre() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		String titre = "La Vie devant soi";
		Livre livre=new Livre(auteur, titre);
		ArrayList<Livre> livres=new ArrayList<>();
		livres.add(livre);
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig", "�tudiante");
		LocalDate date_rendu = LocalDate.of(2020, Month.SEPTEMBER, 7);
		bibliothecaire.preterLivre(livre, emprunteur, date_rendu);
		
		//WHEN
		bibliothecaire.listerPersonnesAyantEmprunteUnLivre();
		
		//THEN
		assertNotNull(bibliothecaire.listerPersonnesAyantEmprunteUnLivre());
		assertTrue(bibliothecaire.listerPersonnesAyantEmprunteUnLivre().contains(emprunteur));
	}
	
	@Test
	void testListerLivresEmpruntesParEtudiant() {
		fail("Not yet implemented");
	}
	
	@Test
	void testListerLivresEmpruntes() {
		fail("Not yet implemented");
	}
	
	@Test
	void testListerLivresAnglais() {
		fail("Not yet implemented");
	}
	
	@Test
	void testListerNbLivresEmpruntesPourUnAuteur() {
		fail("Not yet implemented");
	}
	
	@Test
	void testTrouverLivreSurUnTheme() {
		fail("Not yet implemented");
	}
	
	@Test
	void testEnvoyerAmendeRetardaire() {
		fail("Not yet implemented");
	}
	
	@Test
	void testEncaisserAmendeRetardaire() {
		fail("Not yet implemented");
	}

}
