package jeu;

import javafx.scene.image.Image;

public class Gestiondepl {
	/**Determine si un deplacement est en cours */
	boolean deplacementencours;
	/**Le rang des cases possibles pour un deplacement */
	int[] deplist;
	Map map;
	
	Gestiondepl(Map map){
		deplacementencours=false;
		this.map=map;
	}

	/**
	 * Verifie si la case carre est libre pour un deplacement de l'unite selectionne
	 * 
	 * @param jeu TODO
	 * @param carre TODO
	 * @see Map#selectionne
	 */	
	boolean verifCase(Case carre) { // Doit aussi verifier si l'emplacement est pris par un adversaire.
		if ((map.selectionne.unite == null)||(carre.unite != null)) {
			return false;
		}
		return (map.selectionne.unite.restdeplacement>=carre.terrain.deplacement);
	}

	/**
	 * Affiche les cases adjacentes libres pour un deplacement à partir de deplist
	 * 	
	 * @param jeu TODO
	 * @see #deplist
	 * 	
	 */	
	void render(Jeu jeu) { 
		Image green = new Image("greensquare.png", 50, 50, false, false);
		for(int i = 1; i<deplist.length;i++) {
			if (deplist[i] != -1) {
				int x = (deplist[i]%50)*50-(jeu.map.rangcorner%50)*50;
				int y = (deplist[i]/50)*50-(jeu.map.rangcorner/50)*50;
				jeu.gc.drawImage(green, x, y);
			}
		}
	}
	
	int deplacement() {
		if (deplacementencours) {
			map.selectionnemenu.unite.restdeplacement -= map.selectionne.terrain.deplacement; //on prend en compte le cout en dÃ©placement
			map.moveUnite(map.selectionnemenu,map.selectionne);
			map.selectionnemenu = map.selectionne;
			listUpdate();
			if (map.selectionnemenu.unite.restdeplacement == 0) { // on arrive Ã  0 dÃ©placements
				deplacementencours=false;
				return 0;
			}
		}
		else {
			listUpdate();
			deplacementencours=true;
		}
		return 1;
	}

	/**
	 * Met à jour la deplist pour la case selectionne
	 * 
	 * @see Map#selectionne
	 * @see #deplist	
	 * @param jeu TODO
	 * @see #MISSING()
	 * 	
	 */
	void listUpdate() {
		deplist = new int[5];
		for (int j=0;j<5;j++) {
			deplist[j]=-1;
		}
		deplist[0]=map.selectionne.rang;
		switch(map.selectionne.rang) { // cas particuliers : les coins 
		case 0: if (verifCase(map.plateau[1])){deplist[1]=1;} 
				if (verifCase(map.plateau[50])){deplist[2]=50;} 
				break;
		case 49: if (verifCase(map.plateau[48])){deplist[2]=48;} 
				 if (verifCase(map.plateau[99])){deplist[4]=99;}
				 break;
		case 2499: if (verifCase(map.plateau[2449])){deplist[3]=2449;} 
				   if (verifCase(map.plateau[2498])){deplist[2]=2498;} 
				   break;
		case 2450: if (verifCase(map.plateau[2400])){deplist[3]=2400;} 
				   if (verifCase(map.plateau[2451])){deplist[2]=2451;} 
				   break;
		default:
			int i = map.selectionne.rang;
			int r = i%50;
			int q = i/50;
			if (r==0) { // cas particuliers : les cotes 
				if (verifCase(map.plateau[i-50])){deplist[3]=i-50;} 
				if (verifCase(map.plateau[i+50])){deplist[4]=i+50;}
				if (verifCase(map.plateau[i+1])){deplist[1]=i+1;}
			} else if (r==49) {
				if (verifCase(map.plateau[i-50])){deplist[3]=i-50;} 
				if (verifCase(map.plateau[i+50])){deplist[4]=i+50;}
				if (verifCase(map.plateau[i-1])){deplist[1]=i-1;}
			} else if (q==0) {
				if (verifCase(map.plateau[i-1])){deplist[2]=i-1;} 
				if (verifCase(map.plateau[i+1])){deplist[1]=i+1;}
				if (verifCase(map.plateau[i+50])){deplist[4]=i+50;}
			} else if (q==49) {
				if (verifCase(map.plateau[i-1])){deplist[2]=i-1;} 
				if (verifCase(map.plateau[i+1])){deplist[1]=i+1;}
				if (verifCase(map.plateau[i-50])){deplist[3]=i-50;}
			} else { // Cas general
				if (verifCase(map.plateau[i+1])){deplist[1]=i+1;} 
				if (verifCase(map.plateau[i-1])){deplist[2]=i-1;}
				if (verifCase(map.plateau[i-50])){deplist[3]=i-50;} 
				if (verifCase(map.plateau[i+50])){deplist[4]=i+50;}
			}
		}
	}
}
