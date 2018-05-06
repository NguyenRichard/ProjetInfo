package batiments;

import javafx.scene.image.Image;
import jeu.Batiment;

public class Carregris extends Batiment {
	
	public Carregris(int taille, int joueur){
		super(taille);
		this.joueur = joueur;
		pv = 100;
		images = new Image[1];
		images[0] = new Image("test"+".png",taille, taille,false,false);
	}
}
