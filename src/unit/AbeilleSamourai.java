package unit;

import javafx.scene.image.Image;
import jeu.Unite;

public class AbeilleSamourai extends Unite{

	public AbeilleSamourai(int taille, int joueur) {
		super(taille);
		this.joueur = joueur; 
		images = new Image[6];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("abeillesamouraille/abeille-samouraille-"+l+".png",taille, taille,false,false);
		}
		deplacement=15;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 50;
		portee = new int[] {0,1};
		pv=99;
		dmg=1;
	}
		
	public String toString() {
		return "abeille samourai";
	}
		

}
