package unit;

import javafx.scene.image.Image;
import jeu.Joueur;
import jeu.Unite;

public class Abeille extends Unite{

	public Abeille(int taille, Joueur joueur) {
		super(taille, joueur);
		images = new Image[2];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("abeille/abeille"+l+".png",taille, taille,false,false);
		}
		deplacement=1;
		restedeplacement=deplacement;
		valable=true;
		maxcompteur = 30;
		portee = new int[] {0,1};
		pvmax=99;
		pv=pvmax;
		dmg=1;
	}
	
	public String toString() {
		return "abeille du joueur "+joueur;
	}
	

}
