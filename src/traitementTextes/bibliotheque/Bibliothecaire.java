package traitementTextes.bibliotheque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.time.LocalDate;

public class Bibliothecaire {

	private HashMap<Auteur, ArrayList<Livre>> catalogue;
	private ArrayList<Emprunteur> emprunteurs;
	
	public Bibliothecaire(HashMap<Auteur, ArrayList<Livre>> catalogue) {
		this.catalogue=catalogue;
		
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
		if (Objects.nonNull(emprunteur.getLivresEmpruntes())) {
			emprunteur.getLivresEmpruntes().put(livre, date);
		}
		else {
			HashMap<Livre, LocalDate> livresEmpruntes = new HashMap<>();
			livresEmpruntes.put(livre, date);
			emprunteur.setLivresEmpruntes(livresEmpruntes);
		}
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
	
	public HashMap<Emprunteur, ArrayList<Livre>> RelancerEmprunteurEnRetard(ArrayList<Emprunteur> emprunteurs) {
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
	
	public ArrayList<Emprunteur> listerPersonnesAyantEmprunteUnLivre() {
		if (Objects.nonNull(getEmprunteurs())){
			return getEmprunteurs();
		} else {
			return new ArrayList<Emprunteur>();
		}
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

}
