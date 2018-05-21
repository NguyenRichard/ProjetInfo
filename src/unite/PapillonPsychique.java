package unite;

import javafx.scene.image.Image;
import jeu.Unite;

public class PapillonPsychique extends Unite{
	
		public PapillonPsychique(int taille, int joueur) {
			super(taille,joueur);
			images = new Image[8];
			for (int k = 0; k < images.length; k++ ) {
				int l = k+1;
				images[k] = new Image("papillonpsychique/papillon-psychique-"+l+".png",taille, taille,false,false);
			}
			deplacement=5;
			restdeplacement=deplacement;
			valable=true;
			animcompteur = 0;
			maxcompteur = 60;
			portee = new int[] {2,4};
			pvmax=99;
			pv=pvmax;
			dmg=35;
			cost=100;
			type="zone1";
			volant = true;
		}
			
		public String toString() {
			return "Papillon psychique";
		}
			
	}
