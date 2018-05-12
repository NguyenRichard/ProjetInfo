package jeu;

import java.util.ArrayList;

import javax.sound.sampled.Clip;

import Sounds.Sound;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import terrain.Void;

public class Gestionatq {
	int portee;
	int porteemin;
	int rang;
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
	Image viseurzone1;
	Image viseurzone2;
	boolean animatqencoursligne;
	
 	Gestionatq(Map map){
		attaqueencours=false;
		animatqencoursligne=false;
		this.map=map;
		Im_deg = new Image("TestImpact.png",25,25,false,false);
		Im_hea = new Image("Heal.png",25,25,false,false);
		animatqencours=false;
		animatq=0;
		red = new Image("redsquare.png", map.taillec, map.taillec, false, false);
		viseur = new Image("viseur.png", map.taillec, map.taillec,false,false);
		viseurzone1 = new Image("ciblezone1.png", map.taillec*3, map.taillec*3,false,false);
		viseurzone2 = new Image("ciblezone2.png", map.taillec*5, map.taillec*5,false,false);
		viseursoin = new Image("viseursoin.png",map.taillec, map.taillec,false,false);
		portee = 0;
		porteemin=0;
		rang=0;
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
				if ((type==null)){
					// Son d'attaque
					Sound sd = new Sound();
					sd.runSoundattack();
					animatqencours=true;
				}
				else if (type.compareTo("ligne")==0) { // Ajouter condition attaque en ligne
						rang = map.selectionnemenu.rang;
						portee = map.selectionnemenu.unite.portee[1];
						porteemin = map.selectionnemenu.unite.portee[0];
						Sound sd = new Sound();
						sd.runSoundattack();
						animatqencoursligne=true;
				}
				else if (type.compareTo("zone1")==0) { // Pour attaque de zone (petite)
					rang = map.selectionnemenu.rang;
					portee = map.selectionnemenu.unite.portee[1];
					porteemin = map.selectionnemenu.unite.portee[0];
					Sound sd = new Sound();
					sd.runSoundattack();
					animatqencoursligne=true;
				}
				else if (type.compareTo("zone2")==0) { // Pour attaque de zone (grande)
					rang = map.selectionnemenu.rang;
					portee = map.selectionnemenu.unite.portee[1];
					porteemin = map.selectionnemenu.unite.portee[0];
					Sound sd = new Sound();
					sd.runSoundattack();
					animatqencoursligne=true;
				}
				else if (type.compareTo("healer")== 0) {
					// Son de soin
					Sound sd = new Sound();
					sd.runSoundheal();
					animatqencours=true;
				}
			}
			attaqueencours=false;
			return 0;
		}
		else {
			if ((type==null)||(type.compareTo("ligne")!= 0)) {
				listcaseaportee(); //liste pour l'affichage de l'attaque
			} else if (type.compareTo("ligne")== 0) {
				listcaseaporteeligne(); //liste pour l'affichage de l'attaque
			}
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
	
	void listcaseaporteeligne() {
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
		// Ligne droite
		for (int k = col + 50*lign + porteemin; k <= col + 50*lign + portee; k++) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) && (map.selectionnemenu.distance(map.plateau[k]) > porteemin) && (map.selectionnemenu.distance(map.plateau[k]) <= portee) && (verifCase(map.plateau[k]))) 
			{atqlist.add(map.plateau[k]);}		
		}
		// Ligne gauche
		for (int k = col + 50*lign - portee; k <= col + 50*lign - porteemin; k++) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) &&(map.selectionnemenu.distance(map.plateau[k]) > porteemin) && (map.selectionnemenu.distance(map.plateau[k]) <= portee) && (verifCase(map.plateau[k]))) 
			{atqlist.add(map.plateau[k]);}		
		}
		// Ligne bas
		for (int k = col + 50*lign + porteemin*50; k <= col + 50*lign + portee*50; k=k+50) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) &&(map.selectionnemenu.distance(map.plateau[k]) > porteemin) && (map.selectionnemenu.distance(map.plateau[k]) <= portee) && (verifCase(map.plateau[k]))) 
			{atqlist.add(map.plateau[k]);}		
		}
		// Ligne haut
		for (int k = col + 50*lign - portee*50; k <= col + 50*lign - porteemin*50; k=k+50) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) &&(map.selectionnemenu.distance(map.plateau[k]) > porteemin) && (map.selectionnemenu.distance(map.plateau[k]) <= portee) && (verifCase(map.plateau[k]))) 
			{atqlist.add(map.plateau[k]);}		
		}
	}
	
	void listennemiaportee() {
		atqenemi = new 	ArrayList<Case>();
		for(Case cible: atqlist) {
			if (cible.unite != null) {
				if ((cible.unite.joueur==map.selectionnemenu.unite.joueur)&&((map.selectionnemenu.unite.type!=null)&&(map.selectionnemenu.unite.type.compareTo("healer")== 0)))
					atqenemi.add(cible);
				else if (cible.unite.joueur!=map.selectionnemenu.unite.joueur) {
					atqenemi.add(cible);
				}
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
		 else if ((map.selectionne.unite.type == null)||(map.selectionne.unite.type.compareTo("healer") != 0)) { //cas pour les unites sans types
			 if (carre.unite != null && (map.selectionne.unite.joueur == carre.unite.joueur) ){ 
				return false;
			}
		 }
		 else if (map.selectionne.unite.type.compareTo("healer") == 0){ //si l'unite est un healer il faut cibler les allies et non les ennemis
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
			if ((map.selectionnemenu.unite.type!=null)&&(map.selectionnemenu.unite.type.compareTo("zone1")==0)) {
				if(!(cible.unite == null)&&(cible.unite.joueur != map.selectionnemenu.unite.joueur)) 
				{jeu.gc.drawImage(viseurzone1, x-map.taillec, y-map.taillec);}
			} else if ((map.selectionnemenu.unite.type!=null)&&(map.selectionnemenu.unite.type.compareTo("zone2")==0)) {
				if(!(cible.unite == null)&&(cible.unite.joueur != map.selectionnemenu.unite.joueur)) 
				{jeu.gc.drawImage(viseurzone2, x-map.taillec*2, y-map.taillec*2);}
			} else {
				//si on est sur une unite ennemi :
				if(!(cible.unite == null)&&(cible.unite.joueur != map.selectionnemenu.unite.joueur)) {jeu.gc.drawImage(viseur, x, y);
				}
				//si on est sur une unite alliee :
				else if(!(cible.unite == null)) {jeu.gc.drawImage(viseursoin, x, y); 
				}
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
	
	int[] animdegatligne(GraphicsContext gc) {
		int col = rang%50;
		int lign = rang/50;
		int[] listanim = new int[portee-porteemin];
		if ((map.selectionne.rang%50==map.selectionnemenu.rang%50)&&(map.selectionne.rang/50>=map.selectionnemenu.rang/50)) {
			// en bas
			int k = col + 50*lign + (porteemin+1)*50;
			for (int i = 0;i<portee-porteemin;i++) {
				if ((k>=0) &&(k<2500)) {
					listanim[i]=k;
					k=k+50;
				}
			}
		}
		else if ((map.selectionne.rang%50==map.selectionnemenu.rang%50)&&(map.selectionne.rang/50<=map.selectionnemenu.rang/50)) {
			// au dessus
			int k = col + 50*lign - portee*50;
			for (int i = 0;i<portee-porteemin;i++) {
				if ((k>=0) &&(k<2500)) {
					listanim[i]=k;
					k=k+50;
				}	
			}
		}
		else if ((map.selectionne.rang%50>=map.selectionnemenu.rang%50)&&(map.selectionne.rang/50==map.selectionnemenu.rang/50)) {
			// à droite
			int k = col + 50*lign + porteemin+1;
			for (int i = 0;i<portee-porteemin;i++) {
				if ((k>=0) &&(k<2500)) {
					listanim[i]=k;
					k=k+1;
				}	
			}
		}
		else if ((map.selectionne.rang%50<=map.selectionnemenu.rang%50)&&(map.selectionne.rang/50==map.selectionnemenu.rang/50)) {
			// à gauche
			int k = col + 50*lign - portee;
			for (int i = 0;i<portee-porteemin;i++) {
				if ((k>=0) &&(k<2500)) {
					listanim[i]=k;
					k=k+1;
				}	
			}
		}
		int[] x = new int[portee-porteemin];
		int[] y = new int[portee-porteemin];
		for (int i = 0;i<portee-porteemin;i++) {
			x[i]=(listanim[i]%50 - map.rangcorner%50)*map.taillec;
			y[i]=(listanim[i]/50 - map.rangcorner/50)*map.taillec;
		}
		for (int i = 0;i<portee-porteemin;i++) {
			if (animatq<4) {gc.drawImage(Im_deg, x[i]+(map.taillec/2), y[i]);}
			else if(animatq<8) {gc.drawImage(Im_deg, x[i], y[i]+(map.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_deg, x[i], y[i]);}
			else if(animatq<16) {gc.drawImage(Im_deg, x[i]+(map.taillec/2), y[i]+(map.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_deg, x[i], y[i], map.taillec, map.taillec);;}
		}
		return listanim;
	}
	
	int[] animdegatzone1(GraphicsContext gc) {
		int[] listanim = new int[5];
		listanim = rangzone1(map.selectionne.rang);
		int[] x = new int[5];
		int[] y = new int[5];
		for (int i = 0;i<5;i++) {
			x[i]=(listanim[i]%50 - map.rangcorner%50)*map.taillec;
			y[i]=(listanim[i]/50 - map.rangcorner/50)*map.taillec;
		}
		for (int i = 0;i<5;i++) {
			if (animatq<4) {gc.drawImage(Im_deg, x[i]+(map.taillec/2), y[i]);}
			else if(animatq<8) {gc.drawImage(Im_deg, x[i], y[i]+(map.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_deg, x[i], y[i]);}
			else if(animatq<16) {gc.drawImage(Im_deg, x[i]+(map.taillec/2), y[i]+(map.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_deg, x[i], y[i], map.taillec, map.taillec);;}
		}
		return listanim;
	}
	
	int[] animdegatzone2(GraphicsContext gc) {
		int[] listanim = new int[13];
		listanim = rangzone2(map.selectionne.rang);
		int[] x = new int[13];
		int[] y = new int[13];
		for (int i = 0;i<13;i++) {
			x[i]=(listanim[i]%50 - map.rangcorner%50)*map.taillec;
			y[i]=(listanim[i]/50 - map.rangcorner/50)*map.taillec;
		}
		for (int i = 0;i<13;i++) {
			if (animatq<4) {gc.drawImage(Im_deg, x[i]+(map.taillec/2), y[i]);}
			else if(animatq<8) {gc.drawImage(Im_deg, x[i], y[i]+(map.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_deg, x[i], y[i]);}
			else if(animatq<16) {gc.drawImage(Im_deg, x[i]+(map.taillec/2), y[i]+(map.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_deg, x[i], y[i], map.taillec, map.taillec);;}
		}
		return listanim;
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
	
	void renderanimligne(GraphicsContext gc) {
		if(animatqencoursligne) { //Animation d'attaque
			int[] list = animdegatligne(gc);;
			if (map.plateau[rang].unite.type.compareTo("ligne")== 0) {
				list = animdegatligne(gc);
			} else if  (map.plateau[rang].unite.type.compareTo("zone1")== 0) {
				list = animdegatzone1(gc);
			} else if  (map.plateau[rang].unite.type.compareTo("zone2")== 0) {
				list = animdegatzone2(gc);
			}
	  	  	if(animatq<30) {animatq++;}
	  	  	else {
	  	  		//On enlève les pv :
	  	  		animatqencoursligne=false;
	  	  		for (int i = 0;i<list.length;i++) {
	  	  			if (list[i]!=0) {
	  	  				if (map.plateau[list[i]].unite != null) {
	  	  					map.plateau[list[i]].unite.pv=Integer.min(Integer.max(map.plateau[list[i]].unite.pv - map.selectionnemenu.unite.dmg,0),map.plateau[list[i]].unite.pvmax); //le max c'est pour la mort, le min c'est pour le cas du heal ne pas overheal
	  	  					animatq=0;
	  	  					animatqencours=false;
	  	  					if (map.plateau[list[i]].unite.pv <= 0) {
	  	  						map.joueurs.get(map.plateau[list[i]].unite.joueur).remove(map.plateau[list[i]].unite); //on enleve l'unite de la liste d'unite du joueur
	  	  						map.plateau[list[i]].unite=null;
	  	  					}
	  	  				}
	  	  			}
	  	  		}
	  	  	map.render(gc);
	  	  	}		
	    }
		map.selectionnemenu.unite.valable=false; //apres l'animation d'attaque l'unite n'est plus valable
		
	    
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
			map.selectionnemenu.unite.valable=false; //apres l'animation d'attaque l'unite n'est plus valable
	  	  	map.render(gc);
	    }
	}
	
	int[] rangzone1(int center) {
		int[] list = new int[5];
		list[0]=center;
		if ((center-50>=0)&&(center-50<2500)) {list[1]=center-50;}
		if ((center+50>=0)&&(center+50<2500)) {list[2]=center+50;}
		if ((center-1>=0)&&(center-1<2500)) {list[3]=center-1;}
		if ((center+1>=0)&&(center+1<2500)) {list[4]=center+1;}
		return list;
	}
	
	int[] rangzone2(int center) {
		int[] list = new int[13];
		list[0]=center;
		if ((center-50>=0)&&(center-50<2500)) {list[1]=center-50;}
		if ((center+50>=0)&&(center+50<2500)) {list[2]=center+50;}
		if ((center-1>=0)&&(center-1<2500)) {list[3]=center-1;}
		if ((center+1>=0)&&(center+1<2500)) {list[4]=center+1;}
		if ((center-100>=0)&&(center-100<2500)) {list[5]=center-100;}
		if ((center+100>=0)&&(center+100<2500)) {list[6]=center+100;}
		if ((center-2>=0)&&(center-2<2500)) {list[7]=center-2;}
		if ((center+2>=0)&&(center+2<2500)) {list[8]=center+2;}
		if ((center-51>=0)&&(center-51<2500)) {list[9]=center-51;}
		if ((center+51>=0)&&(center+51<2500)) {list[10]=center+51;}
		if ((center-49>=0)&&(center-49<2500)) {list[11]=center-49;}
		if ((center+49>=0)&&(center+49<2500)) {list[12]=center+49;}
		return list;
	}
	
}
