package jeu;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import son.Son;
import terrain.Montagne;
import terrain.Vide;

public class GestionAtq {
	/**La portee d'attaque maximale de l'unite en train d'attaquer */
	int portee;
	/**La portee d'attaque minimale de l'unite en train d'attaquer */
	int porteemin;
	/**Le rang de la case sur laquelle l'unite en train d'attaquer est situee */
	int rang;
	/**la carte de jeu*/
	Carte carte;
	/**Tableau des cases a portee de l'unite en train d'attaquer */
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
	/**Vrai si l'animation d'une attaque classique est en cours. Il faut alors ignorer les entrees clavier*/
	boolean animatqencours;
	/**Vrai si l'animation d'une attaque particuliere (zone ou ligne) est en cours. Il faut alors ignorer les entrees clavier*/
	boolean animatqencoursligne;
	/**entier indiquant ou on en ai dans l'animation des effets visuels d'attaque*/
	int animatq;
	/**Booleen indiquant une evolution des pvs lors d'une attaque (durant l'animation d'attaque)*/
	boolean pvendiminution;
	/**Entier qui donne la fin de l'animation de diminution des pv*/
	int pvfin;
	Image red;
	Image vert;
	Image viseur; // les differentes images a afficher liees a l'attaque
	Image viseursoin;
	Image viseurzone1;
	Image viseurzone2;
	Image viseurzonesoin;
	Son sd;
	
 	GestionAtq(Carte carte, Son sd){
		attaqueencours=false;
		animatqencoursligne=false;
		this.carte=carte;
		Im_deg = new Image("TestImpact.png",25,25,false,false);
		Im_hea = new Image("Heal.png",25,25,false,false);
		animatqencours=false;
		animatq=0;
		red = new Image("redsquare.png", carte.taillec, carte.taillec, false, false);
		vert = new Image("greensquare.png", carte.taillec, carte.taillec, false, false);
		viseur = new Image("viseur.png", carte.taillec, carte.taillec,false,false);
		viseurzone1 = new Image("ciblezone1.png", carte.taillec*3, carte.taillec*3,false,false);
		viseurzonesoin = new Image("ciblezonesoin.png", carte.taillec*3, carte.taillec*3,false,false);
		viseurzone2 = new Image("ciblezone2.png", carte.taillec*5, carte.taillec*5,false,false);
		viseursoin = new Image("viseursoin.png",carte.taillec, carte.taillec,false,false);
		portee = 0;
		porteemin=0;
		rang=0;
		this.sd = sd;
	}
	
