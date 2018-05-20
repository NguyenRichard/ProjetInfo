package jeu;

import batiments.Portal;

public class GestionCapture {
	
	Map map;
	
	GestionCapture(Map map){
		this.map=map;
	}
	
	public boolean verifCase() {
		if (map.selectionnemenu.batiment != null && map.selectionnemenu.batiment.joueur != map.selectionnemenu.unite.joueur) {
			return true;
		}
		return false;
	}
	
	 public int capture(){
	        if (this.verifCase()) {
	            map.selectionnemenu.batiment.pv -=50;
	            map.selectionne.batiment.entraincapture=true;
	            if ( map.selectionnemenu.batiment.pv == 0) {
	                int joueurcapture = map.selectionnemenu.batiment.joueur;
	                int joueurquicapture = map.selectionnemenu.unite.joueur;
	                map.selectionnemenu.batiment.joueur = joueurquicapture;
	                map.joueurs.get(joueurquicapture).add(map.selectionnemenu); //on ajoute le batiment a la liste du joueur qui a capture
	                map.joueurs.get(joueurcapture).remove(map.selectionnemenu); //on enleve le batiment de la liste du joueur capture
	                if (map.selectionnemenu.batiment instanceof Portal) {
	                    if (map.joueurs.get(joueurcapture).verifvivant(    )) {//on verifie que le joueur qui a perdu son batiment est encore vivant
	                        //si le joueur a perdu on le signal comme tel et on tue toutes ses unitées :
	                        map.perdu(joueurcapture);
	                    }
	                }
	                map.selectionnemenu.batiment.pv = 100;
	                map.selectionnemenu.batiment.cercle=map.selectionne.unite.cercle;
	                map.selectionnemenu.batiment.reset(map.joueurs.get(joueurquicapture).typearmee);
	                map.selectionne.batiment.entraincapture=false;

	            }
	            map.selectionnemenu.unite.valable=false;
	            return 0;
	        }
	        return 1;
	    }
}
