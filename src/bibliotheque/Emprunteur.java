package bibliotheque;

import java.time.LocalDate;
import java.util.HashMap;

public class Emprunteur {
	
	private  String nom;
	private  String prenom;
	private  String situationPro;
	private HashMap<Livre, LocalDate> livresEmpruntes;

	public Emprunteur(String nom, String prenom) {
		this.setNom(nom);
		this.setPrenom(prenom);
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

}
