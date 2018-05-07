package jeu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Element {
	/* Classe qui regroupe batiment, terrain et unite. */
	
	/**Nom de l'image du terrain */
	protected Image[] images;
	/**Taille d'un element */
	int taille;
	/**Compteur des sprites qui s'affichent**/
	protected int animcompteur;
	/**Compteur maximal de sprite (si il est grand alors l'animation est lente et si il est petit l'animation est rapide */
	protected int maxcompteur;
	
/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/**
	 * Constructeur d'Element
	 *	  Constructeur mere, il se declinera en constructeurs fille pour les differents elements: batiment, terrain, unite.
	 * @param taille
	 * 			Entier qui decrit la taille de l'unite en pixel lors de l'affichage.
	 * @see jeu.Case.Case
	 * 
	 * La taille de l'unite est fixee par la taille de la case
	 * 			
	 */	
	Element(int taille){
		this.taille=taille;
		animcompteur = 0;
	}
/*_Affichager du sprites__________________________________________________________________________________________________________ */
	/**
	 * 		Affichage d'un element:
	 * 
	 * @param gc 
	 * 		Contexte graphique dans lequel on affiche.
	 * @param x
	 * 		Coordonee pixel en abscisse sur l'ecran d'affichage.
	 * @param y
	 * 		Cordonnee pixel en ordonnee sur l'ecran d'affichage.
	 * @see jeu.Case#render
	 * 		Calcul des coordonees en fonction du rang de la case dans le plateau
	 */
	
	void render(GraphicsContext gc, int x, int y) {
		// Avec x, y sont les coordonnees sur l'ecran calculees grace a la Map
			int k = animcompteur / (maxcompteur/images.length);
			if (k >= images.length) {
				animcompteur = 0;
				k=0;
			}
			if (this instanceof Batiment) {
				Batiment bat = (Batiment)this;
				String txt = "cerclejoueur" + bat.joueur+".png";
				Image cercle = new Image(txt,taille, taille,false,false);
				gc.drawImage(cercle, x, y);
			}
			else if (this instanceof Unite) {
				Unite uni = (Unite)this;
				String txt = "cerclejoueur" + uni.joueur+".png";
				Image cercle = new Image(txt,taille, taille,false,false);
				gc.drawImage(cercle, x, y);
			}
			Image sprite = images[k];
			gc.drawImage(sprite, x, y);
			animcompteur += 1;
	}
}
