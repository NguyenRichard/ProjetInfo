
package jeu;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import batiments.Crystal;
import batiments.Portail;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import terrain.*;
import terrain.Void;
import unite.Abeille;
import unite.AbeilleSamourai;
import unite.ArcherSquelette;
import unite.CraneVolant;
import unite.EpeisteVolant;
import unite.Fourmis;
import unite.Moustique;
import unite.PapillonPsychique;
import unite.Scarabe;
import unite.SkeletonSoldier;
import unite.TankSquelette;
import unite.ZombieMineur;



public class Carte {
	/**Tableau de case qui represente la carte du jeu*/
	Case[] plateau; 
	/**Image du curseur */
	Image curseur;
	/**Image signalant le gagnant quand la partie est finie */
	Image finpartie;
	/**Si cet entier est non egal a 0 alors la partie est fini et le joueur gagnant est le joueur portant ce nombre */
	int ecranfin;
	/**Taille de la carte affichee, en ordonnee */
	int taillec;
	/**Rang dans la matrice plateau du coin haut gauche du cadre 10x10 cases delimitant l'affichage du jeu */
	int rangcorner;
	/**Case selectionee par le curseur */
	Case selectionne;
	/**Case selectionee par le curseur au moment ou on entre dans le menu action (attaque/deplacement/capture) */
	Case selectionnemenu;
	/**Image affichee en fond, que l'on voit lorsque les cases sont depourvues de terrain*/
	Image fond;
	/**Entier permettant de controler le nombre de cases affichees (par exemple pour 10 on affiche 10x10 cases a la fois.
	 * Nous n'avons neanmoins pas prevu de le bouger regulierement donc il est possible que pour garder un affichage correct
	 * d'une taille differentes quelques ajustements supplementaires soient necessaires
	 */
	int nombrecaseaffichee;
	/**Liste des joueurs avec leurs unites, batiments, ressources et noms*/
	ArrayList<Joueur> joueurs;
	/**Tableau d'element permettant de faire le lien entre le code de l'element et le type de terrain au moment ou
	 * la carte est reconstruite a partir du tableau d'entier de la sauvegarde */
	ArrayList<Terrain> referencecodeterrain;
	/**Vaut null la plupart du temps sauf a l'instant ou un joueur vient de perdre. A ce moment prend le nom de ce joueur
	 * afin de permettre un affichage personnalise de sa defaite*/
	String nomperdant;
	/**Timer pour l'affichage de la mort d'un joueur */
	int affichageperdu;
	

/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/**
	 * <h3> Constructeur de Map: </h3>
	 *- (xcarte,ycarte) : La carte affichee est de 600x600pixels. <br/>
	 *- taillec : La taille d'une case est fixee a 50pixels. <br/>
	 *- La taille du plateau est fixee a 2500 cases. <br/>
	 *- Le rangcorner est initialise a 0. <br/>
	 *- (xcurseur ,ycurseur): initialises a (0,0)
	 */	
	Carte(){
		
		taillec=75;
		
	/*~~~~~~TABLE REFERENCE CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
		referencecodeterrain = new ArrayList<Terrain>();
		referencecodeterrain.add(new Void(taillec)); //permet de faire le lien entre code de la sauvegarde et l'element
		referencecodeterrain.add(new Terre(taillec)); // /!\laisser void en premier !
		referencecodeterrain.add(new Marais(taillec)); //il faut la modifier en ajoutant la ligne correspondante lorsqu'on veut ajouter un nouveau type de terrain au jeu
		referencecodeterrain.add(new Eau(taillec)); // c'est le seul endroit qu'il est necessaire de modifier de la sorte dans le cas des terrains
		referencecodeterrain.add(new Sable(taillec));
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
		
		nombrecaseaffichee = 10;
		fond = new Image("ciel.png",taillec*nombrecaseaffichee,taillec*nombrecaseaffichee,false,false);
		finpartie =  new Image("victoire.png",250,100,false,false);
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
                for (int i = 0; i <= 4;i++) {
        	        joueurs.add(new Joueur("sansnom"));
  	        }
                ecranfin = 0;
	        nomperdant=null;
	}
/*_Affichager des sprites___________________________________________________________________________________________________________ */
	
	/**
	 * <h3>Affichage des cases du plateau</h3>
	 * 
	 * Le cadre de cases a afficher est ici 10x10 cases. On peut modifier la taille via carte.nombrecaseaffichee.
	 * 
	 * @param gc
	 * 		Contexte graphique dans lequel on affiche l'interface visuelle
	 * 
	 *@see jeu.Carte#kdefine
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
	 * Cette fonction sert pour l'animation des sprites des elements lorsqu'ils en ont. Elle n'actualise que ces derniers.
	 * On evite ainsi de reafficher toutes les cases du plateau inutilement.
	 * @param gc Contexte graphique dans lequel on affiche l'interface visuelle
	 */
	void renderanim(GraphicsContext gc) {
		for (int k = rangcorner; k <= rangcorner+(nombrecaseaffichee-1)*51; k++) {
			// Boucle qui affiche les cases du plateau de la carte affichee
			if ((plateau[k].unite != null)||(plateau[k].batiment != null)||(plateau[k].terrain instanceof Eau)) {
				plateau[k].render(gc,rangcorner);
				k=kdefine(k);
			}
			if(ecranfin!=0) { //on affiche par dessus les sprites l'ecran de fin si la partie est finie
	 			gc.drawImage(finpartie, taillec*(nombrecaseaffichee/2-1), taillec*(nombrecaseaffichee/2-1));
	 			String tour = "Gagnant: " + joueurs.get(ecranfin);
		        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		        gc.setLineWidth(1);
		        gc.setFill(Color.YELLOW);
		        gc.setStroke(Color.BLACK);
		        gc.fillText(tour, taillec*(nombrecaseaffichee/2-1)+20, taillec*(nombrecaseaffichee/2-1)+75);
		        gc.strokeText(tour, taillec*(nombrecaseaffichee/2-1)+20, taillec*(nombrecaseaffichee/2-1)+75 );
	 		}
		}
		curseurRender(gc); // Affichage du curseur a la fin pour qu'il reste visible en toute circonstance
	}
	
/*_Rester dans la fenetre d'affichage_______________________________________________________________________________________________ */	
	
	/**
	 * 
	 * @param ko rang a controler
	 * @return ko si le rang de la case est dans le cadre a afficher ou alors, si on vient d'en sortir, la valeur du rang dans le cadre de la ligne 
	 * juste en dessous
	 * @see Carte#render(GraphicsContext)
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
	 * De cette maniere on change les cases affichees dans le cadre et on a la possibilite visualiser l'ensemble des cases.
	 * @see Carte#xcurseur 
	 * @see Carte#rangcorner
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
	 * De cette maniere on change les cases affichees dans le cadre et on a la possibilite visualiser l'ensemble des cases.
	 * @see Carte#xcurseur 
	 * @see Carte#rangcorner
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
	 * De cette maniere on change les cases affichees dans le cadre et on a la possibilite visualiser l'ensemble des cases
	 * @see Carte#ycurseur 
	 * @see Carte#rangcorner
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
	 * De cette maniere on change les cases affichees dans le cadre et on a la possibilite visualiser l'ensemble des cases
	 * @see Carte#ycurseur 
	 * @see Carte#rangcorner
	 */
	void downcurseur() {
		if (selectionne.rang/50 - rangcorner/50 != 9)
			selectionne=plateau[selectionne.rang+50];
		else if (rangcorner/50 < 50-nombrecaseaffichee ) {
			rangcorner+=50;
			selectionne=plateau[selectionne.rang+50];
		}
	}

	/**deplace l'unite de la case a vers la case b
	 * 
	 * @param a case de depart
	 * @param b case d'arrive
	 */
	void moveUnite(Case a,Case b) {
		if (a != b) {
		b.unite = a.unite;
		a.unite = null;
		}
	}
	
	/*_Creation de la carte du jeu___________________________________________________________________________________________________ */
	/** Associe le terrain a la case de la carte reperee par le rang.
	 */

    void addterrain(int rang, Terrain terrain) {
        plateau[rang].terrain=terrain;
    }
    
    /**Fonction qui ajoute le batiment sur le rang "rang" de la carte.
     */
    void addbatiment(int rang, Batiment batiment) {
    	plateau[rang].batiment=batiment;
    }
    
    /**
     * Fonction qui ajoute l'unite "unite" sur le rang "rang" de la carte. 
     */
    void addunite(int rang, Unite unite) {
    		plateau[rang].unite=unite;
    }
    
    
    
    /*_Affichage du curseur___________________________________________________________________________________________________ */
    
    /**Affichage du curseur au bon endroit dans le cadre de cases affichees. La position est determinee grace a rang corner et
     * au rang de la case selectionne
     * 
     * @param gc Contexte graphique dans lequel on affiche l'interface visuelle
     * @see Carte#rangcorner
     * @see Case#rang
     */
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
	 * Change rangcorner de maniere a afficher la case du rang cible au centre du cadre de cases affichees.
	 * @param rang le rang de la case a centrer
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
     * @param rang rang a tester et a centrer
     */
    void adaptaffichage(int rang) {
    	if(!isShown(rang)){
    		centre(rang);
    	}
    }
    
    /**
     * On retire l'unite de la liste d'unite du joueur indique
     * @param unite unite a retirer
     * @param joueur joueur possedant l'unite
     */
    void delunite(Unite unite,int joueur) {
	    joueurs.get(joueur).remove(unite);
    }
    
    /**
     * On retire le batiment de la liste d'unite du joueur indique
     * @param casebatiment la case contenant le batiment a retirer
     * @param joueur joueur possedant l'unite
     */
    void delbatiment(Case casebatiment,int joueur) {
    	joueurs.get(joueur).remove(casebatiment);
    }

    /**
     * Cette fonction est a executer lorsqu'un joueur a perdu. Elle rend ses batiments neutre et le signal mort.
     * Elle permet aussi de detecter si la partie est fini et dans ce cas l'entier qu'elle renvoie est different de
     * 0 et represente le joueur gagnant
     * @param joueur le joueur mort
     * @return 0 si la partie continue et le numero du joueur gagnant sinon
     */
	public void perdu(int joueur) {
			System.out.println(joueur+" a perdu !");
			Joueur perdant = joueurs.get(joueur);
			int res = 0;
			int i = 0;
            while( i<2500 && perdant.possedeunite()) {
                if((plateau[i].unite != null)&&(plateau[i].unite.joueur == joueur)) {
                    delunite(plateau[i].unite,joueur);
                    plateau[i].unite = null;
                }
                i++;
            }
			while(perdant.possedebatiment()) {
				Case casebatiment = perdant.possessions.get(perdant.possessions.size()-1);
				casebatiment.batiment.joueur=0;
				perdant.possessions.remove(perdant.possessions.size()-1);
			}
			perdant.isalive=false;
			int j = 0;
			for(int k=1; k<=4;k++){
				if ((joueurs.get(k).isalive)){
					j++;
					res = k;
				}
			}
			if (j==1) {
				System.out.println("gagné ");
				ecranfin=res;
			}
			else {
				nomperdant=perdant.toString();
			}
			
	}

	/**gestion de la creation d'un joueur si le code du rang k contient un numero
	 * de joueur encore non rencontre au moment de la reconstruction de la carte a partir du code
	 * C'est aussi cette fonction qui gere la demande du nom et le choix de l'armee du joueur au lancement d'une partie
	 * 
	 * <p><font color="red">il faut absolument le faire avant remakeunite et remaketerrain dans le cadre du debut d'une partie !</font></p>
	 * -> pour eviter les erreurs on preferera utiliser directement remakecarte
	 * 
	 * @param codeS la valeur du tableau d'entier de la sauvegarde correspondant a l'etat de la case en question
	 * @see #remakecarte(File, boolean)
	 * 
	 */
	void remakejoueur(int codeS) {
		FXDialogs fx = new FXDialogs();
		int joueurunite = (codeS/(50*50))%50;
		if ((!(joueurs.get(joueurunite).isalive))&&(joueurunite != 0)){ //si le joueur n'est pas en vie lors de la creation c'est qu'il n'a pas encore ete personnalise
			joueurs.get(joueurunite).isalive = true;
			//Pour entrer des String depuis la console
			@SuppressWarnings("static-access")
			String str = fx.showTextInput("nom de joueur", "Veuillez saisir le nom du joueur " + joueurunite +  " :",  "King Arthur");
			joueurs.get(joueurunite).changename(str);
			@SuppressWarnings("static-access")
			String str2 = fx.showConfirm("nom de joueur", "Veuillez saisir une armee de "+joueurs.get(joueurunite)+ " :", "Armee d'insectes", "Armee des morts", "Armee myhtologique chinoise");
			int dec=0; 
			if (str2 == "Armee d'insectes") {
				dec = 0;
			} else if (str2 == "Armee des morts") {
				dec = 2;
			} else {
				dec = 1;
			}
			joueurs.get(joueurunite).typearmee=dec;
		}
		int joueurbatiment = (codeS/(50*50*50*50))%50;
		if ((!(joueurs.get(joueurbatiment).isalive))&&(joueurbatiment != 0)){ //si le joueur n'est pas en vie lors de la creation c'est qu'il n'a pas encore ete personnaliser
			joueurs.get(joueurbatiment).isalive = true;
			//System.out.println("Veuillez saisir le nom du joueur " + joueurbatiment +  " :");
			@SuppressWarnings("static-access")
			String str = fx.showTextInput("nom de joueur", "Veuillez saisir le nom du joueur " + joueurbatiment +  " :", "King Arthur");
			joueurs.get(joueurbatiment).changename(str);
			//System.out.println("Veuillez saisir une armee de "+joueurs.get(joueurbatiment)+ " :");
			@SuppressWarnings("static-access")
			String str2 = fx.showConfirm("nom de joueur", "Veuillez saisir une armee de "+joueurs.get(joueurbatiment)+ " :", "Armee d'insectes", "Armee des morts", "Armee myhtologique chinoise");
			int dec=0; 
			if (str2 == "Armee d'insectes") {
				dec = 0;
			} else if (str2 == "Armee des morts") {
				dec = 2;
			} else {
				dec = 1;
			}
			joueurs.get(joueurbatiment).typearmee=dec;
		}
	}
	
	/**ajoute le terrain correspondant au bon code au rang k
	 * @param k le rang de la case a laquelle on veut ajouter le terrain correspondant au code
	 * @param codeS la valeur du tableau d'entier de la sauvegarde correspondant a l'etat de la case en question
	 * @see #remakecarte(File, boolean)
	 */
	int remaketerrain(int k, int codeS) {
		plateau[k].terrain = referencecodeterrain.get(0); //le vide par defaut
		for (int j =1; j<referencecodeterrain.size();j++) {
			if (codeS%50 == j){
				this.plateau[k].terrain = referencecodeterrain.get(j); //on change la carte

			return j; //on aura une seule correspondance, pas besoin de faire plus de tests
			}

		}
		return 0;
	}

	/**ajoute l'unite correspondant au bon code au rang k de la carte
	 * @param k le rang de la case a laquelle on veut ajouter le terrain correspondant au code
	 * @param codeS la valeur du tableau d'entier de la sauvegarde correspondant a l'etat de la case en question
	 * @param startgame indique si on est dans le cadre de la creation d'une partie ou non (non=on est dans la partie modification de la sauvegarde de la carte)
	 * @see #remakecarte(File, boolean)
	 */
	void remakeunite(int k, int codeS, boolean startgame) {
		int codeunite = (codeS/50)%50;
		int joueur = (codeS/(50*50))%50;
		
		/*~~~~~~Partie a mettre a jour quand on ajoute des types d'unitees !~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
		// Ne pas oublier de modifier le nombre total d'unite dans la fonction en dessous
			if (codeunite == 0) {
				plateau[k].unite = null;
			}
			else {
				if (codeunite == 1) {
					addunite(k, new AbeilleSamourai(taillec,joueur)); //on ajoute l'unite sur la carte
				}
				else if (codeunite == 2) {
					addunite(k, new PapillonPsychique(taillec,joueur));
					}
				else if (codeunite == 3) {
					addunite(k, new Scarabe(taillec,joueur));
					}
				else if (codeunite == 4) {
					addunite(k, new Abeille(taillec,joueur));
					}
				else if (codeunite == 5) {
					addunite(k, new Fourmis(taillec,joueur));
				}
				else if (codeunite == 6) {
					addunite(k, new Moustique(taillec,joueur));
				}
				else if (codeunite == 7) {
					addunite(k,new EpeisteVolant(taillec,joueur));
				}
				else if (codeunite == 8) {
					addunite(k,new TankSquelette(taillec,joueur));
				}
				else if (codeunite == 9) {
					addunite(k,new SkeletonSoldier(taillec,joueur));
				}
				else if (codeunite == 10) {
					addunite(k,new ArcherSquelette(taillec,joueur));
				}
				else if (codeunite == 11) {
					addunite(k,new ZombieMineur(taillec,joueur));
				}
				else if (codeunite ==12) {
					addunite(k,new CraneVolant(taillec,joueur));
				}
				if (startgame) {
					joueurs.get(joueur).add(plateau[k].unite); //on ajoute l'unite la liste d'unites du bon joueur si on est en jeu
				}
			}
			/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/

		}

	/**
	 * A METTRE A JOUR LORSQU'ON AJOUTE UN NOUVEAU TYPE D'UNITE
	 * 
	 * Permet le defilement du choix des unites dans le menucrea (qui correspond a la partie modification de la sauvegarde)
	 */
	int nombretotunite() {return 12;}

	/**ajoute le batiment correspondant au bon code au rang k de la carte
	 * 
	* @param k le rang de la case a laquelle on veut ajouter le terrain correspondant au code
	* @param codeS la valeur du tableau d'entier de la sauvegarde correspondant a l'etat de la case en question
	* @param startgame indique si on est dans le cadre de la creation d'une partie ou non (non=on est dans la partie modification de la sauvegarde de la carte)
	* @see #remakecarte(File, boolean)
	*/
	void remakebatiment(int k, int codeS, boolean startgame) {
		int codebatiment = (codeS/(50*50*50))%50;
		int joueur = (codeS/(50*50*50*50))%50;
		if (codebatiment !=0) {
		/*~~~~~~Partie a mettre a jour quand on ajoute des types de batiments !~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (codebatiment == 1) {
				plateau[k].batiment = new Portail(taillec,joueur,joueurs.get(joueur).typearmee);
				}
			else if (codebatiment == 2) {
				plateau[k].batiment = new Crystal(taillec,joueur);
			}
	
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (startgame&&(joueur != 0)) {
				joueurs.get(joueur).add(plateau[k]); //on ajoute le batiment ï¿½ la liste d'unitï¿½s du bon joueur si on est en jeu et que le batiment n'est pas neutre
			}
		}
		else {
			plateau[k].batiment = null;
		}
	}

	/**
	 * A METTRE A JOUR LORSQU'ON AJOUTE UN NOUVEAU TYPE DE BATIMENT
	 * 
	 * Permet le defilement du choix des batiments dans le menucrea (qui correspond a la partie modification de la sauvegarde)
	 */
	int nombretotbatiment() {return 2;}

	/**Cette fonction renvoie le tableau d'entier correspondant a la sauvegarde tout en modifiant la carte
	 * de maniere a ce quelle soit dans l'etat de la sauvegarde
	 * 
	 * @param f le fichier contenant le tableau d'entier de la sauvegarde de la carte
	 * @param startgame vrai si on utilise remakecarte dans le cadre de la recreation de la carte pour jouer, faux si c'est pour modifier la sauvegarde
	 * @return le tableau d'entier contenu dans le fichier si il existe, et null sinon
	 */
	int[] remakecarte(File f,boolean startgame) {
		try { // si la sauvegarde existe on reconstruit la carte a l'aide du code
			FileInputStream fis = new FileInputStream(f.getName());
			ObjectInputStream ois = new ObjectInputStream(fis);
			SauvegardeCarte sauvegarde = (SauvegardeCarte) ois.readObject();
			int[] cartecode = sauvegarde.grillecarte;
			for(int k=0; k<2500; k++) {
				int codeS = cartecode[k];
				if(startgame) {
					remakejoueur(codeS);
				}
				remaketerrain(k,codeS);
				remakebatiment(k,codeS,startgame);
				remakeunite(k,codeS,startgame);

			}
			ois.close();
			return cartecode;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		}
	
	/** Affiche l'information qu'un joueur a perdu
	 * 
	 * @param gc Contexte graphique dans lequel on affiche l'interface visuelle
	 */
	void affichageperdant(GraphicsContext gc){
		affichageperdu++;
		if (affichageperdu<120) {
			gc.setFill(Color.DARKRED);
			gc.setLineWidth(2);
			int distaucentre =Integer.min(300,Integer.max(9,nomperdant.length())*17);
			gc.fillRoundRect(355-distaucentre, 230, 2*distaucentre, 250, 20, 20);
			gc.strokeRoundRect(355-distaucentre, 230, 2*distaucentre, 250, 20, 20);
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 60));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(nomperdant, 355-Integer.min(250,nomperdant.length()*16), 300, 600);
			gc.strokeText(nomperdant, 355-Integer.min(250,nomperdant.length()*16), 300, 600);
			
			gc.fillText("a perdu", 240, 370);
			gc.strokeText("a perdu", 240, 370);
			
			gc.fillText("RIP", 310, 450);
			gc.strokeText("RIP", 310, 450);
		}
		else {
			affichageperdu=0;
			nomperdant=null;
		}
}
	   
}