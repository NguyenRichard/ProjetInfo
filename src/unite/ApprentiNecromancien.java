package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class ApprentiNecromancien extends Unite {
	
	public ApprentiNecromancien(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[6];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("apprentinecromancien/apprentinecromancien"+k+".png",taille, taille,false,false);
		}
		deplacement=6;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 60;
		portee = new int[] {1,3};
		pvmax=100;
		pv=pvmax;
		dmg=-20;
		type = "zonesoin";
		cost=45;
		volant = false;
	}
	
	public String toString() {
		return "Apprenti Necromancien";
	}
}
