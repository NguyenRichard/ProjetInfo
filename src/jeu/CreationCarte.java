package jeu;

import javax.sound.sampled.Clip;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import son.Son;
import terrain.Vide;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CreationCarte {
	Carte carte;
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non : true = il faut rafraichir */
	boolean update;
	/**le menu permettant de selectionner l'element a placer */
	MenuCrea menucrea;
	/**booleen vrai si on est dans la partie modification de la sauvegarde et faux sinon. Permet de ne prendre en compte
	 * l'appuie sur les touches du clavier qu'au moment voulu*/
	boolean increa;
	/**l'image de fond du menu sur la droite */
	Image menucache;
	/**Nom du fichier contenant la sauvegarde en train d'etre manipulee */
	String namesave;
	/**le tableau d'entier initialement extrait du fichier contenant la sauvegarde et qu'on va modifier*/
	int[] cartecode;
	/**Entier a partir du quel affiche le menu lateral droit**/
    int positionxmenu;
    /**Le menu pour sauvegarder, revenir au menu principal et ouvrir le menu son */
    MenuOption menuoption;
    /**Le menu pour gerer le son de la musique d'ambiance et les effets sonors */
    MenuSon menuson;
    /**Pour gerer le son d'ambiance en fond */
    Son sd;
    /**Pour gerer le son d'ambiance en fond */
    Clip clip;
	
	/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/** Initialisation des champs de CreationMap, qui sera utilise si on desir modifier la sauvegarde
	 * 
	 * @param gc le contexte graphique dans lequel on affiche l'etat du jeu
	 * @param txt le nom du fichier contenant la sauvegarde
	 * @param width longueur en pixel de la fenetre de jeu
	 * @param height hauteur en pixel de la fenetre de jeu
	 */
	CreationCarte(GraphicsContext gc,String txt,int width,int height, Son sd, Clip clip){
		
		carte = new Carte();
		
		this.namesave = txt;
		positionxmenu=carte.taillec*carte.nombrecaseaffichee;
		increa = false;
		menucache = new Image("fondmenu.png",width-carte.taillec*carte.nombrecaseaffichee,height,false,false);
		carte.selectionne = carte.plateau[51];
		this.gc=gc;
		menucrea= new MenuCrea(carte.referencecodeterrain,carte.plateau[2500],positionxmenu);
		actualiservisu(); //a faire apres Menucrea
		carte.affichageEquipe();
		update=true;
		menuoption = new MenuOption(1,width,height);
		menuson = new MenuSon(width,height, clip, sd);
		this.sd=sd;
		this.clip=clip;
	}
	
	/**Modifie la partie du code de la case correpondant au batiment conformement a codeS
	 * 
	 * @param rang le rang de la case pour laquelle on veut modifier la partie du code correspondant au batiment
	 * @param codeS la valeur du tableau d'entier de la sauvegarde correspondant a l'etat de la case en question
	 */
	void changebatimentsave(int rang, int codeS) {
		int codebatiment = (codeS/(50*50*50))%50;
		int joueur = (codeS/(50*50*50*50))%50;
		assert rang >= 0;
		assert rang < 2501;
		assert joueur < 5;
		cartecode[rang] = cartecode[rang] - ((cartecode[rang]/125000)%50)*50 + codebatiment*125000; //on change le batiment dans la sauvegarde
		cartecode[rang] = cartecode[rang] - ((cartecode[rang]/(6250000))%50)*50*50 + joueur*6250000; //de meme pour le joueur
	}
	
	/**Modifie la partie du code de la case correpondant a l'unite conformement a codeS
	 * 
	 * @param rang le rang de la case pour laquelle on veut modifier la partie du code correspondant a l'unite
	 * @param codeS la valeur du tableau d'entier de la sauvegarde correspondant a l'etat de la case en question
	 */
	void changeunitesave(int rang, int codeS) {
		int codeunite = (codeS/50)%50;
		int joueur = (codeS/(50*50))%50;
		//System.out.println(joueur);
		assert rang >= 0;
		assert rang < 2501;
		assert joueur < 5;
		cartecode[rang] = cartecode[rang] - ((cartecode[rang]/50)%50)*50 + codeunite*50; //on change l'unite dans la sauvegarde
		cartecode[rang] = cartecode[rang] - ((cartecode[rang]/(50*50))%50)*50*50 + joueur*50*50; //de meme pour le joueur

	}
	
	/**Modifie la partie du code de la case correpondant au terrain conformement a codeS
	 * 
	 * @param rang le rang de la case pour laquelle on veut modifier la partie du code correspondant au terrain
	 * @param codeS la valeur du tableau d'entier de la sauvegarde correspondant a l'etat de la case en question
	 */
	
	void changeterrainsave(int rang, int codeS) {
		cartecode[rang] = cartecode[rang] - (cartecode[rang]%50) + carte.remaketerrain(rang,codeS);; //on change la sauvegarde
	}
	
	/*_Mise a jour de l'affichage______________________________________________________________________________________________________ */	
	
	void update() {
		if(!menuoption.inmenuop) {
			carte.renderanim(gc); //animation des sprites
			if (update) { // on evite d'afficher toute la carte a chaque fois, seulement quand c'est necessaire
				carte.render(gc);
				update=false;
			}
			gc.drawImage(menucache, positionxmenu, 0);
			menucrea.render(gc);
		    carte.curseurRender(gc); //on affiche le curseur tout a la fin (au dessus donc) et tout le temps car il ne s'agit que d'une image	
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
	
	/**Actualise la partie de la sauvegarde correspondant a la previsualisation des unitees (utilisee dans menuCrea)
	 */
	void actualiservisu() {
		if (increa) {
			changeterrainsave(2500,menucrea.codesave);
			
			carte.remakebatiment(2500,menucrea.codesave,false);
			changebatimentsave(2500,menucrea.codesave);
			
			carte.remakeunite(2500,menucrea.codesave,false);
			changeunitesave(2500,menucrea.codesave);
		}
		else {
			carte.remaketerrain(2500, menucrea.codesave);
			carte.remakebatiment(2500, menucrea.codesave, false);
			carte.remakeunite(2500,menucrea.codesave,false);
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
	    					FileOutputStream fos = new FileOutputStream("creacarte.ser"); // nom du fichier contenant la sauvegarde
	    					SauvegardeCarte sauvegarde = new SauvegardeCarte();
	    					sauvegarde.grillecarte = cartecode;
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
			    		changeterrainsave(carte.selectionne.rang,codeS);
			    	}
			    	else if ((menucrea.choixtype == 2)&&(carte.selectionne.batiment == null)&&!(carte.selectionne.terrain instanceof Vide)) {
			    		carte.remakebatiment(carte.selectionne.rang,codeS,false);
			    		changebatimentsave(carte.selectionne.rang,codeS);
			    	}
			    	else if ((menucrea.choixtype == 1)&&(carte.selectionne.unite == null)&&!(carte.selectionne.terrain instanceof Vide)) {
			    		carte.remakeunite(carte.selectionne.rang,codeS,false); 
			    		changeunitesave(carte.selectionne.rang,codeS);
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
		    		carte.selectionne.terrain = carte.referencecodeterrain.get(0); //on change visuellement
		    		cartecode[carte.selectionne.rang] -= cartecode[carte.selectionne.rang]%50; //et dans la sauvegarde
		    	}
		    	else if (menucrea.choixtype == 1) {
		    		if (carte.selectionne.unite != null) {
		    			carte.delunite(carte.selectionne.unite, carte.selectionne.unite.joueur);
		    			carte.selectionne.unite = null; //on enleve l'unite visuellement et dans la sauvegarde
		    			cartecode[carte.selectionne.rang] -= ((cartecode[carte.selectionne.rang]/50)%50)*50; 
		    			cartecode[carte.selectionne.rang] -= ((cartecode[carte.selectionne.rang]/(50*50))%50)*50*50;
		    		}
		    	}
		    	else if (menucrea.choixtype == 2) {
		    		if (carte.selectionne.batiment != null) {
		    			carte.selectionne.batiment = null; //on enleve l'e batiment visuellement et dans la sauvegarde
		    			cartecode[carte.selectionne.rang] -= ((cartecode[carte.selectionne.rang]/125000)%50)*125000; 
		    			cartecode[carte.selectionne.rang] -= ((cartecode[carte.selectionne.rang]/(125000*50))%50)*125000*50; 
		    		}
		    	}
	    	}
		    break;
		case LEFT: 
			if(!menuoption.inmenuop) {
				if (menucrea.choix) {
					menucrea.leftcurseur(carte.nombretotunite(),carte.nombretotbatiment());
					actualiservisu();
					
				}
				else {carte.leftcurseur();}
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
					menucrea.rightcurseur(carte.nombretotunite(),carte.nombretotbatiment());
					actualiservisu();
				}
				else {carte.rightcurseur();}
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
				else {carte.upcurseur();}
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
				else {carte.downcurseur();}
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
	
	/**Effetue toutes les actions sur les champs de CreationMap de maniere a pouvoir revenir au menu principal
	 */
	void stop() {
		increa = false;
		carte.selectionne = carte.plateau[51];
		carte.rangcorner = 0;
		menucrea= new MenuCrea(carte.referencecodeterrain,carte.plateau[2500],positionxmenu);
		actualiservisu(); //a faire apres Menucrea
		update=true;
	}
	
	
}
