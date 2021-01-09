package bibliotheque;

/**
 * Classe repr�sentant un livre en anglais
 * H�rite de la classe Livre : {@inheritDoc} 
 */
public class LivreAnglais  extends Livre {
	
	private static final long serialVersionUID = 1L;
	String traducteur;

	 /**
	  * Constructeur de la classe LivreAnglais
	  * @param auteur l'auteur du livre, objet de type auteur
	  * @param titre le titre du livre (cha�ne de car.)
	  * @param traducteur le nom du traducteur (cha�ne de car.)
	  */
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
