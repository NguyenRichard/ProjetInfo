package jeu;

import javafx.scene.image.Image;

public class Gestiondepl {
	/**Determine si un deplacement est en cours */
	boolean deplacementencours;
	/**Le rang des cases possibles pour un deplacement */
	int[] deplist;
	Map map;
	/** Objet permettant de convertir la matrice en graphe et vice-versa */
	CreateGrapheDepl cgd;
	/** Objet permettant de realiser l'algorithme de Dijkstra */
	Dijkstra dijkstra;
	/**Objet graphe */
	Graphe graphe;
	/**Premiere case de la matrice de déplacement*/
	int firstcase;
	/**Pour avoir le Joueur en train de jouer */
	Jeu jeu;	
	
	Image green;
	Image basdroite;
	Image basgauche;
	Image hautdroite;
	Image hautgauche;
	Image flechebas;
	Image flechehaut;
	Image flechedroite;
	Image flechegauche;
	Image droithoriz;
	Image droitverti;
	
	
	Gestiondepl(Map map,Jeu jeu){
		deplacementencours=false;
		this.map=map;
		this.jeu = jeu;
		green = new Image("greensquare.png", map.taillec, map.taillec, false, false);
		basdroite = new Image("fleche/bas-droite.png", map.taillec, map.taillec, false, false);
		basgauche = new Image("fleche/bas-gauche.png", map.taillec, map.taillec, false, false);
		hautdroite = new Image("fleche/haut-droite.png", map.taillec, map.taillec, false, false);
		hautgauche = new Image("fleche/haut-gauche.png", map.taillec, map.taillec, false, false);
		flechebas = new Image("fleche/fleche-bas.png", map.taillec, map.taillec, false, false);
		flechehaut = new Image("fleche/fleche-haut.png", map.taillec, map.taillec, false, false);
		flechedroite = new Image("fleche/fleche-droite.png", map.taillec, map.taillec, false, false);
		flechegauche = new Image("fleche/fleche-gauche.png", map.taillec, map.taillec, false, false);
		droithoriz = new Image("fleche/droit-horiz.png", map.taillec, map.taillec, false, false);
		droitverti = new Image("fleche/droit-verti.png", map.taillec, map.taillec, false, false);
	}

	/**
	 * Verifie si la case carre est libre pour un deplacement de l'unite selectionne
	 * 
	 * @param jeu TODO
	 * @param carre TODO
	 * @see Map#selectionne
	 */	
	boolean verifCase(Case carre) { // Doit aussi verifier si l'emplacement est pris par un adversaire.
		if ((map.selectionne.unite.joueur!=jeu.entrainjouer)) {
			return false;
		}
		return (map.selectionne.unite.restdeplacement>=carre.terrain.deplacement);
	}