	/**Application des degats a l'unite selectionnee. Suppression de l'unite en cas de degats lethaux et reset attaque si assassin */
	void prisedegat() {
		carte.selectionne.unite.pv--;

		if (carte.selectionne.unite.pv <= 0) {
			System.out.println("AA"+carte.selectionne.unite+carte.selectionne.batiment);
	    	carte.joueurs.get(carte.selectionne.unite.joueur).remove(carte.selectionne.unite); //on enleve l'unite de la liste d'unite du joueur
	    	carte.selectionne.unite=null;
	    	pvendiminution = false;
	    	if(carte.selectionne.batiment!=null && carte.selectionne.batiment.entraincapture) {
	    		carte.selectionne.batiment.pv=100;
	    		carte.selectionne.batiment.entraincapture=false;
	    	}
			if 	(carte.selectionnemenu.unite.type.compareTo("assassin")== 0) {
				carte.selectionnemenu.unite.valable=true;
				carte.selectionnemenu.unite.restdeplacement = carte.selectionnemenu.unite.deplacement;
				sd.runSoundrefresh();
			}
		}
	}
	
	
	/**
	 * Verifie si l'attaque est en cours ou non et effectue l'attaque :
	 * lance le son correspondant, enclenche la bonne animation et la prise de degat correspondante
	 * 
	 * @return le boleen qui sera la prochaine valeur du menu
	 * @see Jeu#menu
	 */
	int attaque(String type) {
		if(attaqueencours) {
			if (carte.selectionne!=carte.selectionnemenu) { 
				if (type.compareTo("ligne")==0) { // Ajouter condition attaque en ligne
						rang = carte.selectionnemenu.rang;
						portee = carte.selectionnemenu.unite.portee[1];
						porteemin = carte.selectionnemenu.unite.portee[0];
						sd.runSoundattack();
						animatqencoursligne=true;
				}
				else if (type.compareTo("zone1")==0) { // Pour attaque de zone (petite)
					rang = carte.selectionnemenu.rang;
					portee = carte.selectionnemenu.unite.portee[1];
					porteemin = carte.selectionnemenu.unite.portee[0];
					sd.runSoundattack();
					animatqencoursligne=true;
				}
				else if (type.compareTo("zone2")==0) { // Pour attaque de zone (grande)
					rang = carte.selectionnemenu.rang;
					portee = carte.selectionnemenu.unite.portee[1];
					porteemin = carte.selectionnemenu.unite.portee[0];
					sd.runSoundattack();
					animatqencoursligne=true;
				}
				else if (type.compareTo("zonesoin")==0) { // Pour soin de zone
					rang = carte.selectionnemenu.rang;
					portee = carte.selectionnemenu.unite.portee[1];
					porteemin = carte.selectionnemenu.unite.portee[0];
					sd.runSoundheal();
					animatqencoursligne=true;
				}
				else if (type.compareTo("soigneur")== 0) {
					// Son de soin
					sd.runSoundheal();
					animatqencours=true;
				}
				else { //cas par defaut
					//Son d'attaque
					sd.runSoundattack();
					animatqencours=true;
				}
				carte.selectionnemenu.unite.valable=false; //apres l'attaque l'unite n'est plus valable
			}
		attaqueencours=false;
		return 0;
		}
		else {
			if ((type.compareTo("soldat")==0)||(type.compareTo("ligne")!= 0)) {
				listcaseaportee(); //liste pour l'affichage de l'attaque
				if ((type.compareTo("zone1")*(type.compareTo("zone2")*(type.compareTo("zonesoin")))==0) && !atqlist.isEmpty()) {
					carte.selectionne = atqlist.get(0);
				}
			} else if (type.compareTo("ligne")== 0) {
				listcaseaporteeligne(); //liste pour l'affichage de l'attaque
			}
		listennemiaportee(); //liste pour la selection des adversaires
		attaqueencours=true;
		if(atqenemi.size()!=0) {carte.selectionne = atqenemi.get(0);}
		carte.adaptaffichage(carte.selectionne.rang); //si il y a un ennemi on le selectionne et on adapte l'affichage
		return 1;
		}
	
	}

	/**
	 * Met a jour la atqlist pour la case selectionne dans le cadre d'une attaque monocible ou en zone
	 * 
	 * @see Carte#selectionne
	 * @see #atqlist	
	 * @see #verifCase(Case)
	 * 	
	 */
	void listcaseaportee() {
		
		int rang = carte.selectionnemenu.rang;
		int portee = carte.selectionnemenu.unite.portee[1];
		if((carte.selectionnemenu.terrain instanceof Montagne)&&(carte.selectionnemenu.unite.portee[1]>1)) {
			portee += 1; //les unites a distance gagnent 1 de portée sur les montagnes
		}
	
		int porteemin = carte.selectionnemenu.unite.portee[0];
		//on va balayer le carre de cases comprenant les cases a portes d'attaque de l'unite selectionne pour attaquer
		int col = rang%50;
		int lign = rang/50;
		int coldeb = col - portee - 1;
		int ligndeb = lign - portee;
		if(coldeb<0) {coldeb=0;}; //on s'assure que le debut ne sort pas des limites de la carte
		if(ligndeb<0) {ligndeb=0;};
		int colfin = col + portee + 1;
		int lignfin = lign + portee;
		if(colfin>49) {colfin=49;}; //on s'assure que la fin ne sort pas des limites de la carte
		if(lignfin>49) {lignfin=49;};
	
		atqlist = new ArrayList<Case>();
		for (int k = coldeb + 50*ligndeb; k <= colfin + 50*lignfin; k++) { //on balaye du debut a la fin
			if(k%50==colfin) {
				k=coldeb + ((k/50)+1)*50; //si on atteint le bord droit du carre que l'on veut balayer on revient a la ligne
			
			} 
			if ((carte.selectionnemenu.distance(carte.plateau[k]) > porteemin) && (carte.selectionnemenu.distance(carte.plateau[k]) <= portee) && (verifCase(carte.plateau[k]))) {
				atqlist.add(carte.plateau[k]);
			}
				
		}
	}
	
