package unit;

import javafx.scene.image.Image;
import jeu.Unite;

public class Moustique extends Unite{

	public Moustique(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[10];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("moustique/moustique_"+l+".png",taille, taille,false,false);
		}
		deplacement=5;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 60;
		portee = new int[] {0,1};
		pvmax=99;
		pv=pvmax;
		dmg=-30;
		type = "healer";
		cost=20;
	}
	
	public String toString() {
		return "Moustique";
	}
}
