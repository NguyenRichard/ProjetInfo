package jeu;

import javafx.scene.canvas.GraphicsContext;
import terrain.Void;

public class Case {
	/**Unite liee a la case */
	Unite unite;
	/** Terrain lie a la case*/	
	Terrain terrain;
	/**Batiment lie a la case */
	Batiment batiment;
	/**Rang de la case, sert a la localiser par rapport a map */
	int rang;
	
/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	/**
	 * Constructeur de Case:
	 * 
	 * Initialement, la case est creee sans unite et sans batiment.
	 * 
	 * @param taillec
	 * 			Entier qui decrit la taille d'une case en pixel lors de l'affichage.
	 * @param rang
	 * 			Entier qui identifie la position d'une case dans la matrice plateau.
	 * 
	 * @see jeu.Map#Map
	 * L'ensemble de ses parametres sont determines ici
	 * 			
	 */
	Case(int taillec, int rang){
		this.unite=null;
		this.terrain = new Void(taillec);
		this.batiment=null;
		this.rang = rang;
	}
	public String toString() {
		String affichage="Terrain: "+this.terrain.toString()+"\n";
		if (this.unite !=null) {
			affichage = affichage+"Unite: "+this.unite.toString()+"\n";
		}
		else {
			affichage = affichage+"Unite: aucune"+"\n";
		}
		if (this.batiment !=null) {
			affichage = affichage+"Batiment: "+this.batiment.toString()+this.batiment.joueur+"\n";
		}
		else {
			affichage = affichage+"Batiment: aucun"+"\n";
		}
		return affichage;
	}

/*_Affichager des sprites_________________________________________________________________________________________________________ */
		/**
		* <h3>Affichage du terrain, du batiment et de l'unite de la case.</h3>
		 * La position pixel est calculee ici, a partir du rang et du rangcorner
		 * 
		 * @param gc 
		 * 		Contexte graphique dans lequel on affiche
		 * @see Map#rangcorner
		 * @see Map#rangcorner
		 */
	void render(GraphicsContext gc, int rangcorner) {
		int x = (rang%50 - rangcorner%50)*50;
		int y = (rang/50 - rangcorner/50)*50;
		terrain.render(gc,x,y);
		if (this.batiment !=null) {
			batiment.render(gc,x,y);
		}
		if (this.unite !=null) {
			unite.render(gc,x,y);
		}
	}

/*_Utilitaire___________________________________________________________________________________________________________________ */
	
	/**
	 * 
	 * @param cible
	 * @return L'entier correpondant a la distance à vol d'oiseau en case entre la case et la case cible (adjacentes = 1)
	 */
	
	int distance(Case cible) {
		int distanceCol = Math.abs(this.rang%50-cible.rang%50);
		int distanceLin = Math.abs(this.rang/50-cible.rang/50);
		return distanceCol + distanceLin;
	}
	//test mofification
	
}
