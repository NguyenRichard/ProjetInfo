package jeu;

import javax.sound.sampled.Clip;
import Sounds.Sound;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import terrain.*;
import terrain.Void;
import unit.*;
import batiments.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CreationMap {
	Map map;
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non : true = il faut rafraichir */
	boolean update;
	/**le menu permettant de selectionner l'element a placer */
	MenuCrea menucrea;
	boolean increa;
	Image menucache;
	String namesave;
	int[] mapcode;
	/**Entier a partir du quel affiche le menu lateral droit**/
    int positionxmenu;
    MenuOption menuoption;
    MenuSon menuson;
    Sound sd;
    Clip clip;
	
	/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/** Recree la map a l'aide du tableau d'entiers de la sauvegarde. 
	 * Le lien est fait grace a referencecode, il est donc important de le maintenir a jour.
	 * 
	 * @param gc le contexte graphique
	 */
	CreationMap(GraphicsContext gc,String txt,int width,int height,boolean ingame, Sound sd, Clip clip){
		
		map = new Map();
		
		this.namesave = txt;
		positionxmenu=map.taillec*map.nombrecaseaffichee;
		increa = false;
		menucache = new Image("fondmenu.png",width-map.taillec*map.nombrecaseaffichee,height,false,false);
		map.selectionne = map.plateau[51];
		this.gc=gc;
		menucrea= new MenuCrea(map.referencecodeterrain,map.plateau[2500],positionxmenu);
		actualiservisu(); //a faire apres Menucrea
		map.affichageEquipe();
		update=true;
		menuoption = new MenuOption(1,width,height);
		menuson = new MenuSon(width,height, clip, sd);
		this.sd=sd;
		this.clip=clip;
	}
	
	void changebatimentsave(int rang, int codeS) {
		int codebatiment = (codeS/(50*50*50))%50;
		int joueur = (codeS/(50*50*50*50))%50;
		mapcode[rang] = mapcode[rang] - ((mapcode[rang]/125000)%50)*50 + codebatiment*125000; //on change le batiment dans la sauvegarde
		mapcode[rang] = mapcode[rang] - ((mapcode[rang]/(6250000))%50)*50*50 + joueur*6250000; //de meme pour le joueur
	}
	
	void changeunitesave(int rang, int codeS) {
		int codeunite = (codeS/50)%50;
		int joueur = (codeS/(50*50))%50;
		System.out.println(joueur);
		mapcode[rang] = mapcode[rang] - ((mapcode[rang]/50)%50)*50 + codeunite*50; //on change l'unite dans la sauvegarde
		mapcode[rang] = mapcode[rang] - ((mapcode[rang]/(50*50))%50)*50*50 + joueur*50*50; //de meme pour le joueur

	}
	
	void changeterrainsave(int rang, int codeS) {
		mapcode[rang] = mapcode[rang] - (mapcode[rang]%50) + map.remaketerrain(rang,codeS);; //on change la sauvegarde
	}
	
	/*_Mise a jour de l'affichage______________________________________________________________________________________________________ */	
	
	void update() {
		if(!menuoption.inmenuop) {
			map.renderanim(gc); //animation des sprites
			if (update) { // on evite d'afficher toute la map a chaque fois, seulement quand c'est necessaire
				map.render(gc);
				update=false;
			}
			gc.drawImage(menucache, positionxmenu, 0);
			menucrea.render(gc);
		    map.curseurRender(gc); //on affiche le curseur tout a la fin (au dessus donc) et tout le temps car il ne s'agit que d'une image	
		}
		else {
			if (menuson.inmenusd) {
				if(menuson.update) {
					menuson.render(gc);
					menuson.update=false;
				}
			}
			else if(menuoption.update) {
				menuoption.render(gc);
			}
		}
	}
	
	void actualiservisu() {
		if (increa) {
			changeterrainsave(2500,menucrea.codesave);
			
			map.remakebatiment(2500,menucrea.codesave,false);
			changebatimentsave(2500,menucrea.codesave);
			
			map.remakeunite(2500,menucrea.codesave,false);
			changeunitesave(2500,menucrea.codesave);
		}
		else {
			map.remaketerrain(2500, menucrea.codesave);
			map.remakebatiment(2500, menucrea.codesave, false);
			map.remakeunite(2500,menucrea.codesave,false);
		}

	}
	
	/*_Controle du clavier____________________________________________________________________________________________________________ */	

	/**
	 * Controle du clavier:
	 * 
	 * A permet de placer un element.
	 * R permet d'enlever un element.
	 * B permet d'ouvrir le menucrea pour selectionner le type d'element
	 * LEFT,RIGHT,UP,DOWN correspondent aux fleches pour se diriger.
	 * 
	 *@param code
	 *		Code de la touche appuyee.
	 * 
	 */	
	void touch(KeyCode code) {
		if (increa) {
	    switch(code) {
	    case A:
	    	if(!menucrea.choix) {
	    		if(menuoption.inmenuop) {
	    			switch(menuoption.positioncurseur) {
	    			case 0:
	    				menuoption.inmenuop=false;
	    				break;
	    			case 1: 
	    				menuson.inmenusd=true;menuson.update=true; break;
	    			case 2:
	    				try {
	    					FileOutputStream fos = new FileOutputStream("creamap.ser"); // nom du fichier contenant la sauvegarde
	    					SauvegardeMap sauvegarde = new SauvegardeMap();
	    					sauvegarde.grillemap = mapcode;
	    					ObjectOutputStream oos = new ObjectOutputStream(fos);
	    					oos.writeObject(sauvegarde);
	    					oos.close();
	    					} catch (FileNotFoundException e) {
	    						e.printStackTrace();
	    					} catch (IOException e) {
	    						e.printStackTrace();
	    					}
	    				break;
	    			case 3:
	    				stop();
	    				break;
	    			}
	    			if (!menuson.inmenusd) {
	    			menuoption.inmenuop=false;
	    			}
	    		}
	    		else {
			    	int codeS = menucrea.codesave;
			    	if (menucrea.choixtype == 0) {
			    		changeterrainsave(map.selectionne.rang,codeS);
			    	}
			    	else if ((menucrea.choixtype == 2)&&(map.selectionne.batiment == null)&&!(map.selectionne.terrain instanceof Void)) {
			    		map.remakebatiment(map.selectionne.rang,codeS,false);
			    		changebatimentsave(map.selectionne.rang,codeS);
			    	}
			    	else if ((menucrea.choixtype == 1)&&(map.selectionne.unite == null)&&!(map.selectionne.terrain instanceof Void)) {
			    		map.remakeunite(map.selectionne.rang,codeS,false); 
			    		changeunitesave(map.selectionne.rang,codeS);
			    	}
	    		}
	    	}
	   		break;
	    case B: 
	    	if (menuoption.inmenuop) {
	    		if (menuson.inmenusd) {
	    			menuson.inmenusd = false;
	    			menuson.update = false;
	    			menuoption.update=true;
	    		}
	    	} else {menucrea.choix=!menucrea.choix;}
    		break; 
	    case R :
	    	if(!menucrea.choix) {
		    	if (menucrea.choixtype == 0) {
		    		map.selectionne.terrain = map.referencecodeterrain.get(0); //on change visuellement
		    		mapcode[map.selectionne.rang] -= mapcode[map.selectionne.rang]%50; //et dans la sauvegarde
		    	}
		    	else if (menucrea.choixtype == 1) {
		    		if (map.selectionne.unite != null) {
		    			map.delunite(map.selectionne.unite, map.selectionne.unite.joueur);
		    			map.selectionne.unite = null; //on enleve l'unite visuellement et dans la sauvegarde
		    			mapcode[map.selectionne.rang] -= ((mapcode[map.selectionne.rang]/50)%50)*50; 
		    			mapcode[map.selectionne.rang] -= ((mapcode[map.selectionne.rang]/(50*50))%50)*50*50;
		    		}
		    	}
		    	else if (menucrea.choixtype == 2) {
		    		if (map.selectionne.batiment != null) {
		    			map.selectionne.batiment = null; //on enleve l'e batiment visuellement et dans la sauvegarde
		    			mapcode[map.selectionne.rang] -= ((mapcode[map.selectionne.rang]/125000)%50)*125000; 
		    			mapcode[map.selectionne.rang] -= ((mapcode[map.selectionne.rang]/(125000*50))%50)*125000*50; 
		    		}
		    	}
	    	}
		    break;
		case LEFT: 
			if(!menuoption.inmenuop) {
				if (menucrea.choix) {
					menucrea.leftcurseur(map.nombretotunite(),map.nombretotbatiment());
					actualiservisu();
					
				}
				else {map.leftcurseur();}
			} else {
				if (menuson.inmenusd) {
					switch(menuson.positioncurseur) {
						case 0 : menuson.lessMusic();menuson.update=true;break;
						case 1 : menuson.lessEffect();menuson.update=true;break;
						default : break;
					}
				}
			}
			break;
		case RIGHT: 
			if(!menuoption.inmenuop) {
				if (menucrea.choix) {
					menucrea.rightcurseur(map.nombretotunite(),map.nombretotbatiment());
					actualiservisu();
				}
				else {map.rightcurseur();}
			} else {
				if (menuson.inmenusd) {
					switch(menuson.positioncurseur) {
					case 0 : menuson.moreMusic();menuson.update=true;break;
					case 1 : menuson.moreEffect();menuson.update=true;break;
					default : break;
					}
					
				}
			}
			break;
		case UP:
			if(menuoption.inmenuop) {
				if (menuson.inmenusd) {menuson.upcurseur();menuson.update=true;} 
				else {
				menuoption.upcurseur();
				menuoption.update=true;
				}
			}
			else {
				if (menucrea.choix) { //si on est en train de choisir l'element
					menucrea.upcurseur();
				}
				else {map.upcurseur();}
			}
			break;
		case DOWN:
			if(menuoption.inmenuop) {
				if (menuson.inmenusd) {menuson.downcurseur();menuson.update=true;}
				else {
				menuoption.downcurseur();
				menuoption.update=true;
				}
			}
			else {
				if (menucrea.choix) { //si on est en train de choisir l'element
					menucrea.downcurseur();
				}
				else {map.downcurseur();}
			}
			
			break;
		case ENTER:
			if (!menucrea.choix) {
				menuoption.inmenuop=true;
				menuoption.positioncurseur=0;
				menuoption.update=true;
			}
			break;		

				
		default:
    		break;
	    }
		}
	}
	
	void stop() {
		increa = false;
		map.selectionne = map.plateau[51];
		map.rangcorner = 0;
		menucrea= new MenuCrea(map.referencecodeterrain,map.plateau[2500],positionxmenu);
		actualiservisu(); //a faire apres Menucrea
		update=true;
	}
	
	
}
