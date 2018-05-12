package jeu;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import terrain.*;
import terrain.Void;
import unit.*;
import batiments.*;
import java.util.Scanner; 


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
	/**Entier a partir du quel affiche le menu lateral droit**/
    int positionxmenu;
	
	/*_Methode de base de l'objet_______________________________________________________________________________________________________ */
	
	/** Recree la map a l'aide du tableau d'entiers de la sauvegarde. 
	 * Le lien est fait grace a referencecode, il est donc important de le maintenir a jour.
	 * 
	 * @param gc le contexte graphique
	 */
	CreationMap(GraphicsContext gc,String txt,int width,int height,boolean ingame){
		
		map = new Map();
		
	/*~~~~~~TABLE REFERENCE CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
		referencecodeterrain = new ArrayList<Terrain>();
		referencecodeterrain.add(new Void(map.taillec)); //permet de faire le lien entre code et element
		referencecodeterrain.add(new Terre(map.taillec)); // /!\laisser void en premier !
		referencecodeterrain.add(new Marais(map.taillec));
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
		this.namesave = txt;
		positionxmenu=map.taillec*map.nombrecaseaffichee;
		increa = false;
		menucache = new Image("wood.jpg",width-map.taillec*map.nombrecaseaffichee,height,false,false);
		map.selectionne = map.plateau[51];
		this.gc=gc;
		menucrea= new Menucrea(gc,referencecodeterrain,map.plateau[2500],positionxmenu);
		actualiservisu(); //a faire apres Menucrea
		map.affichageEquipe();
		update=true;
		
	}
	
	/**gestion de la creation d'un joueur si le code du rang k contient un numero
	 * de joueur encore non rencontre
	 * IL FAUT TOUJOURS LE FAIRE AVANT remakeunite et remaketerrain !
	 */
	void remakejoueur(int k, int codeS, Map map) {
		int joueurunite = (codeS/(50*50))%50;
		if (!(map.joueurs.get(joueurunite).isalive)){ //si le joueur n'est pas en vie lors de la creation c'est qu'il n'a pas encore été personnaliser
			map.joueurs.get(joueurunite).isalive = true;
			Scanner saisieUtilisateur = new Scanner(System.in); 
			System.out.println("Veuillez saisir le nom du joueur " + joueurunite +  " :");
			String str = saisieUtilisateur.next();
			map.joueurs.get(joueurunite).changename(str);
			System.out.println("Veuillez saisir une armee de "+map.joueurs.get(joueurunite)+ " :");
			map.joueurs.get(joueurunite).typearmee=saisieUtilisateur.nextInt();
		}
		int joueurbatiment = (codeS/(50*50*50*50))%50;
		if (!(map.joueurs.get(joueurbatiment).isalive)){ //si le joueur n'est pas en vie lors de la creation c'est qu'il n'a pas encore été personnaliser
			map.joueurs.get(joueurbatiment).isalive = true;
			Scanner saisieUtilisateur = new Scanner(System.in); 
			System.out.println("Veuillez saisir le nom du joueur " + joueurbatiment +  " :");
			String str = saisieUtilisateur.next();
			map.joueurs.get(joueurbatiment).changename(str);
			System.out.println("Veuillez saisir une armee de "+map.joueurs.get(joueurunite)+ " :");
			map.joueurs.get(joueurbatiment).typearmee=saisieUtilisateur.nextInt();
		}
	}
	
	
	/**ajoute le terrain correspondant au bon code au rang k
	 * @param k
	 */
	void remaketerrain(int k, int codeS,Map map) {
		map.plateau[k].terrain = referencecodeterrain.get(0); //le vide par défaut
		for (int j =1; j<referencecodeterrain.size();j++) {
			if (codeS%50 == j){
				map.plateau[k].terrain = referencecodeterrain.get(j); //on change la map
				if (increa) {
					mapcode[k] = mapcode[k] - (mapcode[k]%50) + j; //on change la sauvegarde
					
				}
			break; //on aura une seule correspondance, pas besoin de faire plus de tests
			}

		}
	}
	
	/**ajoute l'unite correspondant au bon code au rang k
	 * @param k
	 */
	void remakeunite(int k, int codeS, Map map,boolean ingame) {
		int codeunite = (codeS/50)%50;
		int joueur = (codeS/(50*50))%50;
		
		/*~~~~~~Partie a mettre a jour quand on ajoute des types d'unitees !~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (codeunite == 0) {
				map.plateau[k].unite = null;
			}
			else {
				if (codeunite == 1) {
					map.addunite(k, new AbeilleSamourai(map.taillec,joueur)); //on ajoute l'unite sur la map
				}
				else if (codeunite == 2) {
					map.addunite(k, new PapillonPsychique(map.taillec,joueur));
					}
				else if (codeunite == 3) {
					map.addunite(k, new Scarabe(map.taillec,joueur));
					}
				else if (codeunite == 4) {
					map.addunite(k, new Abeille(map.taillec,joueur));
					}
				else if (codeunite == 5) {
					map.addunite(k, new Fourmis(map.taillec,joueur));
				}
				else if (codeunite == 6) {
					map.addunite(k, new Moustique(map.taillec,joueur));
				}
				else if (codeunite == 7) {
					map.addunite(k,new EpeisteVolant(map.taillec,joueur));
				}
				else if (codeunite == 8) {
					map.addunite(k,new TankSquelette(map.taillec,joueur));
				}
				else if (codeunite == 9) {
					map.addunite(k,new SkeletonSoldier(map.taillec,joueur));
				}
				else if (codeunite == 10) {
					map.addunite(k,new ArcherSquelette(map.taillec,joueur));
				}
				if (ingame) {
					map.joueurs.get(joueur).add(map.plateau[k].unite); //on ajoute l'unité à la liste d'unités du bon joueur si on est en jeu
				}
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
	int nombretotunite() {return 10;}
	
	/**ajoute le batiment correspondant au bon code au rang k
	 * @param k
	 */
	void remakebatiment(int k, int codeS, Map map,boolean ingame) {
		int codebatiment = (codeS/(50*50*50))%50;
		int joueur = (codeS/(50*50*50*50))%50;
		if (codebatiment !=0) {
		/*~~~~~~Partie a mettre a jour quand on ajoute des types de batiments !~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (codebatiment == 1) {
				map.plateau[k].batiment = new Portal(map.taillec,joueur,map.joueurs.get(joueur).typearmee);
				}
			else if (codebatiment == 2) {
				map.plateau[k].batiment = new Crystal(map.taillec,joueur);
			}

		/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	*/
			if (increa) {
				mapcode[k] = mapcode[k] - ((mapcode[k]/125000)%50)*50 + codebatiment*125000; //on change le batiment dans la sauvegarde
				mapcode[k] = mapcode[k] - ((mapcode[k]/(6250000))%50)*50*50 + joueur*6250000; //de meme pour le joueur
			}
			if (ingame&&(joueur < 4)) {
				map.joueurs.get(joueur).add(map.plateau[k]); //on ajoute le batiment à la liste d'unités du bon joueur si on est en jeu et que le batiment n'est pas neutre
			}
		}
		else {
			map.plateau[k].batiment = null;
		}
	}	
	
	/**
	 * A METTRE A JOUR LORSQU'ON AJOUTE UN NOUVEAU TYPE DE BATIMENT
	 */
	int nombretotbatiment() {return 2;}
	
	/*_Mise a jour de l'affichage______________________________________________________________________________________________________ */	
	
	void update() {
		map.renderanim(gc); //animation des sprites
		if (update) { // on evite d'afficher toute la map a chaque fois, seulement quand c'est necessaire
			gc.drawImage(menucache, positionxmenu, 0); 
			map.render(gc);
			menucrea.render();
			update=false;
		}
	    map.curseurRender(gc); //on affiche le curseur tout a la fin (au dessus donc) et tout le temps car il ne s'agit que d'une image
		
	}
	
	void actualiservisu() {
		remaketerrain(2500,menucrea.codesave,map);
		remakebatiment(2500,menucrea.codesave,map,false);
		remakeunite(2500,menucrea.codesave,map,false);
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
		    	else if ((menucrea.choixtype == 2)&&(map.selectionne.batiment == null)&&!(map.selectionne.terrain instanceof Void)) {remakebatiment(map.selectionne.rang,codeS,map,false);}
		    	else if ((menucrea.choixtype == 1)&&(map.selectionne.unite == null)&&!(map.selectionne.terrain instanceof Void)) {remakeunite(map.selectionne.rang,codeS,map,false);}
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
		map.selectionne = map.plateau[51];
		menucrea= new Menucrea(gc,referencecodeterrain,map.plateau[2500],positionxmenu);
		actualiservisu(); //a faire apres Menucrea
		update=true;
	}
	
	
}
