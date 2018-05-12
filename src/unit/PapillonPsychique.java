package unit;

import javafx.scene.image.Image;
import jeu.Unite;

public class PapillonPsychique extends Unite{
	
		public PapillonPsychique(int taille, int joueur) {
			super(taille);
			this.joueur = joueur;
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
			portee = new int[] {1,3};
			pvmax=99;
			pv=pvmax;
			dmg=100;
			cost=20;
		}
			
		public String toString() {
			return "Papillon psychique";
		}
			
	}
