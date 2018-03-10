package jeu;

public abstract class Unite extends Element{
	/**Entier qui decrit l'appartenance a un joueur. */
	protected int joueur;
	/**Booleen qui decrit si l'on a joue l'unite ou non. */
	protected boolean valable;
	/**Entier qui decrit le nombre de case max que l'unite peut parcourir. */
	protected int deplacement;
	/**Entier qui decrit le nombre de case que l'unite peut encore parcourir (pour les déplacements en cours). */
	protected int restdeplacement;
	/**Entier representant la portee de l'unite */
	protected int portee;
	/**Entier qui represente le nombre de vie*/
	protected int pv;
	/**Entier qui represente le nombre d'attaque*/
	protected int dmg;
	
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
	protected Unite(int taille){
		super(taille);
		/*joueur = 0; // Lorsque les types filles sont creees, mettre cette ligne dans le constructeur du type fille
		image = "rond.png"; // Lorsque les types filles sont creees, mettre cette ligne dans le constructeur du type fille
		deplacement=0; // Lorsque les types filles sont creees, mettre cette ligne dans le constructeur du type fille
		*/
		valable=false;
	}
	public abstract String toString() ;
	
	/**
	 * Sert à dire si l'unite appartient au joueur en train de jouer.
	 * 
	 */
	boolean goodplayer(int joueur) {
		return (this.joueur==joueur);
	}

}
