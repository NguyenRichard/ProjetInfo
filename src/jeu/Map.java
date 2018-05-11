
package jeu;


import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import terrain.Terre;
import unit.Abeille;
import unit.AbeilleSamourai;
import unit.PapillonPsychique;
import unit.Scarabe;



public class Map {
	/** Tableau de case qui represente la carte du jeu*/
	Case[] plateau; 
	/**Image du curseur */
	Image curseur;
	/**Taille de la carte affichee, en ordonnee */
	int taillec;
	/**Rang dans la matrice plateau du coin haut gauche du cadre 12x12 cases delimitant l'affichage du jeu */
	int rangcorner;
	/**Case selectionee par le curseur */
	Case selectionne;
	/**Case selectionee avec l'unite lorsqu'on entre dans le menu */
	Case selectionnemenu;
	Image fond;
	int nombrecaseaffichee;
	/**Liste des joueurs avec leurs unit�s, batiments etc... */
	ArrayList<Joueur> joueurs;
	

/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/**
	 * <h3> Constructeur de Map: </h3>
	 *- (xcarte,ycarte) : La carte affichee est de 600x600pixels. <br/>
	 *- taillec : La taille d'une case est fixee a 50pixels. <br/>
	 *- La taille du plateau est fixee a 2500 cases. <br/>
	 *- Le rangcorner est initialise a 0. <br/>
	 *- (xcurseur ,ycurseur): initialises a (0,0)
	 */	
	Map(){
		taillec=75;
		nombrecaseaffichee = 10;
		fond = new Image("nuage.jpg",taillec*nombrecaseaffichee,taillec*nombrecaseaffichee,false,false);
		Case[] plateau = new Case[2501];
		rangcorner=0;
		for (int k = 0; k < plateau.length; k++) {
			// Boucle qui initialise les cases du plateau
			plateau[k] = new Case(taillec,k);
		}
		this.plateau = plateau;
		curseur = new Image("cursor2.png", taillec, taillec,false,false);
		selectionne = plateau[51];
		//initialisation des joueurs :
		joueurs = new ArrayList<Joueur>();
        for (int i = 0; i <= 3;i++) {
        	joueurs.add(new Joueur("sansnom"));
        }
	}
/*_Affichager des sprites___________________________________________________________________________________________________________ */
	
	/**
	 * <h3>Affichage des cases du plateau</h3>
	 * 
	 * @param gc <br/>
	 * 		Contexte graphique dans lequel on affiche<br/>
	 * 
	 * On reste dans la carte affichee: 600pixels en dessous et 600 pixels a droite de rangcorner.
	 *@see jeu.Map#kdefine
	 *Pour cela on utilise kdefine
	 */
	void render(GraphicsContext gc) {
		gc.drawImage(fond, 0, 0);
		for (int k = rangcorner; k <= rangcorner+(nombrecaseaffichee-1)*51; k++) {
			// Boucle qui affiche les cases du plateau de la carte affichee
			plateau[k].render(gc,rangcorner);
			k=kdefine(k);
		}
	}
	
	/**
	 * Cette fonction sert pour l'animation des sprites. Elle n'actualise que ces derniers.
	 * @param gc le contexte graphique
	 */
	void renderanim(GraphicsContext gc) {
		for (int k = rangcorner; k <= rangcorner+(nombrecaseaffichee-1)*51; k++) {
			// Boucle qui affiche les cases du plateau de la carte affichee
			if (plateau[k].unite != null) {
				plateau[k].render(gc,rangcorner);
				k=kdefine(k);
			}
		}
		curseurRender(gc); // Affichage du curseur
	}
	
/*_Rester dans la fenetre d'affichage_______________________________________________________________________________________________ */	
	
	/**
	 * 
	 * @param ko rang a controler
	 * @return ko si le rang de la case est dans le cadre a afficher ou alors, si on vient d'en sortir, la valeur du rang dans le cadre de la ligne 
	 * juste en dessous
	 * @see Map#render(GraphicsContext)
	 */
	int kdefine(int ko) {
		if (ko%50-rangcorner%50==(nombrecaseaffichee-1)) {
			return ko-nombrecaseaffichee+50;
		} else {
			return ko;
		}
	}

/*_Mettre a jour la position du curseur______________________________________________________________________________________________ */
	
	/**
	 * A utiliser pour changer l'affichage du curseur d'un rang vers la gauche. Modifie rangcorner plutot que xcurseur si on est a la limite de l'affichage.
	 * 
	 * @see Map#xcurseur 
	 * @see Map#rangcorner
	 */
	void leftcurseur() {
		if (selectionne.rang%50  - rangcorner%50 !=0)
			// Si on ne depasse pas le bord gauche de l'affichage de la carte, on decalle le curseur d'un cran vers la gauche
			selectionne=plateau[selectionne.rang-1];
		else if (rangcorner%50 > 0 ) {
			// Si on depasse ce bord et que l'on reste dans les dimensions du plateau de jeu, on decalle l'affichage d'un cran vers la gauche
			rangcorner-=1;
			selectionne=plateau[selectionne.rang-1];
		}
	}
	
