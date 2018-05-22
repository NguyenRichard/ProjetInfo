package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class MaitreNecromancien extends Unite {
	
	public MaitreNecromancien(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[6];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("maitrenecromancien/maitrenecromancien"+k+".png",taille, taille,false,false);
		}
		deplacement=9;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 60;
		portee = new int[] {0,1};
		pvmax=150;
		pv=pvmax;
		dmg=50;
		type = "faucheur";
		cost=100;
		volant = false;
	}
	
	public String toString() {
		return "Maitre Necromancien";
	}
}
