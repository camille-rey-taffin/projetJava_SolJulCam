package bibliotheque;

import java.io.Serializable;

public class Livre implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Auteur auteur;
	private String titre;
	private String resume;
	private int anneePublication;
	private int nbTomes;
	
	public Livre(Auteur auteur,String titre) {
		this.auteur=auteur;
		this.titre=titre;
	}
	
	public Auteur getAuteur() {
		return auteur;
	}

	public int getAnneePublication() {
		return anneePublication;
	}
	
	@Override
	public boolean equals(Object livre) {
		if (this == livre) {
			return true;
		}
		if (livre instanceof Livre) {
			return ((this.getAuteur().equals(((Livre) livre).getAuteur()))
					&& (this.titre.equals(((Livre) livre).getTitre())));
		}
		return false;
	}

	public int hashCode() {
		return titre.hashCode();
	}


	public void setAnneePublication(int anneePublication) {
		this.anneePublication = anneePublication;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getNbTomes() {
		return nbTomes;
	}

	public void setNbTomes(int nbTomes) {
		this.nbTomes = nbTomes;
	}


}
