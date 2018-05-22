package jeu;

import java.io.Serializable;

public class SauvegardeCarte implements Serializable {
	private static final long serialVersionUID = 1L;
	/**tableau d'entier correspondant aux cases */
	public int[] grillecarte;
	
	
	SauvegardeCarte(){
		grillecarte = new int[2501];
		for (int k=0; k<grillecarte.length-1;k++) {
			grillecarte[k] = 0;
		}
		grillecarte[2500] = 1+50+125000; //dans le cas ou la sauvegarde n'existe pas, on initialise la case de visualisation
										// pour qu'elle montre la première unite du joueur 1 et le batiment du joueur neutre (0)
	}

}
