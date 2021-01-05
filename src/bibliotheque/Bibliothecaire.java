package bibliotheque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.time.LocalDate;

public class Bibliothecaire {

	private HashMap<Auteur, ArrayList<Livre>> catalogue;
	private ArrayList<Emprunteur> emprunteurs;
	private double caisse;
	
	public Bibliothecaire(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue=catalogue;
		this.setCaisse(0);
		this.emprunteurs = new ArrayList<Emprunteur>();
	}

	public void ajouterLivre(Livre nouveauLivre) {
		if (Objects.nonNull(getCatalogue().get(nouveauLivre.getAuteur()))) {
			getCatalogue().get(nouveauLivre.getAuteur()).add(nouveauLivre);
		} else {
			ArrayList<Livre> livres = new ArrayList<>();
			livres.add(nouveauLivre);
			getCatalogue().put(nouveauLivre.getAuteur(), livres);
		}
	}

	public void enleverLivre(Livre ancienLivre) {
		if ((Objects.nonNull(getCatalogue().get(ancienLivre.getAuteur()))) ) {
			getCatalogue().get(ancienLivre.getAuteur()).remove(ancienLivre);
		}
	}
	
	public ArrayList<Livre> listerLivres() {
		ArrayList<Livre> listeLivres = new ArrayList<Livre>();
		for (ArrayList<Livre> livres : getCatalogue().values()) {
			for (Livre livre : livres) {
				listeLivres.add(livre);
			}
		}
		return listeLivres;
	}
	
	public ArrayList<Livre> listerLivres(Auteur auteur) {
		return getCatalogue().get(auteur);
	}
	
	public ArrayList<Livre> listerLivres(String theme) {
		ArrayList<Livre> listeLivresTheme = new ArrayList<Livre>();
		for (Livre livre : listerLivres()) {
			if (livre.getTheme() == theme) {
				listeLivresTheme.add(livre);
			}
		}
		return listeLivresTheme;
	}
	
	public ArrayList<Livre> listerLivres(Class<? extends Livre> cls) {
		ArrayList<Livre> listeLivresClasse = new ArrayList<Livre>();
		for (Livre livre : listerLivres()) {
			if (cls.isInstance(livre)) {
				listeLivresClasse.add(livre);
			}
		}
		return listeLivresClasse;
	}
	
	public void preterLivre(Livre livre, Emprunteur emprunteur, LocalDate date) {
		emprunteur.addEmprunt(livre, date);
		if (!getEmprunteurs().contains(emprunteur)) {
			getEmprunteurs().add(emprunteur);
		}
	}

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

	public ArrayList<Livre> listerLivresEmpruntes() {
		ArrayList<Livre> livres = new ArrayList<Livre>();
		for (Emprunteur emprunteur : emprunteurs) {
			for (Map.Entry<Livre, LocalDate> emprunt : emprunteur.getLivresEmpruntes().entrySet()){
				livres.add(emprunt.getKey());
			}
		}
		return livres;
	}
	
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
	
	public ArrayList<Livre> listerLivresEmpruntes(Auteur auteurFiltre) {
		ArrayList<Livre> livresAuteur = new ArrayList<Livre>();
		for (Livre livre : listerLivresEmpruntes()) {
			if (livre.getAuteur() == auteurFiltre) {
					livresAuteur.add(livre);
			}
		}
		return livresAuteur;
	}
	
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
	
	public void EnvoyerAmendeRetardaire() {
		HashMap<Emprunteur, ArrayList<Livre>> retards = listerEmprunteursEnRetard();
		for (Emprunteur emprunteur : retards.keySet()) {
			emprunteur.changeSolde(2 * retards.get(emprunteur).size());
		}
	}
	
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
