package unit;

import jeu.Unite;

public class Abeille extends Unite{

	public Abeille(int taille, int joueur) {
		super(taille);
		this.joueur = joueur;
		images = new String[2];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = "abeille"+l+".png";
		}
		deplacement=15;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 30;
		portee = 1;
		pv=99;
		dmg=1;
		
	}
	
	public String toString() {
		return "abeille";
	}
	

}
