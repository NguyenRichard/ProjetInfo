package terrain;


import javafx.scene.image.Image;
import jeu.Terrain;

public class Montagne extends Terrain {
	
	public Montagne(int taille){
		super(taille);
		maxcompteur = 4000;
		images = new Image[1];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("terrains/Montagne.png",taille, taille,false,false);
		}
		deplacement=5;
	}
	public String toString() {
		return "Montagne : \ncout deplacement = " + deplacement;
	}
}