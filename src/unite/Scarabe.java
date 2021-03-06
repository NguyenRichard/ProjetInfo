package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class Scarabe extends Unite {
	
	public Scarabe(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[6];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("scarabe/scarabe-"+l+".png",taille, taille,false,false);
		}
		deplacement=5;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 75;
		portee = new int[] {0,3};
		pvmax=150;
		pv=pvmax;
		dmg=50;
		cost=70;
		type="ligne";
	}
	
	public String toString() {
		return "Scarabe";
	}
	
}
