package batiments;

import javafx.scene.image.Image;
import jeu.Batiment;

public class Crystal extends Batiment{
	
	public Crystal(int taille, int joueur){
		super(taille);
		this.joueur = joueur;
		pv = 100;
		images = new Image[5];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("crystal/crystal"+k+".png",taille, taille,false,false);
		}
		maxcompteur = 70;
	}
	
	public String toString() {
		return "Crystal";
	}
	
	public void reset(int typearmee) {
		//ne fait rien (utile pour les portails)
	}
}
