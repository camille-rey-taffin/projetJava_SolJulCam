package bibliotheque;

public class EtudiantEmprunteur extends Emprunteur {
	
	private long numeroEtudiant;

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
