package batiments;

import javafx.scene.image.Image;
import jeu.Batiment;

public class Portal extends Batiment {
	
	public Portal(int taille, int joueur){
		super(taille);
		this.joueur = joueur;
		pv = 100;
		images = new Image[1];
		images[0] = new Image("portal"+".png",taille, taille,false,false);
	}
	
	public String toString() {
		return "Portail";
	}
}
