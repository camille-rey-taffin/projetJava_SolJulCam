package bibliotheque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.time.LocalDate;

/**
 * Classe repr�sentant virtuellement une biblioth�que
 */
public class Bibliothecaire {

	private HashMap<Auteur, ArrayList<Livre>> catalogue;
	private ArrayList<Emprunteur> emprunteurs;
	private double caisse;
	
	 /**
	  * Constructeur de la classe Bibliothecaire
	  * @param catalogue un objet de type HashMap associant un auteur
	  * � la liste de ses livres pr�sents dans la biblioth�que
	  */
	public Bibliothecaire(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue=catalogue;
		this.setCaisse(0);
		this.emprunteurs = new ArrayList<Emprunteur>();
	}
	
	 /**
	  * M�thode pour ajouter un livre au catalogue
	  * @param nouveauLivre un objet de type Livre correspondant au livre � ajouter
	  */
	public void ajouterLivre(Livre nouveauLivre) {
		if (Objects.nonNull(getCatalogue().get(nouveauLivre.getAuteur()))) {
			getCatalogue().get(nouveauLivre.getAuteur()).add(nouveauLivre);
		} else {
			ArrayList<Livre> livres = new ArrayList<>();
			livres.add(nouveauLivre);
			getCatalogue().put(nouveauLivre.getAuteur(), livres);
		}
	}

	 /**
	  * M�thode pour supprimer un livre du catalogue
	  * @param ancienLivre un objet de type Livre correspondant au livre � supprimer
	  */
	public void enleverLivre(Livre ancienLivre) {
		if ((Objects.nonNull(getCatalogue().get(ancienLivre.getAuteur()))) ) {
			getCatalogue().get(ancienLivre.getAuteur()).remove(ancienLivre);
		}
	}
	
	 /**
	  * M�thode pour lister tous les livres du catalogue
	  * @return listeLivres une liste d'objets de type Livre 
	  */
	public ArrayList<Livre> listerLivres() {
		ArrayList<Livre> listeLivres = new ArrayList<Livre>();
		for (ArrayList<Livre> livres : getCatalogue().values()) {
			for (Livre livre : livres) {
				listeLivres.add(livre);
			}
		}
		return listeLivres;
	}
	
	 /**
	  * M�thode pour lister tous les livres d'un auteur
	  * Surcharge de la m�thode listerLivres
	  * @param auteur un objet de type Auteur correspondant � l'auteur recherch� 
	  * @return une liste d'objets de type Livre 
	  */
	public ArrayList<Livre> listerLivres(Auteur auteur) {
		return getCatalogue().get(auteur);
	}
	
	 /**
	  * M�thode pour lister tous les livres d'un th�me sp�cifique
	  * Surcharge de la m�thode listerLivres
	  * @param theme une cha�ne de car. correspondant au th�me recherch�
	  * @return listeLivresTheme une liste d'objets de type Livre
	  */
	public ArrayList<Livre> listerLivres(String theme) {
		ArrayList<Livre> listeLivresTheme = new ArrayList<Livre>();
		for (Livre livre : listerLivres()) {
			if (livre.getTheme() == theme) {
				listeLivresTheme.add(livre);
			}
		}
		return listeLivresTheme;
	}
	
	 /**
	  * M�thode pour lister tous les livres du catalogue appartenant �
	  * une classe sp�cifique d�riv�e de la classe Livre
	  * (Permet notamment de lister tous les livres en anglais = les livres
	  * de la classe LivreAnglais, qui h�rite de la classe Livre)
	  * Surcharge de la m�thode listerLivres
	  * @param cls une classe d�riv�e de la classe Livre  
	  * @return listeLivresClasse une liste d'objets de type Livre
	  */
	public ArrayList<Livre> listerLivres(Class<? extends Livre> cls) {
		ArrayList<Livre> listeLivresClasse = new ArrayList<Livre>();
		for (Livre livre : listerLivres()) {
			if (cls.isInstance(livre)) {
				listeLivresClasse.add(livre);
			}
		}
		return listeLivresClasse;
	}
	
