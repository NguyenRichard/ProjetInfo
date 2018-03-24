package jeu;

import java.util.ArrayList;

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
	
	public int capture(){
		if (this.verifCase()) {
			map.selectionnemenu.batiment.pv -=50;
			map.selectionne.batiment.entraincapture=true;
			if ( map.selectionnemenu.batiment.pv == 0) {
				map.selectionnemenu.batiment.joueur = map.selectionnemenu.unite.joueur;
				map.selectionnemenu.batiment.pv = 100;
				map.selectionne.batiment.entraincapture=false;
				
			}
			map.selectionnemenu.unite.valable=false;
			return 0;
		}
		return 1;
	}
	
}
