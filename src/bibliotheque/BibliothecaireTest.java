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
	private Auteur auteur1;
	private Livre livre1;
	private Auteur auteur2;
	private Livre livre2;
	private LocalDate datePassee;
	private LocalDate dateFuture;
	private Emprunteur emprunteur1;
	private Emprunteur emprunteur2;

	@BeforeEach
	void setUp() throws Exception {
		
		HashMap<Auteur, ArrayList<Livre>> catalogue = new HashMap<>();		
		bibliothecaire = new Bibliothecaire(catalogue);
		auteur1 = new Auteur("Romain Gary");
		livre1 = new Livre(auteur1, "La Vie devant soi");
		auteur2 = new Auteur("Michel Houellebecq");
		livre2 = new Livre(auteur2, "Serotonine");
		datePassee = LocalDate.of(2020, Month.SEPTEMBER, 7);
		dateFuture = LocalDate.of(2021, Month.SEPTEMBER, 7);
		emprunteur1 = new Emprunteur("Poder", "Solveig");
		emprunteur2 = new Emprunteur("Caron", "Juliette");
		
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
		
		//WHEN
		bibliothecaire.ajouterLivre(livre1);
		
		//THEN
		assertNotNull(bibliothecaire.getCatalogue().get(auteur1));
		assertTrue(bibliothecaire.getCatalogue().get(auteur1).contains(livre1));
	}

	@Test
	void testEnleverLivre() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		
		//WHEN
		bibliothecaire.enleverLivre(livre1);
		
		//THEN
		ArrayList<Livre> listeLivres = bibliothecaire.getCatalogue().get(auteur1);
		assertFalse(listeLivres.contains(livre1));
		assertEquals(bibliothecaire.getCatalogue().get(livre1.getAuteur()).size(), 0);
	}

	@Test
	void testPreterUnLivre() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		
		//WHEN
		bibliothecaire.preterLivre(livre1, emprunteur1, datePassee);
		bibliothecaire.preterLivre(livre1, emprunteur2, dateFuture);
		
		//THEN
		assertNotNull(emprunteur1.getLivresEmpruntes().get(livre1));
		assertTrue(emprunteur1.getLivresEmpruntes().get(livre1).equals(datePassee));
		assertNotNull(bibliothecaire.getEmprunteurs());
		assertTrue(bibliothecaire.getEmprunteurs().contains(emprunteur1));
		assertTrue(bibliothecaire.getEmprunteurs().contains(emprunteur2));
	}
	
	@Test
	void testListerEmprunteursEnRetard() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		Emprunteur emprunteur=new Emprunteur("Poder", "Solveig");
		bibliothecaire.preterLivre(livre1, emprunteur, dateFuture);
		
		//WHEN
		HashMap<Emprunteur, ArrayList<Livre>> retards = bibliothecaire.ListerEmprunteursEnRetard();
		
		//THEN
		assertTrue(retards.isEmpty());
	}
	
	@Test
	void testListerEmprunteursEnRetard2() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		bibliothecaire.ajouterLivre(livre2);
		bibliothecaire.preterLivre(livre1, emprunteur1, dateFuture);
		bibliothecaire.preterLivre(livre2, emprunteur1, datePassee);
		bibliothecaire.preterLivre(livre1, emprunteur2, datePassee);
		
		//WHEN
		HashMap<Emprunteur, ArrayList<Livre>> retards = bibliothecaire.ListerEmprunteursEnRetard();
		
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
		bibliothecaire.ajouterLivre(livre1);		
		bibliothecaire.ajouterLivre(livre2);
		bibliothecaire.preterLivre(livre1, emprunteur1, dateFuture);
		bibliothecaire.preterLivre(livre1, emprunteur2, datePassee);
		
		//WHEN
		bibliothecaire.RelancerEmprunteurEnRetard();	

		//THEN
		assertFalse(emprunteur2.getMessagerie().isEmpty());
		assertTrue(emprunteur2.getMessagerie().containsKey("retard"));
	}	
	
	@Test
	void testListerPersonnesAyantEmpruntesUnLivre() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		bibliothecaire.preterLivre(livre1, emprunteur1, dateFuture);
		bibliothecaire.preterLivre(livre1, emprunteur2, datePassee);
		
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
		bibliothecaire.ajouterLivre(livre1);
		bibliothecaire.ajouterLivre(livre2);
		EtudiantEmprunteur etudiant=new EtudiantEmprunteur("Poder", "Solveig", 21903145);
		bibliothecaire.preterLivre(livre1, etudiant, dateFuture);
		bibliothecaire.preterLivre(livre2, emprunteur1, dateFuture);
		
		//WHEN
		ArrayList<Livre> livresEtudiants = bibliothecaire.listerLivresEmpruntesParEtudiant();
		
		//THEN
		assertFalse(livresEtudiants.isEmpty());
		assertTrue(livresEtudiants.contains(livre1));
		assertFalse(livresEtudiants.contains(livre2));
	}
	
	@Test
	void testListerLivresEmpruntes() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		bibliothecaire.ajouterLivre(livre2);
		EtudiantEmprunteur etudiant=new EtudiantEmprunteur("Poder", "Solveig", 21903145);
		bibliothecaire.preterLivre(livre1, etudiant, dateFuture);
		bibliothecaire.preterLivre(livre2, emprunteur1, datePassee);
		
		//WHEN
		ArrayList<Livre> livresEtudiants = bibliothecaire.listerLivresEmpruntes();
		
		//THEN
		assertFalse(livresEtudiants.isEmpty());
		assertTrue(livresEtudiants.contains(livre1));
		assertTrue(livresEtudiants.contains(livre2));
	}
	
	@Test
	void testListerLivresAnglais() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		Auteur auteurEn=new Auteur("Neil Gaiman");
		LivreAnglais livreEn=new LivreAnglais(auteurEn, "American Gods", "Michel Pagel");
		bibliothecaire.ajouterLivre(livreEn);
		
		//WHEN
		ArrayList<Livre> livresAnglais = bibliothecaire.listerLivresAnglais();
		
		//THEN
		assertFalse(livresAnglais.isEmpty());
		assertTrue(livresAnglais.contains(livreEn));
		assertFalse(livresAnglais.contains(livre1));
	}
	
	@Test
	void testListerNbLivresEmpruntesPourUnAuteur() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);
		bibliothecaire.ajouterLivre(livre2);
		bibliothecaire.preterLivre(livre1, emprunteur1, datePassee);
		bibliothecaire.preterLivre(livre2, emprunteur2, dateFuture);
		
		//WHEN
		bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary");
		
		//THEN
		assertNotNull(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary"));
		assertTrue(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary").contains(livre1));
		assertFalse(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary").contains(livre2));
		assertTrue(bibliothecaire.ListerNbLivresEmpruntesPourUnAuteur("Romain Gary").size() == 1);
	}
	
	@Test
	void testTrouverLivreSurUnTheme() {
		//GIVEN
		Auteur auteur=new Auteur("Camille Rey");
		Livre livre=new Livre(auteur, "Mon super livre");
		bibliothecaire.ajouterLivre(livre);
		Auteur auteur2=new Auteur("Juliette Caron");
		Livre livre2=new Livre(auteur2, "Aladdin court");
		bibliothecaire.ajouterLivre(livre2);
		Livre livre3=new Livre(auteur2, "Le petit lapin");
		bibliothecaire.ajouterLivre(livre3);
		Livre livre4=new Livre(auteur, "La course folle");
		bibliothecaire.ajouterLivre(livre4);
		livre.setTheme("biographie");
		livre2.setTheme("aventure");
		livre3.setTheme("enfance");
		livre4.setTheme("aventure");
		
		//WHEN
		Livre result = bibliothecaire.TrouverLivreSurUnTheme("aventure");
		
		//THEN
		assertNotNull(result);
		assertTrue(result.getTheme() == "aventure");
	}
	
	@Test
	void testEnvoyerAmendeRetardaire() {
		//GIVEN
		bibliothecaire.ajouterLivre(livre1);		
		bibliothecaire.ajouterLivre(livre2);
		bibliothecaire.preterLivre(livre1, emprunteur1, dateFuture);
		bibliothecaire.preterLivre(livre1, emprunteur2, datePassee);
		
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
		bibliothecaire.ajouterLivre(livre1);		
		bibliothecaire.preterLivre(livre1, emprunteur1, datePassee);
		bibliothecaire.EnvoyerAmendeRetardaire();

		//WHEN
		bibliothecaire.EncaisserAmendeRetardaire(emprunteur1, 2.0);
		
		//THEN
		assertTrue(emprunteur1.getSolde()==0);
		assertTrue(bibliothecaire.getCaisse() == 2);
	}

}
