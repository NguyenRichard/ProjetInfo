package terrain;

import javafx.scene.image.Image;
import jeu.Terrain;

public class Terre extends Terrain{
	
	public Terre(int taille){
		super(taille);
		maxcompteur = 1;
		images = new Image[1];
		images[0]=new Image("terrains/terre2.png",taille, taille,false,false);
		deplacement=1;
	}
	public String toString() {
		return "Terre";
	}
}
