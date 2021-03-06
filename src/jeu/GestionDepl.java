package jeu;

import javafx.scene.image.Image;
import terrain.Vide;

public class GestionDepl {
	/**Determine si un deplacement est en cours */
	boolean deplacementencours;
	/**Le rang des cases possibles pour un deplacement */
	int[] deplist;
	Carte carte;
	/** Objet permettant de convertir la matrice en graphe et vice-versa */
	GrapheDepl cgd;
	/** Objet permettant de realiser l'algorithme de Dijkstra */
	Dijkstra dijkstra;
	/**Objet graphe */
	Graphe graphe;
	/**Premiere case de la matrice de déplacement*/
	int firstcase;
	/**Pour avoir le Joueur en train de jouer */
	Jeu jeu;	
	
	//Images necessaires pour le deplacement
	Image bleu;
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
	
	
	GestionDepl(Carte carte,Jeu jeu){
		deplacementencours=false;
		this.carte=carte;
		this.jeu = jeu;
		bleu = new Image("bluesquare.png", carte.taillec, carte.taillec, false, false);
		basdroite = new Image("fleche/bas-droite.png", carte.taillec, carte.taillec, false, false);
		basgauche = new Image("fleche/bas-gauche.png", carte.taillec, carte.taillec, false, false);
		hautdroite = new Image("fleche/haut-droite.png", carte.taillec, carte.taillec, false, false);
		hautgauche = new Image("fleche/haut-gauche.png", carte.taillec, carte.taillec, false, false);
		flechebas = new Image("fleche/fleche-bas.png", carte.taillec, carte.taillec, false, false);
		flechehaut = new Image("fleche/fleche-haut.png", carte.taillec, carte.taillec, false, false);
		flechedroite = new Image("fleche/fleche-droite.png", carte.taillec, carte.taillec, false, false);
		flechegauche = new Image("fleche/fleche-gauche.png", carte.taillec, carte.taillec, false, false);
		droithoriz = new Image("fleche/droit-horiz.png", carte.taillec, carte.taillec, false, false);
		droitverti = new Image("fleche/droit-verti.png", carte.taillec, carte.taillec, false, false);
	}

	/**
	 * Verifie si la case carre est libre pour un deplacement de l'unite selectionne
	 * 
	 * @param jeu TODO
	 * @param carre TODO
	 * @see Carte#selectionne
	 */	
	boolean verifCase(Case carre) { // Doit aussi verifier si l'emplacement est pris par un adversaire.
		if ((carte.selectionne.unite.joueur!=jeu.entrainjouer)) {
			return false;
		}
		return !(carre.terrain instanceof Vide);
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
				if((jeu.updatemenu)||(jeu.carte.plateau[deplist[i]].unite!=null)||(jeu.carte.plateau[deplist[i]].batiment!=null)||(jeu.carte.plateau[deplist[i]].terrain.toString().compareTo("Eau")==0)) { //on render si on doit update ou si il y a un element sur la case
					int x = (deplist[i]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
					int y = (deplist[i]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
					jeu.gc.drawImage(bleu, x, y);
				}
			}
		}
	}
	
