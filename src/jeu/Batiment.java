package jeu;

import javafx.scene.image.Image;

public abstract class Batiment extends Element{
	/**Entier qui decrit l'appartenance du batiment a un joueur */
	protected int joueur;
	/**Entier qui decrit les points de vie du batiment. Le batiment est capture lorsqu'ils tombent a 0*/
	protected int pv;
	/** Booleen qui decrit si le batiment est entrain d'etre capturer ou non*/
	protected boolean entraincapture;
	/** Image qui s'affiche pour indiquer a qui appartient cette unite*/
	protected Image cercle;
	
	
	
/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/**
	 * Constructeur de batiment:
	 *	  Constructeur mere, il se declinera en constructeurs fille pour les differents batiments.
	 * @param taille
	 * 			Entier qui decrit la taille de l'element en pixel lors de l'affichage.
	 * @see Case#Case(int,int,int)
	 * 
	 * La taille du batiment est fixee par la taille de la case
	 * 			
	 */	
	public Batiment(int taille, int joueur){
		super(taille);
		this.joueur=joueur;
		if (joueur != 0){
			cercle = new Image("cerclejoueur/cerclejoueur"+joueur+".png",75,75,false,false);
		}
		maxcompteur = 1;
		entraincapture=false;
	}
	public abstract String toString();
	public abstract void reset(int typearmee);
	
}
