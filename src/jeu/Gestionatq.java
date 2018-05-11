package jeu;

import java.util.ArrayList;

import javax.sound.sampled.Clip;

import Sounds.Sound;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import terrain.Void;

public class Gestionatq {
	
	Map map;
	private ArrayList<Case> atqlist;
	/**Tableau des cases a portee de l'unite sur la case selectionne et contenant une unite adverse,
	 *  sert a la selection de l'unite pour l'attaque */
	ArrayList<Case> atqenemi;
	/**indice dans le tableau atqenemi de l'unite enemi selectionne par le curseur, sert pour changer la cible */
	private int numenemi;
	/**Determine si une attaque est en cours */
	boolean attaqueencours;
	/**Image qui s'affichera en flashs lors d'une attaque */
	Image Im_deg;
	/**Image qui s'affichera en flashs lors d'un heal */
	Image Im_hea;
	boolean animatqencours;
	int animatq;
	/**Booleen indiquant une evolution des pvs lors d'une attaque*/
	boolean pvendiminution;
	/**Entier qui donne la fin de l'animation de diminution des pv*/
	int pvfin;
	Image red;
	Image viseur;
	Image viseursoin;
	
 	Gestionatq(Map map){
		attaqueencours=false;
		this.map=map;
		Im_deg = new Image("TestImpact.png",25,25,false,false);
		Im_hea = new Image("Heal.png",25,25,false,false);
		animatqencours=false;
		animatq=0;
		red = new Image("redsquare.png", map.taillec, map.taillec, false, false);
		viseur = new Image("viseur.png", map.taillec, map.taillec,false,false);
		viseursoin = new Image("viseursoin.png",map.taillec, map.taillec,false,false);
	}
	
	/**Application des degats a l'unite selectionnee. Suppression de l'unite en cas de degats lethaux */
	void prisedegat() {
		map.selectionne.unite.pv--;

		if (map.selectionne.unite.pv <= 0) {
	    	map.joueurs.get(map.selectionne.unite.joueur).remove(map.selectionne.unite); //on enleve l'unite de la liste d'unite du joueur
	    	map.selectionne.unite=null;
	    	pvendiminution = false;
		}
	}
	
	
	/**
	 * Verifie si l'attaque est en cours ou non et effectue l'attaque.
	 * @return le boleen qui sera la prochaine valeur du menu.
	 */
	int attaque(String type) {
		if(attaqueencours) {
			if (map.selectionne!=map.selectionnemenu) {
				if ((type==null)||(type.compareTo("healer")!= 0)){
					// Son d'attaque
					Sound sd = new Sound();
					sd.runSoundattack();
				}
				else if (type.compareTo("healer")== 0) {
					// Son de soin
					Sound sd = new Sound();
					sd.runSoundheal();
				}
				animatqencours=true;
			}
			attaqueencours=false;
			return 0;
		}
		else {
			listcaseaportee(); //liste pour l'affichage de l'attaque
			listennemiaportee(); //liste pour la selection des adversaires
			attaqueencours=true;
			if(atqenemi.size()!=0) {map.selectionne = atqenemi.get(0);}
			map.adaptaffichage(map.selectionne.rang); //si il y a un ennemi on le selectionne et on adapte l'affichage
			return 1;
			}
	}

	/**
	 * Met a jour la atqlist pour la case selectionne
	 * 
	 * @see Map#selectionne
	 * @see #atqlist	
	 * @param jeu TODO
	 * @see #verifCase(Case)
	 * 	
	 */
	void listcaseaportee() {
		
		int rang = map.selectionnemenu.rang;
		int portee = map.selectionnemenu.unite.portee[1];
		int porteemin = map.selectionnemenu.unite.portee[0];
		//on va balayer le carre de cases comprenant les cases a portes d'attaque de l'unite selectionne pour attaquer
		int col = rang%50;
		int lign = rang/50;
		int coldeb = col - portee - 1;
		int ligndeb = lign - portee;
		if(coldeb<0) {coldeb=0;}; //on s'assure que le debut ne sort pas des limites de la map
		if(ligndeb<0) {ligndeb=0;};
		int colfin = col + portee + 1;
		int lignfin = lign + portee;
		if(colfin>49) {colfin=49;}; //on s'assure que la fin ne sort pas des limites de la map
		if(lignfin>49) {lignfin=49;};
	
		atqlist = new ArrayList<Case>();
		for (int k = coldeb + 50*ligndeb; k <= colfin + 50*lignfin; k++) { //on balaye du debut a la fin
			if(k%50==colfin) {
				k=coldeb + ((k/50)+1)*50; //si on atteint le bord droit du carre que l'on veut balayer on revient a la ligne
			
			} 
			if ((map.selectionnemenu.distance(map.plateau[k]) > porteemin) && (map.selectionnemenu.distance(map.plateau[k]) <= portee) && (verifCase(map.plateau[k]))) {
				atqlist.add(map.plateau[k]);
			}
				
		}
	}
	void listennemiaportee() {
		atqenemi = new 	ArrayList<Case>();
		for(Case cible: atqlist) {
			if (cible.unite != null) {
				atqenemi.add(cible);
			}
		}
	}
	/**
	 * 
	 * @param carre
	 * @return un boolean, vrai si on peut afficher le rouge d'attaque sur la case carre
	 */
	 boolean verifCase(Case carre) {
		 if (map.selectionne.unite == null) {
			 return false;
		 }
		 else if (map.selectionne.unite.type == null) { //cas pour les unit�s sans types
			 if (carre.unite != null && (map.selectionne.unite.joueur == carre.unite.joueur) ){ 
				return false;
			}
		 }
		 else if (map.selectionne.unite.type.compareTo("healer") == 0){ //si l'unit� est un healer il faut cibler les alli�s et non les ennemis
			 	if (carre.unite != null && (map.selectionne.unite.joueur != carre.unite.joueur) ){
					return false;
				}
			 }
		return (! (carre.terrain instanceof Void)); //Pour pour enlever le vide
	}

