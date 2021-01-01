package bibliotheque;

public class Etudiant extends Emprunteur {
	
	private long numeroEtudiant;

	public Etudiant(String nom, String prenom, long numeroEtudiant) {
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
