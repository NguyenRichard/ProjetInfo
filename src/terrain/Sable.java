package terrain;


import javafx.scene.image.Image;
import jeu.Terrain;

public class Sable extends Terrain {
	
	public Sable(int taille){
		super(taille);
		maxcompteur = 4000;
		images = new Image[1];
		for (int k = 0; k < images.length; k++ ) {
			images[k] = new Image("terrains/sable.png",taille, taille,false,false);
		}
		deplacement=3;
	}
	public String toString() {
		return "Sable : \ncout deplacement = " + deplacement;
	}
}