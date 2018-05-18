package jeu;

import javafx.scene.image.Image;

public abstract class Unite extends Element implements Cloneable{
	/**Entier qui decrit l'appartenance a un joueur. */
	protected int joueur;
	/**Booleen qui decrit si l'on a joue l'unite ou non. */
	protected boolean valable;
	/**Entier qui decrit le nombre de case max que l'unite peut parcourir. */
	protected int deplacement;
	/**Entier qui decrit le nombre de case que l'unite peut encore parcourir (pour les dÃ©placements en cours). */
	protected int restdeplacement;
	/**Entier representant la portee de l'unite */
	protected int[] portee;
	/**Entier qui represente le nombre de vie*/
	protected int pv;
	/**Entier qui permet de connaitre la quantité de pt de vie pr affichage barre de vie*/
	protected int pvmax; 
	/**Entier qui represente le nombre d'attaque*/
	protected int dmg;
	/**Represente le type de l'unite*/
	protected String type;
	protected int cost;
	/** Image qui s'affiche lors de la capture d'un batiment par cette unite*/
	protected Image flag;
	/** Image qui s'affiche pour indiquer a qui appartient cette unite*/
	protected Image cercle;
	
/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	/**
	 * Constructeur d'Unite:
	 *	  Constructeur mere, il se declinera en constructeurs fille pour les differents d'unites.
	 * @param taille
	 * 			Entier qui decrit la taille de l'unite en pixel lors de l'affichage.
	 * @see jeu.Case#Case
	 * 
	 * La taille de l'unite est fixee par la taille de la case
	 * 			
	 */
	protected Unite(int taille,int joueur){
		super(taille);
		this.joueur=joueur;
		flag = new Image("flag/flag"+joueur+".png",75,75,false,false);
		if (joueur !=0) {
			cercle = new Image("cerclejoueur/cerclejoueur"+joueur+".png",75,75,false,false);
		}
		valable=false;
		type = "soldat";
	}
	public abstract String toString() ;
	
	/**
	 * Sert a  dire si l'unite appartient au joueur en train de jouer.
	 * 
	 */
	boolean goodplayer(int joueur) {
		return (this.joueur==joueur);
	}
	
    protected Unite clone() throws CloneNotSupportedException {
        return (Unite) super.clone();
    }

}
