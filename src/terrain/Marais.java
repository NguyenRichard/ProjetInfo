package terrain;


import javafx.scene.image.Image;
import jeu.Terrain;

public class Marais extends Terrain {
	
	public Marais(int taille){
		super(taille);
		maxcompteur = 1;
		images = new Image[1];
		images[0]=new Image("terrains/marais.jpg",taille, taille,false,false);
		deplacement=4;
	}
	public String toString() {
		return "Marais : \ncout deplacement = " + deplacement;
	}
}
