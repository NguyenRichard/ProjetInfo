package terrain;

import javafx.scene.image.Image;
import jeu.Terrain;

public class Void extends Terrain {

/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/**
	 * Constructeur du vide:
	 * @param taille
	 * 			Entier qui decrit la taille de l'unite en pixel lors de l'affichage.
	 * @see jeu.Case.Case
	 * 
	 * La taille du terrain est fixee par la taille de la case
	 * 			
	 */	
	public Void(int taille){
		super(taille);
		maxcompteur = 1;
		images = new Image[1];
		images[0]=new Image("terrains/Void.png",taille, taille,false,false);
		deplacement=100;
	}
	public String toString() {
		return "Void";
	}

}
