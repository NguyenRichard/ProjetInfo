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
	/**Position du curseur dans le menu 0 : attaquer; 1 : deplacer; 2 : capturer*/
	int positioncurseur1;
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
	/**Entier a partir du quel affiche le menu lateral droit**/
    int positionxmenu;
	/**Image de fond du menu lateral droit**/
	Image menucache;
	/**Image de fond du menu lateral droit**/
	Image menu1;
	/**Image de fond du menu changer tour**/
	Image menu2;
	/**Image du curseur pour les menu**/
	Image curseur;
	/**Cache des options qui ne sont plus valide */
Image cache;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non lors d'un deplacement ou d'une attaque: true = il faut rafraichir */
	boolean updatemenu;
	Menuinvocation menuinvoc;

	
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
		entrainjouer=0;
		this.gc=gc;
		menu=0;
		positioncurseur1 = 0;
		update=true;
		atq = new Gestionatq(map);
		depl = new Gestiondepl(map,this);
		capt = new Gestioncapture(map);
		menucache = new Image("wood.jpg", width-map.taillec*map.nombrecaseaffichee,height,false,false);
		menu1 = new Image("menu1(10x16).png", 200, 320, false, false);
		curseur = new Image("curseurmenu1.png",200, 320, false, false);
		menu2 = new Image("menu2(10x16).jpg", 200, 320, false, false);
		cache = new Image("cache.png",200, 320, false, false);
		positionxmenu = map.taillec*map.nombrecaseaffichee;
		ingame = false;
		menuinfo = new MenuInfo(gc,map,positionxmenu);
		updatemenu=false;
		menuinvoc = new Menuinvocation(gc,new Portal(75,0,0));
	}
/*_Mise a jour de l'affichage______________________________________________________________________________________________________ */	
	
	void update() {
		if (menu!=3) {
			
			if (atq.animatqencours||atq.pvendiminution) {
				atq.renderanim(gc);
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
		         gc.fillText(tour, positionxmenu-120, 730);
		         gc.strokeText(tour, positionxmenu-120, 730 );
			}
		
		    if (menu==1) {
		    		if (depl.deplacementencours&&updatemenu) {
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
		    menurender(); //pour l'instant on refresh le menu a chaque fois, pas trop grave vu qu'il ne s'agit que de quelques images
			menuinfo.MenuInforender();
		    map.curseurRender(gc); //on affiche le curseur tout a la fin (au dessus donc) et tout le temps car il ne s'agit que d'une image
		}
		else {
			menuinvoc.render();
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
		    				if (positioncurseur1==0) {
		    					// gestion de l'attaque
		    					menu = atq.attaque(map.selectionnemenu.unite.type);
		    				}
		    				else if ((positioncurseur1==1)&&(map.selectionnemenu.unite.restdeplacement!=0)) {
		    					// gestion de deplacement
		    					menu = depl.deplacement();
		    					
		    				}
		    				else if (positioncurseur1==2) {
		    					// gestion de la capture
		    					menu = capt.capture();
		    				}
		    				update=true;
		    				updatemenu=true;
		    				break;
	    			case 0:
			    			System.out.print(map.selectionne);
			    			if (map.selectionne.unite!=null && map.selectionne.unite.goodplayer(entrainjouer) && map.selectionne.unite.valable) {menu=1;map.selectionnemenu = map.selectionne;} //on ouvre le menu et on selectionne la case
			    			else if (map.selectionne.unite==null && (map.selectionne.batiment!=null) &&(map.selectionne.batiment instanceof Portal) && map.selectionne.batiment.joueur==entrainjouer) {menuinvoc.positioncurseur=0; menu = 3; menuinvoc.portail = (Portal) map.selectionne.batiment; }
			    			else {menu=2; positioncurseur1=0;};	 
			    			break;
	    			case 2:
	    					passertour();
	    					break;
	    			case 3:
	    					menuinvoc.changeinvoque();
	    					break;
	    			default:
	    					break;
	    			} 
	    			break; 
	    case B : 
	    	if (atq.attaqueencours) {atq.attaqueencours=false;map.selectionne = map.selectionnemenu;menu=1;} //pour faire revenir le curseur a l'unite qui attaque
	    	else if (depl.deplacementencours) { depl.deplacementencours=false;map.selectionne = map.selectionnemenu;menu=1;}
	    	else {menu=0;}
	    	update=true; break;
		case LEFT:  
					switch(menu) {
					case 0: map.leftcurseur(); break;//on bouge sur la map
					case 1:
							if ((depl.deplacementencours)&&(inlist(map.selectionne.rang-1,depl.deplist))) {
								map.leftcurseur(); //on selectionne case pour deplacement
							}
							if (atq.attaqueencours && atq.atqenemi.size()!=0) {
								atq.downenemi(); //on change la cible de l'attaque
								map.adaptaffichage(map.selectionne.rang);
							}
							updatemenu=true;
							break;
					default:
							break;
					} update=true; break;
		case RIGHT: 
					switch(menu) {
					case 0: map.rightcurseur(); break;
					case 1:
							if ((depl.deplacementencours)&&(inlist(map.selectionne.rang+1,depl.deplist))) {
								map.rightcurseur(); //on selectionne case pour deplacement
							}
							if (atq.attaqueencours && atq.atqenemi.size()!=0) {
								atq.upenemi();//on change la cible de l'attaque
								map.adaptaffichage(map.selectionne.rang);
							}
							updatemenu=true;
							break;
					default:
							break;
					} update=true;break;
		case UP:  
					switch(menu) {
					case 1:
							if ((depl.deplacementencours)&&(inlist(map.selectionne.rang-50,depl.deplist))) {
								map.upcurseur();//on selectionne case pour deplacement
							} else if(!(depl.deplacementencours)&&(!atq.attaqueencours)) {upcurseur1();} //on bouge curseur du menu
							updatemenu=true;
							break;
					case 0: map.upcurseur(); break;//on bouge curseur de map
					case 3:
							menuinvoc.upcurseur();
							break;
					default:
							break;
					} update=true; break; 
		case DOWN: 
					switch(menu) {
					case 1:
							if ((depl.deplacementencours)&&(inlist(map.selectionne.rang+50,depl.deplist))) {
								map.downcurseur();//on selectionne case pour deplacement
							} else if(!(depl.deplacementencours)&&(!atq.attaqueencours)) {downcurseur1();}//on bouge curseur du menu
							updatemenu=true;
							break;
					case 0: map.downcurseur(); break;//on bouge curseur de map
					case 3:
							menuinvoc.downcurseur();
							break;
					default:
							break;
					} update=true; break;
	    default:
	    		break;
	    }
		}
	}
