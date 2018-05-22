package jeu;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import batiments.Portail;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import son.Son;

public class Jeu {
	/**Entier qui indique quel joueur est en train de jouer */
	int entrainjouer;
	/**Entier qui indique a quel tour de jeu on est */
	int tour;
	/**Carte du jeu */
	Carte carte;
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**Entier qui decrit si on est: 0 dans le jeu; 1 dans le menu1(deplacement/attaque/capture); 2 dans le menu2(changertour); 3 dans le menu3(invoquer)*/
	int menu;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non : true = il faut rafraichir */
	boolean update;
	/**Objet contenant les methodes pour gerer l'attaque entre unites*/
	GestionAtq atq;
	/**Objet contenant les methodes pour gerer le deplacement des unites*/
	GestionDepl depl;
	/**Objet contenant les methodes pour gerer la capture des batiments*/
	GestionCapture capt;
	boolean ingame;
	/**MenuInfo */
	MenuInfo menuinfo;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non lors d'un deplacement ou d'une attaque: true = il faut rafraichir */
	boolean updatemenu;
	/** Menu pour les invocations*/
	MenuInvocation menuinvoc;
	/** Menu de droite pour faire les actions: Attaquer, se deplacer, capturer et passer le tour*/
	MenuAction menudroite;
	MenuOption menuoption;
	MenuSon menuson;
	Son sd;
	Clip clip;

	
/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/**
	 * Constructeur de Jeu.
	 * 
	 * @param gc 
	 * 		Contexte graphique dans lequel on affiche
	 * 
	 * Initialement, on se situe au tour 0 et le premier joueur indique par l'entier (entrainjouer=0) commence
	 * 			
	 */	
	Jeu(GraphicsContext gc, int width, int height, Son sd, Clip clip){
		this.carte = new Carte();
		tour = 0;
		entrainjouer=1;
		this.gc=gc;
		menu=0;
		update=true;
		atq = new GestionAtq(carte,sd);
		depl = new GestionDepl(carte,this);
		capt = new GestionCapture(carte);
		ingame = false;
		menudroite= new MenuAction(gc,carte.taillec*carte.nombrecaseaffichee,width,height);
		menuinfo = new MenuInfo(carte,menudroite.positionxmenu);
		updatemenu=false;
		menuinvoc = new MenuInvocation(new Portail(75,0,0),menudroite.positionxmenu);
		menuoption = new MenuOption(0,width,height);
		menuson = new MenuSon(width,height, clip, sd);
		this.sd=sd;
		this.clip=clip;
	}
/*_Mise a jour de l'affichage______________________________________________________________________________________________________ */	
	
	void update() {
		if (menu!=3) {
			if(menuoption.inmenuop) {
				if (menuson.inmenusd) {
					if(menuson.update) {
						menuson.render(gc);
						menuson.update=false;
					}
				}
				else if(menuoption.update) {
					menuoption.render(gc);
					menuoption.update=false;
				}
			}
			else {
				if (atq.animatqencours||atq.pvendiminution) {
					atq.renderanim(gc);
				}
				else if (atq.animatqencoursligne) {
					atq.renderanimligne(gc);
				}
				else {	
					carte.renderanim(gc); //animation des sprites si pas de combat
				}
				if (update && carte.nomperdant==null) { // on evite d'afficher toute la carte a chaque fois, seulement quand c'est necessaire
			         String tour = "Tour: "+this.tour;
			         gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			         gc.setLineWidth(1);
			         gc.setFill(Color.BISQUE);
			         gc.setStroke(Color.BLACK);
					 carte.render(gc);
					 update=false;
			         gc.fillText(tour, menudroite.positionxmenu-120, 730);
			         gc.strokeText(tour, menudroite.positionxmenu-120, 730 );
				}
				if (carte.nomperdant != null) {
					carte.affichageperdant(gc);
				}
				
			    if (menu==1) {
			    		if (depl.deplacementencours) {
			    			depl.render(this);
			    			depl.arrowrender(this);
			    		}
			    		if (atq.attaqueencours) {
			    			atq.rendercase(updatemenu,gc);
			    		}
			    		if (atq.attaqueencours) {
			    			atq.rendercible(gc);
			    		}
			    		updatemenu=false;
			    }
			    menudroite.menurender(this); //pour l'instant on refresh le menu a chaque fois, pas trop grave vu qu'il ne s'agit que de quelques images
				menuinfo.MenuInforender(gc);
			    carte.curseurRender(gc); //on affiche le curseur tout a la fin (au dessus donc) et tout le temps car il ne s'agit que d'une image
				
			}
		}
		else {
			menuinvoc.render(gc,carte.joueurs.get(entrainjouer));
		}
		//affichage des infos des joueurs-------------------------------------------------------------
		Joueur joueur = carte.joueurs.get(entrainjouer);
		String infojoueur = "Joueur: "+joueur+"\nRessources: "+joueur.ressources;
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setLineWidth(1);
		switch(entrainjouer) {
		case 4: 
			gc.setFill(Color.WHITE);
			break;
		case 1:
			gc.setFill(Color.BLUE);
			break;
		case 2:
			gc.setFill(Color.RED);
			break;
		case 3:
			gc.setFill(Color.GREEN);
			break;
		}

		gc.fillText(infojoueur, menudroite.positionxmenu*1.05, 40 );
		gc.strokeText(infojoueur, menudroite.positionxmenu*1.05, 40 );
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.BLACK);
		//----------------------------------------------------------------------------------------------
	}