	/**
	 * A utiliser pour changer l'affichage du curseur d'un rang vers la droite. Modifie rangcorner plutot que xcurseur si on est a la limite de l'affichage.
	 * 
	 * @see Map#xcurseur 
	 * @see Map#rangcorner
	 */
	void rightcurseur() {
		if (selectionne.rang%50 - rangcorner%50 != (nombrecaseaffichee-1)) 
			selectionne=plateau[selectionne.rang+1];
		else if (rangcorner%50 < 50-nombrecaseaffichee ) {
			rangcorner+=1;
			selectionne=plateau[selectionne.rang+1];
		}
	}
	
	/**
	 * A utiliser pour changer l'affichage du curseur d'un rang vers le haut. Modifie rangcorner plutot que ycurseur si on est a la limite de l'affichage.
	 * 
	 * @see Map#ycurseur 
	 * @see Map#rangcorner
	 */
	void upcurseur() {
		if (selectionne.rang/50 - rangcorner/50!=0)
			selectionne=plateau[selectionne.rang-50];
		else if (rangcorner/50 > 0 ) {
			rangcorner-=50;
			selectionne=plateau[selectionne.rang-50];
		}
	}
	
	/**
	 * A utiliser pour changer l'affichage du curseur d'un rang vers le bas. Modifie rangcorner plutot que ycurseur si on est a la limite de l'affichage.
	 * 
	 * @see Map#ycurseur 
	 * @see Map#rangcorner
	 */
	void downcurseur() {
		if (selectionne.rang/50 - rangcorner/50 != 9)
			selectionne=plateau[selectionne.rang+50];
		else if (rangcorner/50 < 50-nombrecaseaffichee ) {
			rangcorner+=50;
			selectionne=plateau[selectionne.rang+50];
		}
	}

	void moveUnite(Case a,Case b) {
		if (a != b) {
		b.unite = a.unite;
		a.unite = null;
		}
	}
	
	/*_Creation de la carte du jeu___________________________________________________________________________________________________ */
	/**
	 * Associe le terrain a la case de la map reperee par le rang.
	 */

    void addterrain(int rang, Terrain terrain) {
        plateau[rang].terrain=terrain;
    }
    
    /**
     * Fonction qui ajoute le batiment sur le rang "rang" de map.
     */
    void addbatiment(int rang, Batiment batiment) {
    	plateau[rang].batiment=batiment;
    }
    
    /**
     * Fonction qui ajoute l'unite "unite" sur le rang "rang" de map. 
     */
    void addunite(int rang, Unite unite) {
    		plateau[rang].unite=unite;
    }
    
    
    
    /*_Affichage du curseur___________________________________________________________________________________________________ */
    
    void curseurRender(GraphicsContext gc) {
    	int x = (selectionne.rang%50 - rangcorner%50)*taillec;
		int y = (selectionne.rang/50 - rangcorner/50)*taillec;
		gc.drawImage(curseur, x, y);
    }
    
    /*_Affichage equipe dans le terminal______________________________________________________________________________________ */
    
    void affichageEquipe() {
    	for (int k = 0; k < joueurs.size(); k++) {
	    	joueurs.get(k).printSituation();
	    }
		
    }
    
	    
    /**
     * 
     * @param rang a tester
     * @return vrai si le rang est compris dans la fenetre d'affichage et faux sinon. Utilise kdefine et rangcorner
     * 
     * @see #rangcorner
     * @see #kdefine(int)
     */
    boolean isShown(int rang) {
    	for (int k = rangcorner; k <= rangcorner+(nombrecaseaffichee-1)*51; k++) {
    		// test pour chaque case affiche
			if(k==rang) {
				return true;
			}
			k=kdefine(k);
		}
    	return false;
    }
	   
	/**
	 * Change rangcorner de maniere a afficher la case du rang cible au centre.
	 * @param rang
	 */
    void centre(int rang) {
    	int colcase = rang%50;
	   	int ligncase = rang/50;
	   	if(colcase<5) {colcase = 5;}
	   	if(colcase>43) {colcase = 43;}
	   	if(ligncase<5) {ligncase = 5;}
	   	if(ligncase>43) {ligncase = 43;}
	   	rangcorner = (colcase-5) + (ligncase-5)*50;
    }
    /**
     * Centre l'affichage sur la case du rang cible si cette derniere est en dehors de l'ecran
     * @param rang rang a tester
     */
    void adaptaffichage(int rang) {
    	if(!isShown(rang)){
    		centre(rang);
    	}
    }
    
    /**
     * On retire l'unit� de la liste d'unite du joueur indiqu�
     * @param unite
     * @param joueur
     */
    void delunite(Unite unite,int joueur) {
	    joueurs.get(joueur).remove(unite);
    }
	   
}



