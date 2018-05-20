package terrain;


import javafx.scene.image.Image;
import jeu.Terrain;

public class Eau extends Terrain {
	
	public Eau(int taille){
		super(taille);
		maxcompteur = 4000;
		images = new Image[4];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("terrains/eau"+k+".png",taille, taille,false,false);
		}
		deplacement=70;
	}
	public String toString() {
		return "Eau";
	}
}
