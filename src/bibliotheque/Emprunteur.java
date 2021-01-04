package bibliotheque;

import java.time.LocalDate;
import java.util.HashMap;

public class Emprunteur {
	
	private  String nom;
	private  String prenom;
	private  String situationPro;
	private HashMap<Livre, LocalDate> livresEmpruntes;
	private HashMap<String, String> messagerie;
	private double solde;

	
	public Emprunteur(String nom, String prenom) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.livresEmpruntes = new HashMap<Livre, LocalDate> ();
		this.setMessagerie(new HashMap<String, String>());
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSituationPro() {
		return situationPro;
	}

	public void setSituationPro(String situationPro) {
		this.situationPro = situationPro;
	}

	public HashMap<Livre, LocalDate> getLivresEmpruntes() {
		return livresEmpruntes;
	}

	public void setLivresEmpruntes(HashMap<Livre, LocalDate> livresEmpruntes) {
		this.livresEmpruntes = livresEmpruntes;
	}
	
	public void addEmprunt(Livre livreEmprunte, LocalDate dateRendu) {
		getLivresEmpruntes().put(livreEmprunte, dateRendu);
	}

	public HashMap<String, String> getMessagerie() {
		return messagerie;
	}

	public void setMessagerie(HashMap<String, String> messagerie) {
		this.messagerie = messagerie;
	}
	
	public void addMessage(String objet, String message) {
		this.messagerie.put(objet, message);
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}
	
	public void changeSolde(double amende) {
		this.solde += amende;
	}
}
