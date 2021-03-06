package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class AbeilleSamourai extends Unite{

	public AbeilleSamourai(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[6];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("abeillesamouraille/abeille-samouraille-"+l+".png",taille, taille,false,false);
		}
		deplacement=3;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 50;
		portee = new int[] {0,1};
		pvmax=99;
		pv=pvmax;
		dmg=50;
		cost=60;
		type="assassin";
		volant = true;
	}
		
	public String toString() {
		return "Abeille Samourai";
	}
		

}