	/**
	 * Met a jour la atqlist pour la case selectionne dans le cadre d'une attaque en ligne
	 * 
	 * @see Carte#selectionne
	 * @see #atqlist	
	 * @see #verifCase(Case)
	 * 	
	 */
	void listcaseaporteeligne() {
		int rang = carte.selectionnemenu.rang;
		int portee = carte.selectionnemenu.unite.portee[1];
		if((carte.selectionnemenu.terrain instanceof Montagne)&&(carte.selectionnemenu.unite.portee[1]>1)) {
			portee += 1; //les unites a distance gagnent 1 de portée sur les montagnes
		}
		int porteemin = carte.selectionnemenu.unite.portee[0];
		//on va balayer le carre de cases comprenant les cases a portes d'attaque de l'unite selectionne pour attaquer
		int col = rang%50;
		int lign = rang/50;
		int coldeb = col - portee - 1;
		int ligndeb = lign - portee;
		if(coldeb<0) {coldeb=0;}; //on s'assure que le debut ne sort pas des limites de la carte
		if(ligndeb<0) {ligndeb=0;};
		int colfin = col + portee + 1;
		int lignfin = lign + portee;
		if(colfin>49) {colfin=49;}; //on s'assure que la fin ne sort pas des limites de la carte
		if(lignfin>49) {lignfin=49;};
	
		atqlist = new ArrayList<Case>();
		// Ligne droite
		for (int k = col + 50*lign + porteemin; k <= col + 50*lign + portee; k++) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) && (carte.selectionnemenu.distance(carte.plateau[k]) > porteemin) && (carte.selectionnemenu.distance(carte.plateau[k]) <= portee) && (verifCase(carte.plateau[k]))) 
			{atqlist.add(carte.plateau[k]);}		
		}
		// Ligne gauche
		for (int k = col + 50*lign - portee; k <= col + 50*lign - porteemin; k++) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) &&(carte.selectionnemenu.distance(carte.plateau[k]) > porteemin) && (carte.selectionnemenu.distance(carte.plateau[k]) <= portee) && (verifCase(carte.plateau[k]))) 
			{atqlist.add(carte.plateau[k]);}		
		}
		// Ligne bas
		for (int k = col + 50*lign + porteemin*50; k <= col + 50*lign + portee*50; k=k+50) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) &&(carte.selectionnemenu.distance(carte.plateau[k]) > porteemin) && (carte.selectionnemenu.distance(carte.plateau[k]) <= portee) && (verifCase(carte.plateau[k]))) 
			{atqlist.add(carte.plateau[k]);}		
		}
		// Ligne haut
		for (int k = col + 50*lign - portee*50; k <= col + 50*lign - porteemin*50; k=k+50) { //on balaye du debut a la fin
			if ((k>=0) && (k<2500) &&(carte.selectionnemenu.distance(carte.plateau[k]) > porteemin) && (carte.selectionnemenu.distance(carte.plateau[k]) <= portee) && (verifCase(carte.plateau[k]))) 
			{atqlist.add(carte.plateau[k]);}		
		}
	}
	
	/**
	 * Met a jour atqenemi
	 * @see #atqenemi
	 */
	void listennemiaportee() {
		atqenemi = new 	ArrayList<Case>();
		for(Case cible: atqlist) {
			if (cible.unite != null) {
				if ((cible.unite.joueur==carte.selectionnemenu.unite.joueur)&&(carte.selectionnemenu.unite.type.compareTo("soigneur")== 0))
					atqenemi.add(cible);
				else if (cible.unite.joueur!=carte.selectionnemenu.unite.joueur) {
					atqenemi.add(cible);
				}
			}
		}
	}
	/**
	 * @param carre
	 * @return un boolean, vrai si on peut afficher le rouge d'attaque sur la case carre
	 */
	 boolean verifCase(Case carre) {
		 if (carte.selectionne.unite == null) {
			 return false;
		 }
		 else if ((carte.selectionne.unite.type.compareTo("soigneur")*carte.selectionne.unite.type.compareTo("zone1")*carte.selectionne.unite.type.compareTo("zone2")*(carte.selectionne.unite.type.compareTo("zone1"))) != 0) { //cas general (non soigneur)
			 if (carre.unite != null && (carte.selectionne.unite.joueur == carre.unite.joueur) ){ 
				return false;
			}
		 }
		 else if (carte.selectionne.unite.type.compareTo("soigneur") == 0){ //si l'unite est un soigneur il faut cibler les allies et non les ennemis
			 if (carre.unite != null && (carte.selectionne.unite.joueur != carre.unite.joueur) ){
				return false;
			}
		}
		
		return (! (carre.terrain instanceof Vide)); //Pour pour enlever le vide
	}

	/**
	 * Affiche un carre rouge pour les cases a portee et sans unite, et une cible sur les cible potentielles.
	 * Le fait a partir de atqlist
	 * @see #atqlist
	 */
	void rendercase(boolean updatemenu, GraphicsContext gc) {
		for(Case cible: atqlist) {
			if ((updatemenu)||(cible.unite!=null)||(cible.batiment!=null)||(cible.terrain.toString().compareTo("Eau")==0)) {
				int x = (cible.rang%50)*carte.taillec-(carte.rangcorner%50)*carte.taillec;
				int y = (cible.rang/50)*carte.taillec-(carte.rangcorner/50)*carte.taillec;
				if(carte.selectionnemenu.unite.type.compareTo("soigneur")*carte.selectionnemenu.unite.type.compareTo("zonesoin")==0) {
					gc.drawImage(vert, x, y);
				}
				else {gc.drawImage(red, x, y);}
				
			}
			
		}
	}
	
	/**Gestion de l'affichage de la cible en fonction du type de l'unite qui attaque
	 * 
	 */
	void rendercible(GraphicsContext gc) {
		if((carte.selectionnemenu.unite.type.compareTo("zone1")*carte.selectionnemenu.unite.type.compareTo("zone2")*(carte.selectionnemenu.unite.type.compareTo("zonesoin")))!=0) {
			for(Case cible: atqlist) {
				int x = (cible.rang%50)*carte.taillec-(carte.rangcorner%50)*carte.taillec;
				int y = (cible.rang/50)*carte.taillec-(carte.rangcorner/50)*carte.taillec;
				//si on est sur une unite ennemi :
				if(!(cible.unite == null)&&(cible.unite.joueur != carte.selectionnemenu.unite.joueur)) {gc.drawImage(viseur, x, y);
				}
				//si on est sur une unite alliee :
				else if(!(cible.unite == null)) {gc.drawImage(viseursoin, x, y);
				}
			}
		}
		else if (carte.selectionnemenu.unite.type.compareTo("zone1")==0) {
				int x = (carte.selectionne.rang%50 - carte.rangcorner%50)*carte.taillec;
				int y = (carte.selectionne.rang/50 - carte.rangcorner/50)*carte.taillec;
				gc.drawImage(viseurzone1, x-carte.taillec, y-carte.taillec);
		}
		else if (carte.selectionnemenu.unite.type.compareTo("zonesoin")==0) {
			int x = (carte.selectionne.rang%50 - carte.rangcorner%50)*carte.taillec;
			int y = (carte.selectionne.rang/50 - carte.rangcorner/50)*carte.taillec;
			gc.drawImage(viseurzonesoin, x-carte.taillec, y-carte.taillec);
		}
		else if (carte.selectionnemenu.unite.type.compareTo("zone2")==0) {
				int x = (carte.selectionne.rang%50 - carte.rangcorner%50)*carte.taillec;
				int y = (carte.selectionne.rang/50 - carte.rangcorner/50)*carte.taillec;
				gc.drawImage(viseurzone2, x-carte.taillec*2, y-carte.taillec*2);
			
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
				carte.selectionne = atqenemi.get(numenemi);
			}else {
				numenemi += 1;
				carte.selectionne = atqenemi.get(numenemi);
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
				carte.selectionne = atqenemi.get(numenemi);
			}else {
				numenemi -= 1;
				carte.selectionne = atqenemi.get(numenemi);
			}
			
		}
	}
	
	/**Gestion de l'animation d'attaque dans le cas de l'attaque en ligne
	 * 
	 * @return la liste des rangs des cases sur lesquelles l'animation des degats de la ligne a lieu (sert pour appliquer les degats)
	 */
	int[] animdegatligne(GraphicsContext gc) {
		int col = rang%50;
		int lign = rang/50;
		int[] listanim = new int[portee-porteemin];
		if ((carte.selectionne.rang%50==carte.selectionnemenu.rang%50)&&(carte.selectionne.rang/50>=carte.selectionnemenu.rang/50)) {
			// en bas
			int k = col + 50*lign + (porteemin+1)*50;
			for (int i = 0;i<portee-porteemin;i++) {
				if ((k>=0) &&(k<2500)) {
					listanim[i]=k;
					k=k+50;
				}
			}
		}
		else if ((carte.selectionne.rang%50==carte.selectionnemenu.rang%50)&&(carte.selectionne.rang/50<=carte.selectionnemenu.rang/50)) {
			// au dessus
			int k = col + 50*lign - portee*50;
			for (int i = 0;i<portee-porteemin;i++) {
				if ((k>=0) &&(k<2500)) {
					listanim[i]=k;
					k=k+50;
				}	
			}
		}
		else if ((carte.selectionne.rang%50>=carte.selectionnemenu.rang%50)&&(carte.selectionne.rang/50==carte.selectionnemenu.rang/50)) {
			// Ã  droite
			int k = col + 50*lign + porteemin+1;
			for (int i = 0;i<portee-porteemin;i++) {
				if ((k>=0) &&(k<2500)) {
					listanim[i]=k;
					k=k+1;
				}	
			}
		}
		else if ((carte.selectionne.rang%50<=carte.selectionnemenu.rang%50)&&(carte.selectionne.rang/50==carte.selectionnemenu.rang/50)) {
			// Ã  gauche
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
			x[i]=(listanim[i]%50 - carte.rangcorner%50)*carte.taillec;
			y[i]=(listanim[i]/50 - carte.rangcorner/50)*carte.taillec;
		}
		for (int i = 0;i<portee-porteemin;i++) {
			if (animatq<4) {gc.drawImage(Im_deg, x[i]+(carte.taillec/2), y[i]);}
			else if(animatq<8) {gc.drawImage(Im_deg, x[i], y[i]+(carte.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_deg, x[i], y[i]);}
			else if(animatq<16) {gc.drawImage(Im_deg, x[i]+(carte.taillec/2), y[i]+(carte.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_deg, x[i], y[i], carte.taillec, carte.taillec);;}
		}
		return listanim;
	}
	
	/**Gestion de l'animation d'attaque dans le cas de l'attaque en petite zone
	 * 
	 * @return la liste des rangs des cases sur lesquelles l'animation des degats a lieu (sert pour appliquer les degats)
	 */
	int[] animdegatzone1(GraphicsContext gc) {
		int[] listanim = new int[5];
		listanim = rangzone1(carte.selectionne.rang);
		int[] x = new int[5];
		int[] y = new int[5];
		for (int i = 0;i<5;i++) {
			x[i]=(listanim[i]%50 - carte.rangcorner%50)*carte.taillec;
			y[i]=(listanim[i]/50 - carte.rangcorner/50)*carte.taillec;
		}
		for (int i = 0;i<5;i++) {
			if (listanim[i]!=-1) {
				if (animatq<4) {gc.drawImage(Im_deg, x[i]+(carte.taillec/2), y[i]);}
				else if(animatq<8) {gc.drawImage(Im_deg, x[i], y[i]+(carte.taillec/2));}
				else if(animatq<12) {gc.drawImage(Im_deg, x[i], y[i]);}
				else if(animatq<16) {gc.drawImage(Im_deg, x[i]+(carte.taillec/2), y[i]+(carte.taillec/2));}
				else if (animatq<30) {gc.drawImage(Im_deg, x[i], y[i], carte.taillec, carte.taillec);;}
			}
		}
		return listanim;
	}
	
	/**Gestion de l'animation d'attaque dans le cas du soin en zone
	 * 
	 * @return la liste des rangs des cases sur lesquelles l'animation du soin a lieu (sert pour rendre les pvs)
	 */
	int[] animdegatzonesoin(GraphicsContext gc) {
		int[] listanim = new int[5];
		listanim = rangzone1(carte.selectionne.rang);
		int[] x = new int[5];
		int[] y = new int[5];
		for (int i = 0;i<5;i++) {
			x[i]=(listanim[i]%50 - carte.rangcorner%50)*carte.taillec;
			y[i]=(listanim[i]/50 - carte.rangcorner/50)*carte.taillec;
		}
		for (int i = 0;i<5;i++) {
			if (listanim[i]!=-1) {
				if (animatq<4) {gc.drawImage(Im_hea, x[i]+(carte.taillec/2), y[i]);}
				else if(animatq<8) {gc.drawImage(Im_hea, x[i], y[i]+(carte.taillec/2));}
				else if(animatq<12) {gc.drawImage(Im_hea, x[i], y[i]);}
				else if(animatq<16) {gc.drawImage(Im_hea, x[i]+(carte.taillec/2), y[i]+(carte.taillec/2));}
				else if (animatq<30) {gc.drawImage(Im_hea, x[i], y[i], carte.taillec, carte.taillec);;}
			}
		}
		return listanim;
	}
	
	/**Gestion de l'animation d'attaque dans le cas de l'attaque en grande zone
	 * 
	 * @return la liste des rangs des cases sur lesquelles l'animation des degats a lieu (sert pour appliquer les degats)
	 */
	int[] animdegatzone2(GraphicsContext gc) {
		int[] listanim = new int[13];
		listanim = rangzone2(carte.selectionne.rang);
		int[] x = new int[13];
		int[] y = new int[13];
		for (int i = 0;i<13;i++) {
			x[i]=(listanim[i]%50 - carte.rangcorner%50)*carte.taillec;
			y[i]=(listanim[i]/50 - carte.rangcorner/50)*carte.taillec;
		}
		for (int i = 0;i<13;i++) {
			if (listanim[i]!=-1) {
				if (animatq<4) {gc.drawImage(Im_deg, x[i]+(carte.taillec/2), y[i]);}
				else if(animatq<8) {gc.drawImage(Im_deg, x[i], y[i]+(carte.taillec/2));}
				else if(animatq<12) {gc.drawImage(Im_deg, x[i], y[i]);}
				else if(animatq<16) {gc.drawImage(Im_deg, x[i]+(carte.taillec/2), y[i]+(carte.taillec/2));}
				else if (animatq<30) {gc.drawImage(Im_deg, x[i], y[i], carte.taillec, carte.taillec);;}
			}
		}
		return listanim;
	}
	
	/**Gestion de l'animation d'attaque ou de soin pour les attaques et soins monocibles
	 * 
	 * @param type soigneur ou monocible
	 */
	void animdegat(String type, GraphicsContext gc) {
		int x = (carte.selectionne.rang%50 - carte.rangcorner%50)*carte.taillec;
		int y = (carte.selectionne.rang/50 - carte.rangcorner/50)*carte.taillec;
		if (type.compareTo("soigneur")!= 0){ //cas ou le type n'est pas soigneur, a changer si animation differente pour d'autres types que soigneur
			if (animatq<4) {gc.drawImage(Im_deg, x+(carte.taillec/2), y);}
			else if(animatq<8) {gc.drawImage(Im_deg, x, y+(carte.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_deg, x, y);}
			else if(animatq<16) {gc.drawImage(Im_deg, x+(carte.taillec/2), y+(carte.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_deg, x, y, carte.taillec, carte.taillec);;}
		}
		else if (type.compareTo("soigneur")== 0) { //cas ou le type est un soigneur. je laisse la condition a verifier au cas ou 
			if (animatq<4) {gc.drawImage(Im_hea, x+(carte.taillec/2), y);} //on fait le choix de mettre d'autres animations pour des autres types d'unites que soigneur
			else if(animatq<8) {gc.drawImage(Im_hea, x, y+(carte.taillec/2));}
			else if(animatq<12) {gc.drawImage(Im_hea, x, y);}
			else if(animatq<16) {gc.drawImage(Im_hea, x+(carte.taillec/2), y+(carte.taillec/2));}
			else if (animatq<30) {gc.drawImage(Im_hea, x, y, carte.taillec, carte.taillec);;}
		}
	}
	
	/**Declenche la bonne animation de degat en fonction du type de l'unite qui attaque pour les attaques de type zone
	 * puis lance l'animation de chute de pv correspondante
	 * 
	 * @see #animdegatligne(GraphicsContext)
	 * @see #animdegatzone1(GraphicsContext)
	 * @see #animdegatzone2(GraphicsContext)
	 * @see #animdegatzonesoin(GraphicsContext)
	 */
	void renderanimligne(GraphicsContext gc) {
		if(animatqencoursligne) { //Animation d'attaque
			int[] list = new int[0];
			if (carte.plateau[rang].unite.type.compareTo("ligne")== 0) {
				list = animdegatligne(gc);
			} else if  (carte.plateau[rang].unite.type.compareTo("zone1")== 0) {
				list = animdegatzone1(gc);
			} else if  (carte.plateau[rang].unite.type.compareTo("zonesoin")== 0) {
				list = animdegatzonesoin(gc);
			} else if  (carte.plateau[rang].unite.type.compareTo("zone2")== 0) {
				list = animdegatzone2(gc);
			}
	  	  	if(animatq<30) {animatq++;}
	  	  	else {
	  	  		//On enleve les pv :
	  	  		animatqencoursligne=false;
				animatq=0;
	  	  		for (int i = 0;i<list.length;i++) {
	  	  			if (list[i]!=-1) {
	  	  				if (carte.plateau[list[i]].unite != null) {
	  	  					carte.plateau[list[i]].unite.pv=Integer.min(Integer.max(carte.plateau[list[i]].unite.pv - carte.selectionnemenu.unite.dmg,0),carte.plateau[list[i]].unite.pvmax); //le max c'est pour la mort, le min c'est pour le cas du heal ne pas overheal
	  	  					animatqencours=false;
	  	  					if (carte.plateau[list[i]].unite.pv <= 0) {
	  	  						carte.joueurs.get(carte.plateau[list[i]].unite.joueur).remove(carte.plateau[list[i]].unite); //on enleve l'unite de la liste d'unite du joueur
	  	  						carte.plateau[list[i]].unite=null;
	  	  						if(carte.selectionne.batiment!=null && carte.selectionne.batiment.entraincapture) {
	  	  							carte.selectionne.batiment.pv=100;
	  	  							carte.selectionne.batiment.entraincapture=false;
	  	  						}
	  	  					}
	  	  				}
	  	  			}
	  	  		}
	  	  	carte.render(gc);
	  	  	}		
	    }
	}
	
	/**Declenche la bonne animation de degat en fonction du type de l'unite qui attaque pour les attaques monocible
	 * puis lance l'animation de chute de pv correspondante
	 * 
	 * @param gc
	 * @see #animdegat(String, GraphicsContext)
	 */
	void renderanim(GraphicsContext gc) {
		if(animatqencours) { //Animation d'attaque
	  	  animdegat(carte.selectionnemenu.unite.type, gc);
	  	  if(animatq<30) {animatq++;}
	  	  else {
	  		//On prepare la chute de pv :
	  		  pvfin =Integer.min(Integer.max(carte.selectionne.unite.pv - carte.selectionnemenu.unite.dmg,0),carte.selectionne.unite.pvmax); //le max c'est pour la mort, le min c'est pour le cas du heal ne pas overheal
	  		  animatq=0;
	  		  pvendiminution=true;
	  		  animatqencours=false;		
	  	  }		
	    }
	    
	    if(pvendiminution) { //Animation de chute des pv (ou augmentation pour soigneur)
	    	if (carte.selectionnemenu.unite.dmg < 0) { //cas du soigneur
	    		carte.selectionne.unite.pv ++;
	    		if(carte.selectionne.unite.pv>=pvfin) {
		  	  		pvendiminution=false;
		  	  	}
	    	}
	    	else {
		    	prisedegat();
		  	  	if ((pvfin !=0) && (carte.selectionne.unite.pv<=pvfin)) {
		  	  		pvendiminution=false;
		  	  	}
	    	}
	  	  	carte.render(gc);
	    }
	}
	
	/**@return le rang des cases touchees pour une attaque de type zone 1 a partir du rang du centre*/

	int[] rangzone1(int center) {
		int[] list = new int[5];
		for (int i=0;i<list.length;i++) {
			list[i]=-1;
		}
		list[0]=center;
		if ((center-50>=0)&&(center-50<2500)) {list[1]=center-50;}
		if ((center+50>=0)&&(center+50<2500)) {list[2]=center+50;}
		if ((center-1>=0)&&(center-1<2500)) {list[3]=center-1;}
		if ((center+1>=0)&&(center+1<2500)) {list[4]=center+1;}
		return list;
	}
	
	/**@return le rang des cases touchees pour une attaque de type zone 2 a partir du rang du centre*/
	int[] rangzone2(int center) {
		int[] list = new int[13];
		for (int i=0;i<list.length;i++) {
			list[i]=-1;
		}
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
	
	/**Permet d'alleger l'ecriture des conditions pour le deplacements du curseur continus dans les zones
	 * 
	 * 
	 * @param e le rang de la case a tester
	 * @return
	 */
	boolean deplacementzoneadjacente(int e) {
		return attaqueencours
		&& (carte.selectionnemenu.unite.type.compareTo("zone1")*carte.selectionnemenu.unite.type.compareTo("zone2")*carte.selectionnemenu.unite.type.compareTo("zonesoin")==0)
		&& e<2500 && e>=0 
		&& atqlist.contains(carte.plateau[e]);
	}
	
	/**Si on est en bordure d'une zone continue, change la case selectionne pour qu'elle corresponde a l'intuition
	 * 
	 * @param direction 0:droite 1:gauche 2:haut 3:bas
	 */
	void deplacementzonesaut(int direction) {
		if( attaqueencours && atqlist.size() != 0 &&
				carte.selectionnemenu.unite.type.compareTo("zone1")*carte.selectionnemenu.unite.type.compareTo("zone2")*carte.selectionnemenu.unite.type.compareTo("zonesoin")==0) {
			int position = atqlist.indexOf(carte.selectionne); //la position dans la liste de la case selectionne
			if (direction ==0) { //on selectionne la case suivante dans la liste si elle est a droite sur la meme ligne
				if ((atqlist.size()>position+1) && (atqlist.get(position+1).rang < carte.selectionne.rang + 50)) {
					carte.selectionne = atqlist.get(position+1);
				}
			}
			else if (direction ==1) { //on selectionne la case precedente dans la liste si elle est a gauche sur la meme ligne
				if ((position-1 >= 0) && (atqlist.get(position-1).rang > carte.selectionne.rang -50)) {
					carte.selectionne = atqlist.get(position-1);
				}
			}
			else if (direction ==2) { //on selectionne la premiere case precedente dans la liste qui est sur une ligne au dessus
				int k = 0; //entier qui sert a savoir si il y a un candidat ou non
				while(k==0 && position-1 >= 0) {
					position--;
					if ((atqlist.get(position).rang)%50 == (carte.selectionne.rang)%50){
						k=1;
					}
				}
				if(k==1) {
					carte.selectionne = atqlist.get(position);
				}
			}
			else if (direction ==3) { //on selectionne la premiere case suivante dans la liste qui est sur une ligne au dessous
				int k = 0; //entier qui sert a savoir si il y a un candidat ou non
				while(k==0 && atqlist.size()>position+1) {
					position++;
					if ((atqlist.get(position).rang)%50 == (carte.selectionne.rang)%50){
						k=1;
					}
				}
				if(k==1) {
					carte.selectionne = atqlist.get(position);
				}
			}
			
			carte.adaptaffichage(carte.selectionne.rang);
		}
				
	}
}
