package terrain;


import javafx.scene.image.Image;
import jeu.Terrain;

public class Eau extends Terrain {
	
	public Eau(int taille){
		super(taille);
		maxcompteur = 1;
		images = new Image[1];
		images[0]=new Image("terrains/eau.png",taille, taille,false,false);
		deplacement=70;
	}
	public String toString() {
		return "Eau";
	}
}