	void arrowrender(Jeu jeu) {
		int center = jeu.carte.selectionnemenu.rang;
		int cursor = jeu.carte.selectionne.rang;
		assert center < 2501;
		assert center >= 0;
		assert cursor < 2501;
		assert cursor >= 0;
		if (cursor!=center) {
			
			// Plus court chemin entre curseur et centre
			int depl = carte.selectionnemenu.unite.deplacement;
			boolean volant = carte.selectionnemenu.unite.volant;
			cgd=new GrapheDepl(depl,firstcase,center,50,carte,jeu.entrainjouer,volant);
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
				// Pour l'affichage de la fleche il est necessaire de tester tous les cas possibles pour toute les differentes position de la fleche
				if((jeu.updatemenu)||(jeu.carte.plateau[chemin[i]].unite!=null)||(jeu.carte.plateau[chemin[i]].batiment!=null)||(jeu.carte.plateau[chemin[i]].terrain.toString().compareTo("Eau")==0)) { //on render si on doit update ou si il y a unun element sur la case
					if (((chemin[i-1]==chemin[i]-1)&&(chemin[i+1]==chemin[i]+50)) || ((chemin[i+1]==chemin[i]-1)&&(chemin[i-1]==chemin[i]+50))) {
						int x = (chemin[i]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
						int y = (chemin[i]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
						jeu.gc.drawImage(basgauche, x, y);
					} else if (((chemin[i-1]==chemin[i]+1)&&(chemin[i+1]==chemin[i]+50)) || ((chemin[i+1]==chemin[i]+1)&&(chemin[i-1]==chemin[i]+50))) {
						int x = (chemin[i]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
						int y = (chemin[i]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
						jeu.gc.drawImage(basdroite, x, y);
					} else if  (((chemin[i-1]==chemin[i]+1)&&(chemin[i+1]==chemin[i]-50)) || ((chemin[i+1]==chemin[i]+1)&&(chemin[i-1]==chemin[i]-50))) {
						int x = (chemin[i]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
						int y = (chemin[i]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
						jeu.gc.drawImage(hautdroite, x, y);
					} else if  (((chemin[i-1]==chemin[i]-1)&&(chemin[i+1]==chemin[i]-50)) || ((chemin[i+1]==chemin[i]-1)&&(chemin[i-1]==chemin[i]-50))) {
						int x = (chemin[i]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
						int y = (chemin[i]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
						jeu.gc.drawImage(hautgauche, x, y);
					} else if  (((chemin[i-1]==chemin[i]-1)&&(chemin[i+1]==chemin[i]+1)) || ((chemin[i+1]==chemin[i]-1)&&(chemin[i-1]==chemin[i]+1))) {
						int x = (chemin[i]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
						int y = (chemin[i]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
						jeu.gc.drawImage(droithoriz, x, y);
					} else if  (((chemin[i-1]==chemin[i]-50)&&(chemin[i+1]==chemin[i]+50)) || ((chemin[i+1]==chemin[i]-50)&&(chemin[i-1]==chemin[i]+50))) {
						int x = (chemin[i]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
						int y = (chemin[i]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
						jeu.gc.drawImage(droitverti, x, y);
					}
				}
			}
			int n = chemin.length;
			if((jeu.updatemenu)||(jeu.carte.plateau[chemin[n-1]].unite!=null)||(jeu.carte.plateau[chemin[n-1]].batiment!=null)||(jeu.carte.plateau[chemin[n-1]].terrain.toString().compareTo("Eau")==0)) { //on render si on doit update ou si il y a un element sur la case
				if (chemin[n-1]==chemin[n-2]+50) {
					int x = (chemin[n-1]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
					int y = (chemin[n-1]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
					jeu.gc.drawImage(flechebas, x, y);
				} else if (chemin[n-1]==chemin[n-2]-50) {
					int x = (chemin[n-1]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
					int y = (chemin[n-1]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
					jeu.gc.drawImage(flechehaut, x, y);
				} else if (chemin[n-1]==chemin[n-2]+1) {
					int x = (chemin[n-1]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
					int y = (chemin[n-1]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
					jeu.gc.drawImage(flechedroite, x, y);
				} else if (chemin[n-1]==chemin[n-2]-1) {
					int x = (chemin[n-1]%50)*carte.taillec-(jeu.carte.rangcorner%50)*carte.taillec;
					int y = (chemin[n-1]/50)*carte.taillec-(jeu.carte.rangcorner/50)*carte.taillec;
					jeu.gc.drawImage(flechegauche, x, y);
				}
			}
			
		}
		
	}
	/**
	 * Verifie si le deplacement est en cours ou non pour et effectue le deplacement
	 * @return le booleen qui correspond a la prochaine valeur du menu.
	 */
	int deplacement() {
		if (deplacementencours) {
			if (carte.selectionne.unite==null) {
				carte.selectionnemenu.unite.restdeplacement = 0; //on prend en compte le cout en deplacement
				if (carte.selectionnemenu.batiment != null) {
					if (carte.selectionnemenu.batiment.entraincapture) {
						carte.selectionnemenu.batiment.entraincapture=false;
						carte.selectionnemenu.batiment.pv=100;
					}
				}
				carte.moveUnite(carte.selectionnemenu,carte.selectionne);
				carte.selectionnemenu = carte.selectionne;
				listUpdate();
				if (carte.selectionnemenu.unite.restdeplacement == 0) { // on arrive a 0 deplacements
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
	 * @see Carte#selectionne
	 * @see #deplist	
	 * @param jeu TODO
	 * @see #MISSING()
	 * 	
	 */
	void listUpdate() {
		
		int depl = carte.selectionne.unite.deplacement;
		boolean volant = carte.selectionnemenu.unite.volant;
		int center = carte.selectionne.rang;
		assert center < 2501;
		assert center >= 0;
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
		
		cgd=new GrapheDepl(depl,firstcase,center,50,carte,jeu.entrainjouer,volant);
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
				if ((dist <= depl)&&(verifCase(carte.plateau[firstcase+j*50+k]))) {
					deplist[j*(2*depl+1)+k]=firstcase+j*50+k;
				}
			}
		}
		
	}

	/**
	 * 
	 * @param e
	 * @param list
	 * @return true si l'entier e est dans la liste list, false sinon
	 */
	boolean inlist(int e, int[] list) {
		for (int i=0;i<list.length;i++) {
			if (list[i]==e) {
				return true;
			}
		} return false;
	}
}
