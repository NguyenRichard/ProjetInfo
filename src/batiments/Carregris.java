package batiments;

import jeu.Batiment;

public class Carregris extends Batiment {
	
	public Carregris(int taille, int joueur){
		super(taille);
		this.joueur = joueur;
		pv = 100;
		images = new String[1];
		images[0] = "batiment"+".png";
	}
}
