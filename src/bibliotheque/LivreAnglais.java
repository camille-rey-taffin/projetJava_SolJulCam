package bibliotheque;

public class LivreAnglais  extends Livre {
	
	private static final long serialVersionUID = 1L;
	String traducteur;

	public LivreAnglais(Auteur auteur,String titre, String traducteur) {
		super(auteur, titre);
		this.traducteur = traducteur;
	}

	public String getTraducteur() {
		return traducteur;
	}

	public void setTraducteur(String traducteur) {
		this.traducteur = traducteur;
	}
	
}
