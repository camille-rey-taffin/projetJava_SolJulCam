package bibliotheque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.util.Map.Entry;
import java.time.LocalDate;

public class Bibliothecaire {

	private HashMap<Auteur, ArrayList<Livre>> catalogue;
	private ArrayList<Emprunteur> emprunteurs;
	private double caisse;
	
	public Bibliothecaire(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue=catalogue;
		this.setCaisse(0);
		
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

	public String listerOeuvresAuteur(Auteur auteur) {
		ArrayList<Livre> livres= catalogue.get(auteur);
		String titreDesLivres="";
		for (Livre livre : livres) {
			titreDesLivres+=livre.getTitre()+ "\n";
		}
		return "L'auteur "+auteur.getNom()+" a ecrit "+livres.size() +" livres:\n"+ titreDesLivres;
	}
	
	public void enleverLivre(Livre ancienLivre) {
		if ((Objects.nonNull(getCatalogue().get(ancienLivre.getAuteur()))) ) {
			getCatalogue().get(ancienLivre.getAuteur()).remove(ancienLivre);
		}
	}
	
	public void preterLivre(Livre livre, Emprunteur emprunteur, LocalDate date) {
		emprunteur.getLivresEmpruntes().put(livre, date);
		if (Objects.nonNull(getEmprunteurs())){
			if (!getEmprunteurs().contains(emprunteur)) {
				getEmprunteurs().add(emprunteur);
			}
		} else {
			ArrayList<Emprunteur> emprunteurs= new ArrayList<Emprunteur>();
			emprunteurs.add(emprunteur);
			setEmprunteurs(emprunteurs);
		}
	}
	
	public HashMap<Emprunteur, ArrayList<Livre>> ListerEmprunteursEnRetard(ArrayList<Emprunteur> emprunteurs) {
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
	
	public void RelancerEmprunteurEnRetard(ArrayList<Emprunteur> emprunteurs) {
		HashMap<Emprunteur, ArrayList<Livre>> retards = ListerEmprunteursEnRetard(emprunteurs);
		String livresRetard = "";
		for (Emprunteur emprunteur : retards.keySet()) {
			for (Livre livre : retards.get(emprunteur)) {
				livresRetard += "\n- " + livre.getTitre();
			}
			emprunteur.addMessage("retard", "vous avez des livres en retard : " + livresRetard);
		}
	}
	
	public ArrayList<Emprunteur> listerPersonnesAyantEmprunteUnLivre() {
		if (Objects.nonNull(getEmprunteurs())){
			return getEmprunteurs();
		} else {
			return new ArrayList<Emprunteur>();
		}
	}
	
	public ArrayList<Livre> listerLivresEmpruntesParEtudiant() {
		ArrayList<Livre> livres = new ArrayList<Livre>();
		for (Emprunteur emprunteur : emprunteurs) {
			if (emprunteur instanceof Etudiant) {
				for (Map.Entry<Livre, LocalDate> emprunt : emprunteur.getLivresEmpruntes().entrySet()){
					livres.add(emprunt.getKey());
				}
			}
		}
		return livres;
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
	
	public ArrayList<Livre> listerLivresAnglais() {
		ArrayList<Livre> livres = new ArrayList<Livre>();
		for (Entry<Auteur, ArrayList<Livre>> a : getCatalogue().entrySet()) {
			for (Livre livre : a.getValue()) {
				if (livre instanceof LivreAnglais) {
					livres.add(livre);
				}
			}
		}
		return livres;
	}
	
	public ArrayList<Livre> ListerNbLivresEmpruntesPourUnAuteur(String auteurTest) {
		ArrayList<Livre> livresAuteur = new ArrayList<Livre>();
		ArrayList<Livre> livres = listerLivresEmpruntes();
		for (Livre livre : livres) {
			if (livre.getAuteur().getNom() == auteurTest) {
					livresAuteur.add(livre);
			}
		}
		return livresAuteur;
	}
	
	public void EnvoyerAmendeRetardaire() {
		HashMap<Emprunteur, ArrayList<Livre>> retards = ListerEmprunteursEnRetard(emprunteurs);
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
