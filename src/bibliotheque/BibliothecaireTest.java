package bibliotheque;

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

	@Test
	void testPreterUnLivre() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig");
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
		Livre livre=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig");
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
		Livre livre=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig");
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
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre);
		Etudiant etudiant=new Etudiant("Poder", "Solveig", 21903145);
		LocalDate date_rendu = LocalDate.of(2020, Month.SEPTEMBER, 7);
		Auteur auteur2=new Auteur("Elena Ferrante");
		Livre livre2=new Livre(auteur2, "L'Amie prodigieuse");
		bibliothecaire.ajouterLivre(livre2);
		Emprunteur emprunteur=new Emprunteur("Garnier", "Johanna");
		bibliothecaire.preterLivre(livre, etudiant, date_rendu);
		bibliothecaire.preterLivre(livre2, emprunteur, date_rendu);
		
		//WHEN
		bibliothecaire.listerLivresEmpruntesParEtudiant();
		
		//THEN
		assertNotNull(bibliothecaire.listerLivresEmpruntesParEtudiant());
		assertTrue(bibliothecaire.listerLivresEmpruntesParEtudiant().contains(livre));
		assertFalse(bibliothecaire.listerLivresEmpruntesParEtudiant().contains(livre2));
	}
	
	@Test
	void testListerLivresEmpruntes() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre);
		Etudiant etudiant=new Etudiant("Poder", "Solveig", 21903145);
		LocalDate date_rendu = LocalDate.of(2020, Month.SEPTEMBER, 7);
		Auteur auteur2=new Auteur("Elena Ferrante");
		Livre livre2=new Livre(auteur2, "L'Amie prodigieuse");
		bibliothecaire.ajouterLivre(livre2);
		Emprunteur emprunteur=new Emprunteur("Garnier", "Johanna");
		bibliothecaire.preterLivre(livre, etudiant, date_rendu);
		bibliothecaire.preterLivre(livre2, emprunteur, date_rendu);
		
		//WHEN
		bibliothecaire.listerLivresEmpruntes();
		
		//THEN
		assertNotNull(bibliothecaire.listerLivresEmpruntes());
		assertTrue(bibliothecaire.listerLivresEmpruntes().contains(livre));
		assertTrue(bibliothecaire.listerLivresEmpruntes().contains(livre2));
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
