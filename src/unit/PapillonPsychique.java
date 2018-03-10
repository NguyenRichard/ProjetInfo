package unit;

import jeu.Unite;

public class PapillonPsychique extends Unite{
	
		public PapillonPsychique(int taille, int joueur) {
			super(taille);
			this.joueur = joueur;
			images = new String[8];
			for (int k = 0; k < images.length; k++ ) {
				int l = k+1;
				images[k] = "papillon-psychique-"+l+".png";
			}
			deplacement=15;
			restdeplacement=deplacement;
			valable=true;
			animcompteur = 0;
			maxcompteur = 60;
			portee = 3;
			pv=99;
			dmg=100;
		}
			
		public String toString() {
			return "papillon psychique";
		}
			
	}
