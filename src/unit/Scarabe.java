package unit;

import javafx.scene.image.Image;
import jeu.Joueur;
import jeu.Unite;

public class Scarabe extends Unite {
	
	public Scarabe(int taille, Joueur joueur) {
		super(taille, joueur);
		images = new Image[6];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("scarabe/scarabe-"+l+".png",taille, taille,false,false);
		}
		deplacement=1;
		restedeplacement=deplacement;
		valable=true;
		maxcompteur = 75;
		portee = new int[] {0,2};
		pvmax=99;
		pv=pvmax;
		dmg=80;
		
	}
	
	public String toString() {
		return "scarabe du joueur "+joueur;
	}
	
}
