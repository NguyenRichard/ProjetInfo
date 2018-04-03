package jeu;


public abstract class Batiment extends Element{
	/**Entier qui decrit l'appartenance du batiment a un joueur */
	protected int joueur;
	/**Entier qui decrit les points de vie du batimen*/
	protected int pv;
	protected boolean entraincapture;
	
	
	
/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/**
	 * Constructeur de batiment:
	 *	  Constructeur mere, il se declinera en constructeurs fille pour les differents batiments.
	 * @param taille
	 * 			Entier qui decrit la taille de l'unite en pixel lors de l'affichage.
	 * @see Case#Case(int,int,int)
	 * 
	 * La taille du batiment est fixee par la taille de la case
	 * 			
	 */	
	public Batiment(int taille){
		super(taille);
		maxcompteur = 1;
		entraincapture=false;
	}
	public String toString() {
		return "batiment"; // A changer en methode abstraite avec le bon nom selon le type de batiment fille
	}
	
}
