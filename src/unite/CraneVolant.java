package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class CraneVolant extends Unite {

	public CraneVolant(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[5];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("cranevolant/cranevolant"+k+".png",taille, taille,false,false);
		}
		deplacement=3;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 50;
		portee = new int[] {3,5};
		pvmax=99;
		pv=pvmax;
		dmg=25;
		cost=60;
		type="zone2";
		volant = true;
	}
		
	public String toString() {
		return "Crane Volant";
	}
		
}
