package jeu;

import batiments.Portal;

public class Gestioncapture {
	
	Map map;
	
	Gestioncapture(Map map){
		this.map=map;
	}
	
	public boolean verifCase() {
		if (map.selectionnemenu.batiment != null && map.selectionnemenu.batiment.joueur != map.selectionnemenu.unite.joueur) {
			return true;
		}
		return false;
	}
	
	
}
