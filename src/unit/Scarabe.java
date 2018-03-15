package unit;

import jeu.Unite;

public class Scarabe extends Unite {
	
	public Scarabe(int taille, int joueur) {
		super(taille);
		this.joueur = joueur; 
		images = new String[6];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = "scarabe/scarabe-"+l+".png";
		}
		deplacement=15;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 75;
		portee = 2;
		pv=99;
		dmg=1;
		
	}
	
	public String toString() {
		return "scarabe";
	}
	
}