/*_Controle du clavier____________________________________________________________________________________________________________ */	
	/**
	 * Controle du clavier:
	 * 
	 * A permet de selectionner une case.
	 * B permet de sortir du menu.
	 * LEFT,RIGHT,UP,DOWN correspondent aux fleches pour se diriger.
	 * 
	 *@param code
	 *		Code de la touche appuyee.
	 * @throws CloneNotSupportedException 
	 * 
	 */	
	void touch(KeyCode code) throws CloneNotSupportedException {
		if (ingame && !(atq.animatqencours||atq.pvendiminution)) {
		if (carte.ecranfin == 0){ //permet de quitter en appuyant sur n'importe quelle touche une fois la partie finie
	    switch(code) {
	    case A: // On fait les options du menu1 : Verification que l'on peut jouer l'unite
	    	switch(menu) {
	    	case 1:
	    		if (menudroite.positioncurseur1==0) {
	    			// gestion de l'attaque
	    			//menu = atq.attaque(carte.selectionnemenu.unite.type);
	    			menu = atq.attaque(carte.selectionnemenu.unite.type);
	    		}
	    		else if ((menudroite.positioncurseur1==1)&&(carte.selectionnemenu.unite.restdeplacement!=0)) {
	    			// gestion de deplacement
	    			menu = depl.deplacement();

	    		}
	    		else if (menudroite.positioncurseur1==2) {
	    			// gestion de la capture
	    			menu = capt.capture();
	    		}
	    		update=true;
	    		updatemenu=true;
	    		break;
	    	case 0:
	    		if(menuoption.inmenuop) {
	    			switch(menuoption.positioncurseur) {
	    			case 0:
	    				menuoption.inmenuop=false;
	    				break;
	    			case 1: 
	    				menuson.inmenusd=true;menuson.update=true; break;
	    			case 2:
	    				carte.perdu(entrainjouer);
	    				passertour();
	    				break;
	    			case 3:
	    				fin();
	    				break;
	    			default:
	    				break;
	    			}
	    			if (!menuson.inmenusd) {menuoption.inmenuop=false;}
	    		}
	    		else {
	    			System.out.print(carte.selectionne);
	    			if (carte.selectionne.unite!=null && carte.selectionne.unite.goodplayer(entrainjouer) && carte.selectionne.unite.valable) {menudroite.positioncurseur1=0;menu=1;carte.selectionnemenu = carte.selectionne;} //on ouvre le menu et on selectionne la case
	    			else if (carte.selectionne.unite==null && (carte.selectionne.batiment!=null) &&(carte.selectionne.batiment instanceof Portail) && carte.selectionne.batiment.joueur==entrainjouer) {menuinvoc.positioncurseur=0; menuinvoc.confirmationencours=false;menu = 3; menuinvoc.portail = (Portail) carte.selectionne.batiment; }
	    			else {menu=2; menudroite.positioncurseur1=0;};	 
	    		}
	    		break;
	    	case 2:
	    		passertour();
	    		break;
	    	case 3:
	    		if(menuinvoc.confirmationencours && menuinvoc.positioncurseurconf==0) {
	    			menuinvoc.changeinvoque(carte.joueurs.get(entrainjouer));
	    			menuinvoc.confirmationencours=false;
	    		}
	    		else if (menuinvoc.confirmationencours && menuinvoc.positioncurseurconf==1) {
	    			menuinvoc.confirmationencours=false;
	    		}
	    		else {
	    			menuinvoc.confirmationencours=true;
	    		}
	    		update=true;
	    		break;
	    	default:
	    		break;
	    	}
	    	break; 
	    case B : 
	    	if (menuoption.inmenuop) {
	    		if (menuson.inmenusd) {
	    			menuson.inmenusd = false;
	    			menuson.update = false;
	    			menuoption.update=true;
	    		} else {
	    		menuoption.inmenuop=false; menuoption.positioncurseur=0; } }
	    	else {
		    	if (atq.attaqueencours) {atq.attaqueencours=false;carte.selectionne = carte.selectionnemenu;menu=1;} //pour faire revenir le curseur a l'unite qui attaque
		    	else if (depl.deplacementencours) { depl.deplacementencours=false;carte.selectionne = carte.selectionnemenu;menu=1;}
		    	else {menu=0;}
	    	}
	    	break;
		case LEFT:  
					switch(menu) {
					case 0: if(!menuoption.inmenuop) { carte.leftcurseur(); break;}//on bouge sur la carte
							else {
								if (menuson.inmenusd) {
									switch(menuson.positioncurseur) {
										case 0 : menuson.lessMusic();menuson.update=true;break;
										case 1 : menuson.lessEffect();menuson.update=true;break;
										default : break;
									}
								}
							}
					case 1:
							if ((depl.deplacementencours)&&(depl.inlist(carte.selectionne.rang-1,depl.deplist))) {
								carte.leftcurseur(); //on selectionne case pour deplacement
							}
							else if (atq.attaqueencours && atq.atqenemi.size()!=0 && (carte.selectionnemenu.unite.type.compareTo("zone1")*carte.selectionnemenu.unite.type.compareTo("zone2")*carte.selectionnemenu.unite.type.compareTo("zonesoin")!=0)) {
								atq.downenemi(); //on change la cible de l'attaque
								carte.adaptaffichage(carte.selectionne.rang);
							}
							else if (atq.deplacementzoneadjacente(carte.selectionne.rang-1)) {
								carte.leftcurseur();
							}
							else {atq.deplacementzonesaut(1);}
							updatemenu=true;
							break;
					case 3:
							if(menuinvoc.confirmationencours) {
								menuinvoc.leftcurseurconf();
							}
					default:
							break;
					}
					break;
		case RIGHT: 
					switch(menu) {
					case 0: if(!menuoption.inmenuop) { carte.rightcurseur(); break; } 
							else {
								if (menuson.inmenusd) {
									switch(menuson.positioncurseur) {
									case 0 : menuson.moreMusic();menuson.update=true;break;
									case 1 : menuson.moreEffect();menuson.update=true;break;
									default : break;
									}
									
								}
							}
					case 1:
							if ((depl.deplacementencours)&&(depl.inlist(carte.selectionne.rang+1,depl.deplist))) {
								carte.rightcurseur(); //on selectionne case pour deplacement
							}
							else if (atq.attaqueencours && atq.atqenemi.size()!=0 && (carte.selectionnemenu.unite.type.compareTo("zone1")*carte.selectionnemenu.unite.type.compareTo("zone2")*carte.selectionnemenu.unite.type.compareTo("zonesoin")!=0)) {
								atq.upenemi();//on change la cible de l'attaque
								carte.adaptaffichage(carte.selectionne.rang);
							}
							else if (atq.deplacementzoneadjacente(carte.selectionne.rang+1)) {
								carte.rightcurseur();
							}
							else {atq.deplacementzonesaut(0);}
							updatemenu=true;
							break;
					case 3:
							if(menuinvoc.confirmationencours) {
								menuinvoc.rightcurseurconf();
							}
							break;
					default:
							break;
					}
					break;
		case UP:  
					switch(menu) {
					case 1:
							if ((depl.deplacementencours)&&(depl.inlist(carte.selectionne.rang-50,depl.deplist))) {
								carte.upcurseur();//on selectionne case pour deplacement
							} 
							else if (atq.deplacementzoneadjacente(carte.selectionne.rang-50)) {
								carte.upcurseur();
							}
							else if(!(depl.deplacementencours)&&(!atq.attaqueencours)) {menudroite.upcurseur1();} //on bouge curseur du menu
							else {atq.deplacementzonesaut(2);}
							updatemenu=true;
							break;
					case 0: 
							if (menuoption.inmenuop){
								if (menuson.inmenusd) {menuson.upcurseur();menuson.update=true;}
								else {
								menuoption.upcurseur();
								menuoption.update=true;
								}
							}
							else {
								carte.upcurseur();//on bouge curseur de carte
							}
							break;
					case 3:
							if(!menuinvoc.confirmationencours) {
								menuinvoc.upcurseur();
							}
							break;
					default:
							break;
					}
					break; 
		case DOWN: 
					switch(menu) {
					case 1:
							if ((depl.deplacementencours)&&(depl.inlist(carte.selectionne.rang+50,depl.deplist))) {
								carte.downcurseur();//on selectionne case pour deplacement
							}
							else if (atq.deplacementzoneadjacente(carte.selectionne.rang+50)) {
								carte.downcurseur();
							}
							else if(!(depl.deplacementencours)&&(!atq.attaqueencours)) {menudroite.downcurseur1();}//on bouge curseur du menu
							else {atq.deplacementzonesaut(3);}
							updatemenu=true;
							break;
					case 0:
							if (menuoption.inmenuop) {
								if (menuson.inmenusd) {menuson.downcurseur();menuson.update=true;}
								else {
								menuoption.downcurseur();
								menuoption.update=true;
								}
							}
							else {

								carte.downcurseur();//on bouge curseur de carte
							}
							break;
					case 3:
							if(!menuinvoc.confirmationencours) {
								menuinvoc.downcurseur();
							}
							break;
					default:
							break;
					}
					break;
		case ENTER:
				if (ingame) {
					if (menu==0) {
						menuoption.inmenuop=true;
						menuoption.positioncurseur=0;
						menuoption.update=true;
					}
				}
				break;			
	    default:
	    		break;
	    }
		}
		else {
			fin();
		}
		}
	}

	/**
	 * Incremente le nombre de tour, change le joueur qui est entrain de jouee et reinitialisation du booleen valable
	 * pour pouvoir rejouer les unites.
	 * @throws CloneNotSupportedException 
	 */
	void passertour() throws CloneNotSupportedException {
		do{
			entrainjouer++;	//on passe au prochain joueur en vie
			if (entrainjouer == 5) {
				entrainjouer = 1;
				tour++; //Change de tour
			}
		}while(!(carte.joueurs.get(entrainjouer).isalive));
		carte.joueurs.get(entrainjouer).rendreValable();
		carte.joueurs.get(entrainjouer).printSituation();
		menu=0;
		carte.joueurs.get(entrainjouer).actiondesbatiments();
		
	}
	

	/**
	 * Reinitialise jeu lorsque l'on arrete la partie.
	 */
    
	void fin() {
		
		//reinitialisation des joueurs :
		carte.joueurs = new ArrayList<Joueur>();
		for (int i = 0; i <= 4;i++) {
			carte.joueurs.add(new Joueur("sansnom")); 
		}
		carte.ecranfin = 0;
		tour = 0;
		entrainjouer=1;
		menu=0;
		update=true;
		atq = new GestionAtq(carte,sd);
		depl = new GestionDepl(carte,this);
		capt = new GestionCapture(carte);
		ingame=false;
		carte.selectionne=carte.plateau[51];
		carte.rangcorner = 0;
	}

}
