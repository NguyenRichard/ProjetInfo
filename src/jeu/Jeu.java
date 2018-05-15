package jeu;

import java.util.ArrayList;

import batiments.Portal;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Jeu {
	/**Entier qui indique quel joueur est en train de jouer */
	int entrainjouer;
	/**Entier qui indique a quel tour de jeu on est */
	int tour;
	/**Carte du jeu */
	Map map;
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**Entier qui decrit si on est: 0 dans le jeu; 1 dans le menu1(deplacement/attaque/capture); 2 dans le menu2(changertour); 3 dans le menu3(invoquer)*/
	int menu;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non : true = il faut rafraichir */
	boolean update;
	/**Objet contenant les methodes pour gerer l'attaque entre unites*/
	Gestionatq atq;
	/**Objet contenant les methodes pour gerer le deplacement des unites*/
	Gestiondepl depl;
	/**Objet contenant les methodes pour gerer la capture des batiments*/
	Gestioncapture capt;
	boolean ingame;
	/**MenuInfo */
	MenuInfo menuinfo;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non lors d'un deplacement ou d'une attaque: true = il faut rafraichir */
	boolean updatemenu;
	/** Menu pour les invocations*/
	Menuinvocation menuinvoc;
	/** Menu de droite pour faire les actions: Attaquer, se deplacer, capturer et passer le tour*/
	Menuaction menudroite;
	Menuoption menuoption;

	
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
	Jeu(GraphicsContext gc, int width, int height){
		this.map = new Map();
		tour = 0;
		entrainjouer=1;
		this.gc=gc;
		menu=0;
		update=true;
		atq = new Gestionatq(map);
		depl = new Gestiondepl(map,this);
		capt = new Gestioncapture(map);
		ingame = false;
		menudroite= new Menuaction(gc,map.taillec*map.nombrecaseaffichee,width,height);
		menuinfo = new MenuInfo(map,menudroite.positionxmenu);
		updatemenu=false;
		menuinvoc = new Menuinvocation(new Portal(75,0,0));
		menuoption = new Menuoption(0,width,height);
	}
/*_Mise a jour de l'affichage______________________________________________________________________________________________________ */	
	
	void update() {
		if (menu!=3) {
			if(menuoption.inmenuop) {
				if(menuoption.update) {
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
				else {	map.renderanim(gc);}//animation des sprites si pas de combat
				if (update) { // on evite d'afficher toute la map a chaque fois, seulement quand c'est necessaire
			         String tour = "Tour: "+this.tour;
			         gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			         gc.setLineWidth(1);
			         gc.setFill(Color.BISQUE);
			         gc.setStroke(Color.BLACK);
					 map.render(gc);
					 update=false;
			         gc.fillText(tour, menudroite.positionxmenu-120, 730);
			         gc.strokeText(tour, menudroite.positionxmenu-120, 730 );
				}
			
			    if (menu==1) {
			    		if (depl.deplacementencours) {
			    			depl.render(this);
			    			depl.arrowrender(this);
			    		}
			    		if (atq.attaqueencours&&updatemenu) {
			    			atq.rendercase(this);
			    		}
			    		if (atq.attaqueencours) {
			    			atq.rendercible(this);
			    		}
			    		updatemenu=false;
			    }
			    menudroite.menurender(this); //pour l'instant on refresh le menu a chaque fois, pas trop grave vu qu'il ne s'agit que de quelques images
				menuinfo.MenuInforender(gc);
			    map.curseurRender(gc); //on affiche le curseur tout a la fin (au dessus donc) et tout le temps car il ne s'agit que d'une image
				
			}
		}
		else {
			menuinvoc.render(gc);
		}
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
	    switch(code) {
	    case A: // On fait les options du menu1 : Verification que l'on peut jouer l'unite
	    	switch(menu) {
	    	case 1:
	    		if (menudroite.positioncurseur1==0) {
	    			// gestion de l'attaque
	    			//menu = atq.attaque(map.selectionnemenu.unite.type);
	    			menu = atq.attaque(map.selectionnemenu.unite.type);
	    		}
	    		else if ((menudroite.positioncurseur1==1)&&(map.selectionnemenu.unite.restdeplacement!=0)) {
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
	    			case 2:
	    				map.perdu(entrainjouer);
	    				passertour();
	    				break;
	    			case 3:
	    				fin();
	    				break;
	    			default:
	    				break;
	    			}
	    			menuoption.inmenuop=false;
	    		}
	    		else {
	    			System.out.print(map.selectionne);
	    			if (map.selectionne.unite!=null && map.selectionne.unite.goodplayer(entrainjouer) && map.selectionne.unite.valable) {menudroite.positioncurseur1=0;menu=1;map.selectionnemenu = map.selectionne;} //on ouvre le menu et on selectionne la case
	    			else if (map.selectionne.unite==null && (map.selectionne.batiment!=null) &&(map.selectionne.batiment instanceof Portal) && map.selectionne.batiment.joueur==entrainjouer) {menuinvoc.positioncurseur=0; menuinvoc.confirmationencours=false;menu = 3; menuinvoc.portail = (Portal) map.selectionne.batiment; }
	    			else {menu=2; menudroite.positioncurseur1=0;};	 
	    		}
	    		break;
	    	case 2:
	    		passertour();
	    		break;
	    	case 3:
	    		if(menuinvoc.confirmationencours && menuinvoc.positioncurseurconf==0) {
	    			menuinvoc.changeinvoque(map.joueurs.get(entrainjouer));
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
	    		menuoption.inmenuop=false; menuoption.positioncurseur=0; }
	    	else {
		    	if (atq.attaqueencours) {atq.attaqueencours=false;map.selectionne = map.selectionnemenu;menu=1;} //pour faire revenir le curseur a l'unite qui attaque
		    	else if (depl.deplacementencours) { depl.deplacementencours=false;map.selectionne = map.selectionnemenu;menu=1;}
		    	else {menu=0;}
	    	}
	    	break;
		case LEFT:  
					switch(menu) {
					case 0: if(!menuoption.inmenuop) { map.leftcurseur(); break;}//on bouge sur la map
					case 1:
							if ((depl.deplacementencours)&&(depl.inlist(map.selectionne.rang-1,depl.deplist))) {
								map.leftcurseur(); //on selectionne case pour deplacement
							}
							if (atq.attaqueencours && atq.atqenemi.size()!=0) {
								atq.downenemi(); //on change la cible de l'attaque
								map.adaptaffichage(map.selectionne.rang);
							}
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
					case 0: if(!menuoption.inmenuop) { map.rightcurseur(); break; }
					case 1:
							if ((depl.deplacementencours)&&(depl.inlist(map.selectionne.rang+1,depl.deplist))) {
								map.rightcurseur(); //on selectionne case pour deplacement
							}
							if (atq.attaqueencours && atq.atqenemi.size()!=0) {
								atq.upenemi();//on change la cible de l'attaque
								map.adaptaffichage(map.selectionne.rang);
							}
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
							if ((depl.deplacementencours)&&(depl.inlist(map.selectionne.rang-50,depl.deplist))) {
								map.upcurseur();//on selectionne case pour deplacement
							} else if(!(depl.deplacementencours)&&(!atq.attaqueencours)) {menudroite.upcurseur1();} //on bouge curseur du menu
							updatemenu=true;
							break;
					case 0: 
							if (menuoption.inmenuop){
								menuoption.upcurseur();
								menuoption.update=true;
							}
							else {
								map.upcurseur();//on bouge curseur de map
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
							if ((depl.deplacementencours)&&(depl.inlist(map.selectionne.rang+50,depl.deplist))) {
								map.downcurseur();//on selectionne case pour deplacement
							} else if(!(depl.deplacementencours)&&(!atq.attaqueencours)) {menudroite.downcurseur1();}//on bouge curseur du menu
							updatemenu=true;
							break;
					case 0:
							if (menuoption.inmenuop) {
								menuoption.downcurseur();
								menuoption.update=true;
							}
							else {

								map.downcurseur();//on bouge curseur de map
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
	}

	/**
	 * Incremente le nombre de tour, change le joueur qui est entrain de jouee et reinitialisation du booleen valable
	 * pour pouvoir rejouer les unites.
	 * @throws CloneNotSupportedException 
	 */
	void passertour() throws CloneNotSupportedException {
		int i = 0;
		do{
			i++;
			entrainjouer++;	//on passe au prochain joueur en vie
			if (entrainjouer == 5) {
				entrainjouer = 0;
				tour++; //Change de tour
			}
		}while(!(map.joueurs.get(entrainjouer).isalive));
		if (i==4) {
			System.out.println(map.joueurs.get(entrainjouer) + " a gagne !");
		}
		map.joueurs.get(entrainjouer).rendreValable();
		map.joueurs.get(entrainjouer).printSituation();
		menu=0;
		map.joueurs.get(entrainjouer).actiondesbatiments();
		
	}
	

	/**
	 * Reinitialise jeu lorsque l'on arrete la partie.
	 */
    
	void fin() {
		
		//reinitialisation des joueurs :
		map.joueurs = new ArrayList<Joueur>();
		for (int i = 0; i <= 4;i++) {
			map.joueurs.add(new Joueur("sansnom")); 
		}
		tour = 0;
		entrainjouer=1;
		menu=0;
		update=true;
		atq = new Gestionatq(map);
		depl = new Gestiondepl(map,this);
		capt = new Gestioncapture(map);
		ingame=false;
		map.selectionne=map.plateau[51];
	}

}