	 /**
	  * M�thode pour pr�ter un livre � un individu
	  * Ajoute � l'inventaire d'un emprunteur un livre associ� � une date de rendu 
	  * @param livre un objet de type Livre correspondant au livre pr�t�
	  * @param emprunteur objet de type Emprunteur correspondant � l'individu qui emprunte le livre 
	  * @param date la date � laquelle le livre doit �tre rendu
	  */
	public void preterLivre(Livre livre, Emprunteur emprunteur, LocalDate date) {
		emprunteur.addEmprunt(livre, date);
		if (!getEmprunteurs().contains(emprunteur)) {
			getEmprunteurs().add(emprunteur);
		}
	}
	
	 /**
	  * M�thode pour lister tous les emprunteurs en retard = les emprunteurs ayant
	  * dans leur inventaire un ou plusieurs livres dont la date de rendu est d�pass�e
	  * @return retards un objet HashMap associant un emprunteur (objet Emprunteur)
	  * � la liste de ses livres (objets Livre) en retard
	  */
	public HashMap<Emprunteur, ArrayList<Livre>> listerEmprunteursEnRetard() {
		LocalDate aujourdhui = LocalDate.now();
		HashMap<Emprunteur, ArrayList<Livre>> retards = new HashMap<Emprunteur, ArrayList<Livre>>();
		for (Emprunteur emprunteur : emprunteurs) {
			for (Map.Entry<Livre, LocalDate> emprunt : emprunteur.getLivresEmpruntes().entrySet()){
				LocalDate dateRendu = emprunt.getValue();
				if (dateRendu.isBefore(aujourdhui)) {
					if (Objects.nonNull(retards.get(emprunteur))){
						retards.get(emprunteur).add(emprunt.getKey());
					} else {
						ArrayList<Livre> livres = new ArrayList<Livre>();
						livres.add(emprunt.getKey());
						retards.put(emprunteur, livres);
					}
				}
			}
		}
		return retards;
	}
	
	 /**
	  * M�thode pour "relancer" un emprunteur en retard
	  * Envoie un message � l'emprunteur en retard : ajoute � sa bo�te de r�ception
	  * (attribut messagerie) un message associ� � l'intitul� "retard"
	  */
	public void RelancerEmprunteurEnRetard() {
		HashMap<Emprunteur, ArrayList<Livre>> retards = listerEmprunteursEnRetard();
		String livresRetard = "";
		for (Emprunteur emprunteur : retards.keySet()) {
			for (Livre livre : retards.get(emprunteur)) {
				livresRetard += "\n- " + livre.getTitre();
			}
			emprunteur.addMessage("retard", "vous avez des livres en retard : " + livresRetard);
		}
	}

	 /**
	  * M�thode pour lister tous les livres du catalogue qui sont emprunt�s
	  * @return livres une liste d'objets de type Livre correspondant aux livres
	  * qui sont dans les inventaires des emprunteurs
	  */
	public ArrayList<Livre> listerLivresEmpruntes() {
		ArrayList<Livre> livres = new ArrayList<Livre>();
		for (Emprunteur emprunteur : emprunteurs) {
			for (Map.Entry<Livre, LocalDate> emprunt : emprunteur.getLivresEmpruntes().entrySet()){
				livres.add(emprunt.getKey());
			}
		}
		return livres;
	}
	
