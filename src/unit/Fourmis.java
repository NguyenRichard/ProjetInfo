package unit;

import javafx.scene.image.Image;
import jeu.Unite;

public class Fourmis extends Unite{
	public Fourmis(int taille, int joueur) {
		
		super(taille);
		this.joueur = joueur;
		images = new Image[5];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("fourmis/fourmis_"+l+".png",taille, taille,false,false);
		}
		deplacement=1;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 50;
		portee = new int[] {0,1};
		pvmax=99;
		pv=pvmax;
		dmg=1;
	}
	
	public String toString() {
		return "fourmis";
	}
}
