package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class Abeille extends Unite{

	public Abeille(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[2];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("abeille/abeille"+l+".png",taille, taille,false,false);
		}
		deplacement=1;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 30;
		portee = new int[] {0,1};
		pvmax=99;
		pv=pvmax;
		dmg=1;
		cost=20;
		volant = true;
	}
	
	public String toString() {
		return "Abeille";
	}
	

}