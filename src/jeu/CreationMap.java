package jeu;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import terrain.*;
import terrain.Void;
import unit.*;
import batiments.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CreationMap {
	Map map;
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**Boolean qui decrit si l'on doit rafraichir l'affichage ou non : true = il faut rafraichir */
	boolean update;
	/**Tableau d'element permettant de faire le lien entre le code de l'element et le type de terrain */
	ArrayList<Terrain> referencecodeterrain;
	/**le menu permettant de selectionner l'element a placer */
	Menucrea menucrea;
	boolean increa;
	Image menucache;
	String namesave;
	int[] mapcode;
	
	/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/** Recree la map a l'aide du tableau d'entiers de la sauvegarde. 
	 * Le lien est fait grace a referencecode, il est donc important de le maintenir a jour.
	 * 
	 * @param gc le contexte graphique
	 */
	CreationMap(GraphicsContext gc,String txt){
		
	/*~~~~~~TABLE REFERENCE CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
		referencecodeterrain = new ArrayList<Terrain>();
		referencecodeterrain.add(new Void(50)); //permet de faire le lien entre code et element
		referencecodeterrain.add(new Terre(50)); // /!\laisser void en premier !
		referencecodeterrain.add(new Marais(50));
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
		this.namesave = txt;
		increa = false;
		menucache = new Image("wood.jpg",400,600,false,false);
		map = new Map();
		map.selectionne = map.plateau[51];
		this.gc=gc;
		menucrea= new Menucrea(gc,referencecodeterrain,map.plateau[2500]);
		actualiservisu(); //a faire apres Menucrea
		map.nombrejoueur = map.equipe.size();
		map.affichageEquipe();
		update=true;
		
	}
	
	
	/**ajoute le terrain correspondant au bon code au rang k
	 * @param k
	 */
	void remaketerrain(int k, int codeS,Map map) {
		for (int j =1; j<referencecodeterrain.size();j++) {
			if (codeS%50 == j){
				map.plateau[k].terrain = referencecodeterrain.get(j); //on change la map
				if (increa) {
					mapcode[k] = mapcode[k] - (mapcode[k]%50) + j; //on change la sauvegarde
					
				}
			}
		}
	}
	
	/**ajoute l'unite correspondant au bon code au rang k
	 * @param k
	 */
	void remakeunite(int k, int codeS, Map map) {
		int codeunite = (codeS/50)%50;
		int joueur = (codeS/(50*50))%50;
		while (joueur >= map.equipe.size()) {
			map.equipe.add(new ArrayList<Unite>());
		}
		
		/*~~~~~~Partie a mettre a jour quand on ajoute des types d'unitees !~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (codeunite == 0) {
				map.plateau[k].unite = null;
			}
			else if (codeunite == 1) {
				map.addunite(k, new AbeilleSamourai(map.taillec,joueur),joueur);
				}
			else if (codeunite == 2) {
				map.addunite(k, new PapillonPsychique(map.taillec,joueur),joueur);
				}
			else if (codeunite == 3) {
				map.addunite(k, new Scarabe(map.taillec,joueur),joueur);
				}
			else if (codeunite == 4) {
				map.addunite(k, new Abeille(map.taillec,joueur),joueur);
				}
			/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (increa) {
				
				mapcode[k] = mapcode[k] - ((mapcode[k]/50)%50)*50 + codeunite*50; //on change l'unite dans la sauvegarde
				mapcode[k] = mapcode[k] - ((mapcode[k]/(50*50))%50)*50*50 + joueur*50*50; //de meme pour le joueur
				
			}
		}
	/**
	 * A METTRE A JOUR LORSQU'ON AJOUTE UN NOUVEAU TYPE D'UNITE
	 */
	int nombretotunite() {return 4;}
	
	/**ajoute le batiment correspondant au bon code au rang k
	 * @param k
	 */
	void remakebatiment(int k, int codeS, Map map) {
		int codebatiment = (codeS/(50*50*50))%50;
		int joueur = (codeS/(50*50*50*50))%50;
		if (codebatiment !=0) {
		/*~~~~~~Partie a mettre a jour quand on ajoute des types de batiments !~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (codebatiment == 1) {
				map.plateau[k].batiment= new Carregris(map.taillec,joueur);
				}
		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (increa) {
				mapcode[k] = mapcode[k] - ((mapcode[k]/125000)%50)*50 + codebatiment*125000; //on change le batiment dans la sauvegarde
				mapcode[k] = mapcode[k] - ((mapcode[k]/(6250000))%50)*50*50 + joueur*6250000; //de meme pour le joueur
			}
		}
	}	
	
	/**
	 * A METTRE A JOUR LORSQU'ON AJOUTE UN NOUVEAU TYPE DE BATIMENT
	 */
	int nombretotbatiment() {return 1;}
	
	/*_Mise a jour de l'affichage______________________________________________________________________________________________________ */	
	
	void update() {
		map.renderanim(gc); //animation des sprites
		if (update) { // on evite d'afficher toute la map a chaque fois, seulement quand c'est necessaire
			gc.drawImage(menucache, 600, 0); 
			map.render(gc);
			menucrea.render();
			update=false;
		}
	    map.curseurRender(gc); //on affiche le curseur tout a la fin (au dessus donc) et tout le temps car il ne s'agit que d'une image
		
	}
	
	void actualiservisu() {
		remaketerrain(2500,menucrea.codesave,map);
		remakebatiment(2500,menucrea.codesave,map);
		remakeunite(2500,menucrea.codesave,map);
		map.delunite(map.plateau[2500].unite, (menucrea.codesave/(50*50))%50);
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
		    	int codeS = menucrea.codesave;
		    	if (menucrea.choixtype == 0) {
		    		remaketerrain(map.selectionne.rang,codeS,map);
		    		
		    	}
		    	else if ((menucrea.choixtype == 2)&&(map.selectionne.batiment == null)&&!(map.selectionne.terrain instanceof Void)) {remakebatiment(map.selectionne.rang,codeS,map);}
		    	else if ((menucrea.choixtype == 1)&&(map.selectionne.unite == null)&&!(map.selectionne.terrain instanceof Void)) {remakeunite(map.selectionne.rang,codeS,map);}
	    	}
		    update=true;
	   		break;
	    case B: 
    		menucrea.choix=!menucrea.choix;
    		update=true;
    		break; 
	    case R :
	    	if(!menucrea.choix) {
		    	if (menucrea.choixtype == 0) {
		    		map.selectionne.terrain = referencecodeterrain.get(0); //on change visuellement
		    		mapcode[map.selectionne.rang] -= mapcode[map.selectionne.rang]%50; //et dans la sauvegarde
		    	}
		    	else if (menucrea.choixtype == 1) {
		    		if (map.selectionne.unite != null) {
		    			map.delunite(map.selectionne.unite, map.selectionne.unite.joueur);
		    			map.selectionne.unite = null; //on enleve l'unite visuellement et dans la sauvegarde
		    			mapcode[map.selectionne.rang] -= ((mapcode[map.selectionne.rang]/50)%50)*50; 
		    		}
		    	}
		    	else if (menucrea.choixtype == 2) {
		    		if (map.selectionne.batiment != null) {
		    			map.selectionne.batiment = null; //on enleve l'e batiment visuellement et dans la sauvegarde
		    			mapcode[map.selectionne.rang] -= ((mapcode[map.selectionne.rang]/125000)%50)*125000; 
		    		}
		    	}
	    	}
		    update=true; 
		    break;
		case LEFT: 
			if (menucrea.choix) {
				menucrea.leftcurseur(nombretotunite(),nombretotbatiment());
				actualiservisu();
				
			}
			else {map.leftcurseur();}
			update=true; 
			break;
		case RIGHT: 
			if (menucrea.choix) {
				menucrea.rightcurseur(nombretotunite(),nombretotbatiment());
				actualiservisu();
			}
			else {map.rightcurseur();}
			update=true;
			break;
		case UP:
			if (menucrea.choix) { //si on est en train de choisir l'element
				menucrea.upcurseur();
			}
			else {map.upcurseur();}
			update=true;
			break;
		case DOWN:
			if (menucrea.choix) { //si on est en train de choisir l'element
				menucrea.downcurseur();
			}
			else {map.downcurseur();}
			update=true;
			break;
		case ENTER:
			// on cree l'objet
			try {
				FileOutputStream fos = new FileOutputStream("creamap.ser"); // nom du fichier contenant la sauvegarde
				Sauvegardemap sauvegarde = new Sauvegardemap();
				sauvegarde.grillemap = mapcode;
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(sauvegarde);
				oos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		default:
    		break;
	    }
		}
	}
	
	void stop() {
		increa = false;
		menucrea= new Menucrea(gc,referencecodeterrain,map.plateau[2500]);
		actualiservisu(); //a faire apres Menucrea
		map.nombrejoueur = map.equipe.size();
		map.affichageEquipe();
		update=true;
	}
	
	
}
