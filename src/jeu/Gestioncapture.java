package jeu;

import java.util.ArrayList;

public class Gestioncapture {
	
	Map map;
	ArrayList<Case> entraincapture;
	
	Gestioncapture(Map map){
		this.map=map;
		entraincapture = new ArrayList<Case>();
	}
	
	public boolean verifCase() {
		if (map.selectionnemenu.batiment != null && map.selectionnemenu.batiment.joueur != map.selectionnemenu.unite.joueur) {
			return true;
		}
		return false;
	}
	
	public int capture(){
		if (this.verifCase()) {
			Batiment batimentselect = new Batiment(map.taillec);
			batimentselect = map.selectionnemenu.batiment;
			batimentselect.pv -=50;
			if (batimentselect.pv == 50) {
				entraincapture.add(map.selectionnemenu);
			}
			if (batimentselect.pv == 0) {
				batimentselect.joueur = map.selectionnemenu.unite.joueur;
				batimentselect.pv = 100;
				entraincapture.remove(map.selectionnemenu);
				
			}
			map.selectionnemenu.unite.valable=false;
			return 0;
		}
		return 1;
	}
	
	public void refreshCapture() {
		for (Case cible: entraincapture) {
			if (cible.unite == null) {
				cible.batiment.pv = 100;
				entraincapture.remove(cible);
			}
		}
	}
}
