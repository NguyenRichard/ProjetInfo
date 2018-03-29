package unit;

import javafx.scene.image.Image;
import jeu.Unite;

public class Abeille extends Unite{

	public Abeille(int taille, int joueur) {
		super(taille);
		this.joueur = joueur;
		images = new Image[2];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("abeille/abeille"+l+".png",taille, taille,false,false);
		}
		deplacement=15;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 30;
		portee = 1;
		pv=99;
		dmg=1;
		
	}
	
	public String toString() {
		return "abeille";
	}
	

}
