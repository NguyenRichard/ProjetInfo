package jeu;

public class Gestioncapture {
	
	Map map;
	
	Gestioncapture(Map map){this.map=map; }
	
	public boolean verifCase() {
		if (map.selectionnemenu.batiment != null && map.selectionnemenu.batiment.joueur != map.selectionnemenu.unite.joueur) {
			return true;
		}
		return false;
	}
	
	public int capture(){
		if (this.verifCase()) {
			Batiment batimentencapture = map.selectionnemenu.batiment;
			batimentencapture.pv -=50;
			batimentencapture.entraincapture=true;
			if ( batimentencapture.pv == 0) {
				//Nouveau code :
				batimentencapture.joueur.remove(map.selectionnemenu.batiment);
				map.verifjoueursvivants();
				batimentencapture.changejoueur(map.selectionne.unite.joueur);
				map.selectionne.unite.joueur.add(map.selectionnemenu.batiment);
				batimentencapture.pv = 100;
				batimentencapture.entraincapture=false;
			}
			map.selectionnemenu.unite.valable=false;
			return 0;
		}
		return 1;
	}
	
}
