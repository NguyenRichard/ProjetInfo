package jeu;

import java.io.Serializable;

public class Sauvegardemap implements Serializable {
	private static final long serialVersionUID = 1L;
	/**tableau d'entier correspondant aux cases */
	public int[] grillemap;
	
	
	Sauvegardemap(){
		grillemap = new int[2501];
		for (int k=0; k<grillemap.length-1;k++) {
			grillemap[k] = 0;
		}
		grillemap[2500] = 1+50+125000;
	}
	

}
