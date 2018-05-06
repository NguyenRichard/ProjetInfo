package jeu;

public abstract class Unite extends Element{
	/**Entier qui decrit l'appartenance a un joueur. */
	protected Joueur joueur;
	/**Booleen qui decrit si l'on a joue l'unite ou non. */
	protected boolean valable;
	/**Entier qui decrit le nombre de case max que l'unite peut parcourir. */
	protected int deplacement;
	/**Entier qui decrit le nombre de case que l'unite peut encore parcourir (pour les dÃ©placements en cours). */
	protected int restedeplacement;
	/**Entier representant la portee de l'unite */
	protected int[] portee;
	/**Entier qui represente le nombre de vie*/
	protected int pv;
	/**Entier qui permet de connaitre la quantité de pt de vie pr affichage barre de vie*/
	protected int pvmax; 
	/**Entier qui represente le nombre d'attaque*/
	protected int dmg;
	
/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	/**
	 * Constructeur d'Unite:
	 *	  Constructeur mere, il se declinera en constructeurs fille pour les differents d'unites.
	 * @param taille
	 * 			Entier qui decrit la taille de l'unite en pixel lors de l'affichage.
	 * @param joueur TODO
	 * @see jeu.Case#Case
	 * 
	 * La taille de l'unite est fixee par la taille de la case
	 * 			
	 */
	protected Unite(int taille, Joueur joueur){
		super(taille);
		this.joueur=joueur;
		valable=false;
		pv=pvmax;
	}
	
	protected Unite(int taille) {
		super(taille);
		valable=false;
	}
	
	public abstract String toString() ;
	
	/**
	 * Sert a  dire si l'unite appartient au joueur en train de jouer.
	 * 
	 */
	/*boolean goodplayer(int placejoueur) {
		return (this.joueur==);
	}*/

}