	/**
	 * Affiche un carre rouge pour les cases a portee et sans unite, et une cible sur les cible potentielles.
	 * Le fait a partir de atqlist
	 * @param jeu TODO
	 * @see #atqlist
	 */
	
	void rendercase(Jeu jeu) {
		for(Case cible: atqlist) {
			int x = (cible.rang%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
			int y = (cible.rang/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
			if(cible.unite == null){
				jeu.gc.drawImage(red, x, y);
			}
		}
	}
	
	void rendercible(Jeu jeu) {
		for(Case cible: atqlist) {
			int x = (cible.rang%50)*map.taillec-(jeu.map.rangcorner%50)*map.taillec;
			int y = (cible.rang/50)*map.taillec-(jeu.map.rangcorner/50)*map.taillec;
			//si on est sur une unite ennemi :
			if(!(cible.unite == null)&&(cible.unite.joueur != map.selectionnemenu.unite.joueur)) {jeu.gc.drawImage(viseur, x, y);
			}
			//si on est sur une unite alliee :
			else if(!(cible.unite == null)) {jeu.gc.drawImage(viseursoin, x, y); 
			}
		}
	}
	
	/**
	 * changement de la cible dans la liste des ennemis a portee.
	 * 
	 * @see #atqenemi
	 */
	void upenemi() {
		if (atqenemi.size() != 1) {
			if(numenemi == atqenemi.size() -1) {
				numenemi = 0;
				map.selectionne = atqenemi.get(numenemi);
			}else {
				numenemi += 1;
				map.selectionne = atqenemi.get(numenemi);
			}
		}
	}
	
	/**
	 * changement de la cible dans la liste des ennemis a portee.
	 * 
	 * @see #atqenemi
	 */
	void downenemi() {
		if (atqenemi.size() != 1) {
			if(numenemi == 0) {
				numenemi = atqenemi.size() -1;
				map.selectionne = atqenemi.get(numenemi);
			}else {
				numenemi -= 1;
				map.selectionne = atqenemi.get(numenemi);
			}
			
		}
	}
	
	
	
	void animdegat(String type, GraphicsContext gc) {
		int x = (map.selectionne.rang%50 - map.rangcorner%50)*map.taillec;
		int y = (map.selectionne.rang/50 - map.rangcorner/50)*map.taillec;
		if ((type==null)||(type.compareTo("healer")!= 0)){ //cas ou le type n'est pas healer, a changer si animation differente pour d'autres types que healer
			if (animatq<4) {gc.drawImage(Im_deg, x+(map.taillec/2), y);}
			else if(animatq<8) {gc.drawImage(Im_deg, x, y+(map.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_deg, x, y);}
			else if(animatq<16) {gc.drawImage(Im_deg, x+(map.taillec/2), y+(map.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_deg, x, y, map.taillec, map.taillec);;}
		}
		else if (type.compareTo("healer")== 0) { //cas ou le type est un healer
			if (animatq<4) {gc.drawImage(Im_hea, x+(map.taillec/2), y);}
			else if(animatq<8) {gc.drawImage(Im_hea, x, y+(map.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_hea, x, y);}
			else if(animatq<16) {gc.drawImage(Im_hea, x+(map.taillec/2), y+(map.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_hea, x, y, map.taillec, map.taillec);;}
		}
		
	}
	
	void renderanim(GraphicsContext gc) {
		if(animatqencours) { //Animation d'attaque
	  	  animdegat(map.selectionnemenu.unite.type, gc);
	  	  if(animatq<30) {animatq++;}
	  	  else {
	  		//On prepare la chute de pv :
	  		  pvfin =Integer.min(Integer.max(map.selectionne.unite.pv - map.selectionnemenu.unite.dmg,0),map.selectionne.unite.pvmax); //le max c'est pour la mort, le min c'est pour le cas du heal ne pas overheal
	  		  animatq=0;
	  		  pvendiminution=true;
	  		  animatqencours=false;		
	  	  }		
	    }
	    
	    if(pvendiminution) { //Animation de chute des pv (ou augmentation pour healer)
	    	if (map.selectionnemenu.unite.dmg < 0) { //cas du healer
	    		map.selectionne.unite.pv ++;
	    		if(map.selectionne.unite.pv>=pvfin) {
		  	  		pvendiminution=false;
		  	  	}
	    	}
	    	else {
			
		    	prisedegat();
		  	  	if ((pvfin !=0) && (map.selectionne.unite.pv<=pvfin)) {
		  	  		pvendiminution=false;
		  	  	}
	    	}
			map.selectionnemenu.unite.valable=false; //apr�s l'animation d'attaque l'unit� n'est plus valable
	  	  	map.render(gc);
	    }
	}
	
}
