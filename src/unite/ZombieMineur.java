package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class ZombieMineur extends Unite{
	public ZombieMineur(int taille, int joueur) {
		
		super(taille,joueur);
		images = new Image[3];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("zombie/zombie"+k+".png",taille, taille,false,false);
		}
		deplacement=7;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 50;
		portee = new int[] {0,1};
		pvmax=33;
		pv=pvmax;
		dmg=15;
		cost=40;
		type="mineur";
	}
	
	public String toString() {
		return "ZombieMineur";
	}
}
