package jeu;

import batiments.Portail;

public class GestionCapture {
	/**La carte du jeu (tableau de cases) */
	Carte carte;
	
	GestionCapture(Carte carte){
		this.carte=carte;
	}
	
	/**@return un booleen, vrai si on est bien dans un cas de capture et faux sinon (evite les nullpointer exceptions)*/
	public boolean verifCase() {
		if (carte.selectionnemenu.batiment != null && carte.selectionnemenu.batiment.joueur != carte.selectionnemenu.unite.joueur) {
			return true;
		}
		return false;
	}
	
	/**Gere la capture du batiment et tout ce qui va avec : avancement dans la capture, changement de proprietaire et mort
	 * d'un joueur dans le cas de la capture de son dernier portail
	 * 
	 * @return 0 si on est bien dans le cas d'une capture, 1 sinon
	 */
	 public int capture(){
	        if (this.verifCase()) {
	        	if (carte.selectionnemenu.unite.type.compareTo("mineur")==0) { //si c'est un mineur on capture en 1 tour
	        		carte.selectionnemenu.batiment.pv = 0;
	        	}
	        	else {
		            carte.selectionnemenu.batiment.pv -=50;
		            carte.selectionne.batiment.entraincapture=true;
	        	}
	            if ( carte.selectionnemenu.batiment.pv == 0) {
	                int joueurcapture = carte.selectionnemenu.batiment.joueur;
	                int joueurquicapture = carte.selectionnemenu.unite.joueur;
	                carte.selectionnemenu.batiment.joueur = joueurquicapture;
	                carte.joueurs.get(joueurquicapture).add(carte.selectionnemenu); //on ajoute le batiment a la liste du joueur qui a capture
	                carte.joueurs.get(joueurcapture).remove(carte.selectionnemenu); //on enleve le batiment de la liste du joueur capture
	                if (carte.selectionnemenu.batiment instanceof Portail) {
	                    if (carte.joueurs.get(joueurcapture).verifvivant(    )) {//on verifie que le joueur qui a perdu son batiment est encore vivant
	                        //si le joueur a perdu on le signal comme tel et on tue toutes ses unitées :
	                        carte.perdu(joueurcapture);
	                    }
	                }
	                carte.selectionnemenu.batiment.pv = 100;
	                carte.selectionnemenu.batiment.cercle=carte.selectionne.unite.cercle;
	                carte.selectionnemenu.batiment.reset(carte.joueurs.get(joueurquicapture).typearmee);
	                carte.selectionne.batiment.entraincapture=false;

	            }
	            carte.selectionnemenu.unite.valable=false;
	            return 0;
	        }
	        return 1;
	    }
}
