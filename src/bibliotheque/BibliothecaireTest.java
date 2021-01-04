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
		//System.out.println(listeOeuvres);
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
		Emprunteur emprunteur1=new Emprunteur("Poder", "Solveig");
		Emprunteur emprunteur2=new Emprunteur("Caron", "Juliette");
		LocalDate date_rendu = LocalDate.of(2020, Month.SEPTEMBER, 7); 
		
		//WHEN
		bibliothecaire.preterLivre(livre, emprunteur1, date_rendu);
		bibliothecaire.preterLivre(livre, emprunteur2, date_rendu);
		
		//THEN
		assertNotNull(emprunteur1.getLivresEmpruntes().get(livre));
		assertTrue(emprunteur1.getLivresEmpruntes().get(livre).equals(date_rendu));
		assertNotNull(bibliothecaire.getEmprunteurs());
		assertTrue(bibliothecaire.getEmprunteurs().contains(emprunteur1));
		assertTrue(bibliothecaire.getEmprunteurs().contains(emprunteur2));
	}
	
	@Test
	void testListerEmprunteursEnRetard() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig");
		LocalDate date_rendu = LocalDate.of(2021, Month.SEPTEMBER, 7);
		bibliothecaire.preterLivre(livre, emprunteur, date_rendu);
		
		//WHEN
		HashMap<Emprunteur, ArrayList<Livre>> retards = bibliothecaire.ListerEmprunteursEnRetard(bibliothecaire.getEmprunteurs());
		
		//THEN
		assertTrue(retards.isEmpty());
	}
	
	@Test
	void testListerEmprunteursEnRetard2() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre1=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre1);		
		Auteur auteur2=new Auteur("Camille Rey");
		Livre livre2=new Livre(auteur2, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre2);
		Emprunteur emprunteur1=new Emprunteur("Poder", "Solveig");
		Emprunteur emprunteur2=new Emprunteur("Caron", "Juliette");
		bibliothecaire.preterLivre(livre1, emprunteur1, LocalDate.of(2021, Month.SEPTEMBER, 7));
		bibliothecaire.preterLivre(livre2, emprunteur1, LocalDate.of(2020, Month.SEPTEMBER, 7));
		bibliothecaire.preterLivre(livre1, emprunteur2, LocalDate.of(2020, Month.SEPTEMBER, 7));
		
		//WHEN
		HashMap<Emprunteur, ArrayList<Livre>> retards = bibliothecaire.ListerEmprunteursEnRetard(bibliothecaire.getEmprunteurs());
		
		//THEN
		assertFalse(retards.isEmpty());
		assertNotNull(retards.get(emprunteur1));
		assertNotNull(retards.get(emprunteur2));
		assertFalse(retards.get(emprunteur1).contains(livre1));
		assertTrue(retards.get(emprunteur1).contains(livre2));
		assertTrue(retards.get(emprunteur2).contains(livre1));
	}
	
	@Test
	void testRelancerEmprunteurEnRetard() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre1=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre1);		
		Auteur auteur2=new Auteur("Camille Rey");
		Livre livre2=new Livre(auteur2, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre2);
		Emprunteur emprunteur1=new Emprunteur("Poder", "Solveig");
		Emprunteur emprunteur2=new Emprunteur("Caron", "Juliette");
		bibliothecaire.preterLivre(livre1, emprunteur1, LocalDate.of(2021, Month.SEPTEMBER, 7));
		bibliothecaire.preterLivre(livre1, emprunteur2, LocalDate.of(2020, Month.SEPTEMBER, 7));
		
		//WHEN
		bibliothecaire.RelancerEmprunteurEnRetard(bibliothecaire.getEmprunteurs());	

		//THEN
		assertFalse(emprunteur2.getMessagerie().isEmpty());
		assertTrue(emprunteur2.getMessagerie().containsKey("retard"));
	}	
	
	@Test
	void testListerPersonnesAyantEmpruntesUnLivre() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre);
		Emprunteur emprunteur1=new Emprunteur("Poder", "Solveig");
		Emprunteur emprunteur2=new Emprunteur("Caron", "Juliette");
		bibliothecaire.preterLivre(livre, emprunteur1, LocalDate.of(2021, Month.SEPTEMBER, 7));
		bibliothecaire.preterLivre(livre, emprunteur2, LocalDate.of(2021, Month.SEPTEMBER, 7));
		
		//WHEN
		ArrayList<Emprunteur> emprunteurs = bibliothecaire.listerPersonnesAyantEmprunteUnLivre();
		
		//THEN
		assertFalse(emprunteurs.isEmpty());
		assertTrue(emprunteurs.contains(emprunteur1));
		assertTrue(emprunteurs.contains(emprunteur2));
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
		ArrayList<Livre> livresEtudiants = bibliothecaire.listerLivresEmpruntesParEtudiant();
		
		//THEN
		assertFalse(livresEtudiants.isEmpty());
		assertTrue(livresEtudiants.contains(livre));
		assertFalse(livresEtudiants.contains(livre2));
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
		ArrayList<Livre> livresEtudiants = bibliothecaire.listerLivresEmpruntes();
		
		//THEN
		assertFalse(livresEtudiants.isEmpty());
		assertTrue(livresEtudiants.contains(livre));
		assertTrue(livresEtudiants.contains(livre2));
	}
	
	@Test
	void testListerLivresAnglais() {
		//GIVEN
		Auteur auteurfr=new Auteur("Romain Gary");
		Livre livrefr=new Livre(auteurfr, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livrefr);
		Auteur auteuren=new Auteur("Neil Gaiman");
		LivreAnglais livreen=new LivreAnglais(auteuren, "American Gods", "Michel Pagel");
		bibliothecaire.ajouterLivre(livreen);
		
		//WHEN
		ArrayList<Livre> livresAnglais = bibliothecaire.listerLivresAnglais();
		
		//THEN
		assertFalse(livresAnglais.isEmpty());
		assertTrue(livresAnglais.contains(livreen));
		assertFalse(livresAnglais.contains(livrefr));
	}
	
	@Test
	void testListerNbLivresEmpruntesPourUnAuteur() {
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
		bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary");
		
		//THEN
		assertNotNull(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary"));
		assertTrue(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary").contains(livre));
		assertFalse(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary").contains(livre2));
		assertTrue(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary").size() == 1);
	}
	
	@Test
	void testTrouverLivreSurUnTheme() {
		fail("Not yet implemented");
	}
	
	@Test
	void testEnvoyerAmendeRetardaire() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre1=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre1);		
		Auteur auteur2=new Auteur("Camille Rey");
		Livre livre2=new Livre(auteur2, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre2);
		Emprunteur emprunteur1=new Emprunteur("Poder", "Solveig");
		Emprunteur emprunteur2=new Emprunteur("Caron", "Juliette");
		bibliothecaire.preterLivre(livre1, emprunteur1, LocalDate.of(2021, Month.SEPTEMBER, 7));
		bibliothecaire.preterLivre(livre1, emprunteur2, LocalDate.of(2020, Month.SEPTEMBER, 7));
		
		//WHEN
		bibliothecaire.EnvoyerAmendeRetardaire();
		
		//THEN
		assertNotNull(emprunteur1.getSolde());
		assertTrue(emprunteur2.getSolde()>0);
		assertTrue(emprunteur1.getSolde()==0);
	}
	
	@Test
	void testEncaisserAmendeRetardaire() {
		//GIVEN
		Auteur auteur=new Auteur("Romain Gary");
		Livre livre1=new Livre(auteur, "La Vie devant soi");
		bibliothecaire.ajouterLivre(livre1);		
		Emprunteur emprunteur2=new Emprunteur("Caron", "Juliette");
		bibliothecaire.preterLivre(livre1, emprunteur2, LocalDate.of(2020, Month.SEPTEMBER, 7));
		bibliothecaire.EnvoyerAmendeRetardaire();

		//WHEN
		bibliothecaire.EncaisserAmendeRetardaire(emprunteur2, 2.0);
		
		//THEN
		assertTrue(emprunteur2.getSolde()==0);
		assertTrue(bibliothecaire.getCaisse() == 2);
	}

}
