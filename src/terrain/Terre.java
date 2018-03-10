package terrain;

import jeu.Terrain;

public class Terre extends Terrain{
	
	public Terre(int taille){
		super(taille);
		maxcompteur = 1;
		images = new String[1];
		images[0]="terre.jpg";
		deplacement=1;
	}
	public String toString() {
		return "Terre";
	}
}
