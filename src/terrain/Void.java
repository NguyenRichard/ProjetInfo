package terrain;

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
		images = new String[1];
		images[0]="Void.png";
		deplacement=100;
	}
	public String toString() {
		return "Void";
	}

}
