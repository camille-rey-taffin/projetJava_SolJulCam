package bibliotheque;

/**
 * Classe repr�sentant un emprunteur qui est �tudiant
 * H�rite de la classe Emprunteur : {@inheritDoc} 
 */
public class EtudiantEmprunteur extends Emprunteur {
	
	private long numeroEtudiant;

	 /**
	  * Constructeur de la classe EtudiantEmprunteur
	  * @param nom le nom de l'individu emprunteur
	  * @param prenom le pr�nom de l'emprunteur
	  * @param numeroEtudiant un nombre correspondant au num�ro �tudiant de l'emprunteur
	  */
	public EtudiantEmprunteur(String nom, String prenom, long numeroEtudiant) {
		super(nom, prenom);
		this.setNumeroEtudiant(numeroEtudiant);
	}

	public long getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setNumeroEtudiant(long numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}
}