	/**
	 * Affiche les cases adjacentes libres pour un deplacement a partir de deplist
	 * 	
	 * @param jeu TODO
	 * @see #deplist
	 * 	
	 */	
	void render(Jeu jeu) { 

		for(int i = 1; i<deplist.length;i++) {
			if (deplist[i] != -1) {
				int x = (deplist[i]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
				int y = (deplist[i]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
				jeu.gc.drawImage(green, x, y);
			}
		}
	}
	
	void arrowrender(Jeu jeu) {
		int center = jeu.map.selectionnemenu.rang;
		int cursor = jeu.map.selectionne.rang;
		if (cursor!=center) {
			
			// Plus court chemin entre curseur et centre
			int depl = map.selectionnemenu.unite.deplacement;
			cgd=new CreateGrapheDepl(depl,firstcase,center,50,map,jeu.entrainjouer);
			cgd.transforme();
			int newcenter = (center/50-firstcase/50)*(2*depl+1)+(center%50-firstcase%50);
			int newcursor = (cursor/50-firstcase/50)*(2*depl+1)+(cursor%50-firstcase%50);
			graphe= cgd.generate();
			dijkstra= new Dijkstra(graphe,newcenter,newcursor);
			dijkstra.runDijkstra();
			int[] chemin = dijkstra.chemin();
			chemin= cgd.reversechemin(chemin);
			
			for (int k=0;k<chemin.length;k++) {
				chemin[k]=(chemin[k]/(2*depl+1))*50+chemin[k]%(2*depl+1)+firstcase;
			}
			
			
			for (int i=1;i<chemin.length-1;i++) {
				if (((chemin[i-1]==chemin[i]-1)&&(chemin[i+1]==chemin[i]+50)) || ((chemin[i+1]==chemin[i]-1)&&(chemin[i-1]==chemin[i]+50))) {
					int x = (chemin[i]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
					int y = (chemin[i]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
					jeu.gc.drawImage(basgauche, x, y);
				} else if (((chemin[i-1]==chemin[i]+1)&&(chemin[i+1]==chemin[i]+50)) || ((chemin[i+1]==chemin[i]+1)&&(chemin[i-1]==chemin[i]+50))) {
					int x = (chemin[i]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
					int y = (chemin[i]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
					jeu.gc.drawImage(basdroite, x, y);
				} else if  (((chemin[i-1]==chemin[i]+1)&&(chemin[i+1]==chemin[i]-50)) || ((chemin[i+1]==chemin[i]+1)&&(chemin[i-1]==chemin[i]-50))) {
					int x = (chemin[i]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
					int y = (chemin[i]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
					jeu.gc.drawImage(hautdroite, x, y);
				} else if  (((chemin[i-1]==chemin[i]-1)&&(chemin[i+1]==chemin[i]-50)) || ((chemin[i+1]==chemin[i]-1)&&(chemin[i-1]==chemin[i]-50))) {
					int x = (chemin[i]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
					int y = (chemin[i]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
					jeu.gc.drawImage(hautgauche, x, y);
				} else if  (((chemin[i-1]==chemin[i]-1)&&(chemin[i+1]==chemin[i]+1)) || ((chemin[i+1]==chemin[i]-1)&&(chemin[i-1]==chemin[i]+1))) {
					int x = (chemin[i]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
					int y = (chemin[i]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
					jeu.gc.drawImage(droithoriz, x, y);
				} else if  (((chemin[i-1]==chemin[i]-50)&&(chemin[i+1]==chemin[i]+50)) || ((chemin[i+1]==chemin[i]-50)&&(chemin[i-1]==chemin[i]+50))) {
					int x = (chemin[i]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
					int y = (chemin[i]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
					jeu.gc.drawImage(droitverti, x, y);
				}
			}
			int n = chemin.length;
			if (chemin[n-1]==chemin[n-2]+50) {
				int x = (chemin[n-1]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
				int y = (chemin[n-1]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
				jeu.gc.drawImage(flechebas, x, y);
			} else if (chemin[n-1]==chemin[n-2]-50) {
				int x = (chemin[n-1]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
				int y = (chemin[n-1]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
				jeu.gc.drawImage(flechehaut, x, y);
			} else if (chemin[n-1]==chemin[n-2]+1) {
				int x = (chemin[n-1]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
				int y = (chemin[n-1]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
				jeu.gc.drawImage(flechedroite, x, y);
			} else if (chemin[n-1]==chemin[n-2]-1) {
				int x = (chemin[n-1]%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
				int y = (chemin[n-1]/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
				jeu.gc.drawImage(flechegauche, x, y);
			} 
			
		}
		
	}
	/**
	 * Verifie si le deplacement est en cours ou non pour et effectue le deplacement
	 * @return le booleen qui correspond a la prochaine valeur du menu.
	 */
	int deplacement() {
		if (deplacementencours) {
			if (map.selectionne.unite==null) {
				map.selectionnemenu.unite.restdeplacement = 0; //on prend en compte le cout en deplacement
				if (map.selectionnemenu.batiment != null) {
					if (map.selectionnemenu.batiment.entraincapture) {
						map.selectionnemenu.batiment.entraincapture=false;
						map.selectionnemenu.batiment.pv=100;
					}
				}
				map.moveUnite(map.selectionnemenu,map.selectionne);
				map.selectionnemenu = map.selectionne;
				listUpdate();
				if (map.selectionnemenu.unite.restdeplacement == 0) { // on arrive a 0 deplacements
					deplacementencours=false;
				}
			}
		}
		else {
			listUpdate();
			deplacementencours=true;
		}
		return 1;
	}

	/**
	 * Met a jour la deplist pour la case selectionne
	 * 
	 * @see Map#selectionne
	 * @see #deplist	
	 * @param jeu TODO
	 * @see #MISSING()
	 * 	
	 */
	void listUpdate() {
		
		int depl = map.selectionne.unite.deplacement; 
		int center = map.selectionne.rang;
		deplist = new int[(2*depl+1)*(2*depl+1)];
		
		if (center/50!=(center-depl)/50) { // cote gauche
			if ((center-50*depl)<0) { // coin haut gauche
				firstcase=0;
			} else if ((center+50*depl)>50*50-1) { // coin bas gauche
				firstcase=49*50;
			} else {firstcase=((center-depl*50)/50)*50;}
		}
		else if (center/50!=(center+depl)/50) { // cote droit
			if ((center-50*depl)<0) { // coin haut droite
				firstcase=50-2*depl;
			} else if ((center+50*depl)>50*50-1) { // coin bas droite
				firstcase=50*50-1-2*depl*51;
			} else {firstcase=center+(49-center%50)-2*depl+1-depl*50;}	
		}
		else if((center-50*depl)<0) { // cote haut
			firstcase=(center-depl)%50;
		}
		else if ((center+50*depl)>50*50-1) { // cote bas
			firstcase=50*(49-2*depl+1)+(center-depl)%50;
		}
		else {
			firstcase=((center/50)-depl)*50+center%50-depl;
		}
		
		cgd=new CreateGrapheDepl(depl,firstcase,center,50,map,jeu.entrainjouer);
		cgd.transforme();
		
		
		for (int j=0;j<deplist.length;j++) {
			deplist[j]=-1;
		}
		
		int newcenter = (center/50-firstcase/50)*(2*depl+1)+(center%50-firstcase%50);
		for (int j=0;j<2*depl+1;j++) {
			for (int k=0;k<2*depl+1;k++) {
				graphe= cgd.generate();
				dijkstra= new Dijkstra(graphe,newcenter,j*(2*depl+1)+k);
				int dist = dijkstra.runDijkstra();
				if ((dist <= depl)&&(verifCase(map.plateau[firstcase+j*50+k]))) {
					deplist[j*(2*depl+1)+k]=firstcase+j*50+k;
				}
			}
		}
		
	}
}
