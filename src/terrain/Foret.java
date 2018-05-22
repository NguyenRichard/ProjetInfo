package terrain;


import javafx.scene.image.Image;
import jeu.Terrain;

public class Foret extends Terrain {
	
	public Foret(int taille){
		super(taille);
		maxcompteur = 1;
		images = new Image[1];
		images[0]=new Image("terrains/foret.png",taille, taille,false,false);
		deplacement=3;
	}
	public String toString() {
		return "Foret : \ncout deplacement = " + deplacement;
	}
}

