package batiments;

import javafx.scene.image.Image;
import jeu.Batiment;

public class Crystal extends Batiment{
	
	public Crystal(int taille, int joueur){
		super(taille);
		this.joueur = joueur;
		pv = 100;
		images = new Image[1];
		images[0] = new Image("crystal"+".png",taille, taille,false,false);
	}
	
	public String toString() {
		return "Crystal";
	}
}