/*_Affichage du menu1_____________________________________________________________________________________________________________ */	
	/**
	 * Affichage du menu de deplacement et d'attaque, true le montre en false le cache en recouvrant tout le coter(a adapter plus tard)
	 * 			
	 */	
	void menurender() {
		gc.drawImage(menucache, positionxmenu, 0);
	    switch(menu) {
	    case 1:
				gc.drawImage(menu1, positionxmenu*1.05, 50);
				if (map.selectionnemenu.unite.restdeplacement == 0) {
	    			gc.drawImage(cache, positionxmenu*1.05,50+1*52);
	    		}
				if ((map.selectionnemenu.batiment == null)||(map.selectionnemenu.batiment.joueur == map.selectionnemenu.unite.joueur)) {
					gc.drawImage(cache, positionxmenu*1.05,50+2*52);
				}
				gc.drawImage(curseur, positionxmenu*1.05,50+positioncurseur1*52);
				break;
	    case 2:
	    		gc.drawImage(menu2, positionxmenu*1.05, 50);
	    		gc.drawImage(curseur, positionxmenu*1.05,50+positioncurseur1*52);
	    		break;

	    default:
	    		break;
		} 
	}

/*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
	
	void upcurseur1() {if (positioncurseur1 != 0) {positioncurseur1 -= 1;}}
	void downcurseur1() {if (positioncurseur1 != 2){positioncurseur1 += 1;}}
	
	
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
			if (entrainjouer == 4) {
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
		for (int i = 0; i <= 3;i++) {
			map.joueurs.add(new Joueur("sansnom")); 
		}
		tour = 0;
		entrainjouer=0;
		menu=0;
		positioncurseur1 = 0;
		update=true;
		atq = new Gestionatq(map);
		depl = new Gestiondepl(map,this);
		capt = new Gestioncapture(map);
		menuinfo = new MenuInfo(gc,map,positionxmenu);
		ingame=false;
	}

}