	 /**
	  * M�thode pour lister tous les livres emprunt�s par un type d'emprunteur sp�cifique
	  * (Permet notamment de lister tous les livres emprunt�s par des �tudiants, 
	  * de la classe EtudiantEmprunteur qui h�rite de la classe Emprunteur)
	  * Surcharge de la m�thode listerLivresEmpruntes
	  * @param cls une classe d�riv�e de la classe Emprunteur
	  * @return livres une liste d'objets de type Livre 
	  */
	public ArrayList<Livre> listerLivresEmpruntes(Class<? extends Emprunteur> cls) {
		ArrayList<Livre> livres = new ArrayList<Livre>();
		for (Emprunteur emprunteur : emprunteurs) {
			if (cls.isInstance(emprunteur)) {
				for (Map.Entry<Livre, LocalDate> emprunt : emprunteur.getLivresEmpruntes().entrySet()){
					livres.add(emprunt.getKey());
				}
			}
		}
		return livres;
	}
	
	 /**
	  * M�thode pour lister tous les livres d'un auteur qui sont emprunt�s
	  * Surcharge de la m�thode listerLivresEmpruntes
	  * @param auteurFiltre un objet de type Auteur correspondant � l'auteur recherch� 
	  * @return livresAuteur une liste d'objets de type Livre, les livres �crits par
	  * l'auteur recherch� qui sont emprunt�s
	  */
	public ArrayList<Livre> listerLivresEmpruntes(Auteur auteurFiltre) {
		ArrayList<Livre> livresAuteur = new ArrayList<Livre>();
		for (Livre livre : listerLivresEmpruntes()) {
			if (livre.getAuteur() == auteurFiltre) {
					livresAuteur.add(livre);
			}
		}
		return livresAuteur;
	}
	
	 /**
	  * M�thode s�lectionner un livre d'un th�me sp�cifique
	  * @param theme une cha�ne de car. correspondant au th�me recherch�
	  * @return choix un livre (objet Livre) s�lectionn� au hasard 
	  * parmi les livres du catalogue qui correspondent au th�me recherch�
	  * (si aucun livre ne correspond au th�me : renvoie la valeur null)
	  */
	public Livre TrouverLivreSurUnTheme(String theme) {
		ArrayList<Livre> livresTheme = listerLivres(theme);
		Random randomGenerator = new Random();
		try {
			int index = randomGenerator.nextInt(livresTheme.size());
			Livre choix = livresTheme.get(index);
			return choix;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	 /**
	  * M�thode pour envoyer une amende � un retardataire
	  * Ajoute au solde de l'emprunteur 2 euros pour chaque livre en retard 
	  * (objet Livre de son inventaire dont la date de rendu est d�pass�e)
	  * Le solde g�n�r� correspond � la somme qu'il doit � la biblioth�que
	  */
	public void EnvoyerAmendeRetardaire() {
		HashMap<Emprunteur, ArrayList<Livre>> retards = listerEmprunteursEnRetard();
		for (Emprunteur emprunteur : retards.keySet()) {
			emprunteur.changeSolde(2 * retards.get(emprunteur).size());
		}
	}
	
	 /**
	  * M�thode pour encaisser l'amende d'un retardataire
	  * Transf�re une somme du solde de l'emprunteur vers la caisse de la biblioth�que
	  * @param retardataire l'emprunteur en retard (objet Emprunteur) qui
	  * paye son amende
	  * @param versement nombre correspondant � la somme vers�e par l'emprunteur
	  */
	public void EncaisserAmendeRetardaire(Emprunteur retardataire, double versement) {
		retardataire.changeSolde(-versement);
		this.changeCaisse(versement);
		}
	
	public HashMap<Auteur, ArrayList<Livre>> getCatalogue() {
		return catalogue;
	}

	public void initialiserCatalogue(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue = catalogue;
	}

	public ArrayList<Emprunteur> getEmprunteurs() {
		return emprunteurs;
	}

	public void setEmprunteurs(ArrayList<Emprunteur> emprunteurs) {
		this.emprunteurs = emprunteurs;
	}

	public double getCaisse() {
		return caisse;
	}

	public void setCaisse(double caisse) {
		this.caisse = caisse;
	}
	
	public void changeCaisse(double montant) {
		this.caisse += montant;
	}
	
}
