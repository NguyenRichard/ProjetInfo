package jeu;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Menuprinc {
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**L'image de fond du menu*/
	Image fond;
	boolean inmenuprin;
	int positioncurseur;
	Jeu game;
	CreationMap crea;
   	boolean update;
	
	/* Constructeur de Menu*/
	Menuprinc(GraphicsContext gc,Jeu game,CreationMap crea,int width, int height){
		fond = new Image("InsectWorldWar.png", width, height, false, false);
		this.gc=gc;
		this.game=game;
		this.crea=crea;
		this.inmenuprin = true;
		update = true;
	}
	
	void render() {
		gc.drawImage(fond, 0, 0);
		
	}
	
	void touch(KeyCode code) throws CloneNotSupportedException {
		if (inmenuprin) {
			    switch(code) {
			    case A:
			    		if (positioncurseur == 0) {
			    			File f = new File(crea.namesave); // nom du fichier contenant la sauvegarde
			    			if(f.exists()) { 
			    				try { // si la sauvegarde existe on reconstruit la map a l'aide du code
			    					FileInputStream fis = new FileInputStream(crea.namesave);
			    					ObjectInputStream ois = new ObjectInputStream(fis);
			    					Sauvegardemap sauvegarde = (Sauvegardemap) ois.readObject();
			    					for(int k=0; k<2500; k++) {
			    						int codeS = sauvegarde.grillemap[k];
			    						crea.remakejoueur(k,codeS,game.map);
			    						crea.remaketerrain(k,codeS,game.map);
			    						crea.remakebatiment(k,codeS,game.map,true);
			    						crea.remakeunite(k,codeS,game.map,true);
			    					}
			    					ois.close();
			    				} catch (FileNotFoundException e) {
			    					e.printStackTrace();
			    				} catch (IOException e) {
			    					e.printStackTrace();
			    				}catch (ClassNotFoundException e) {
			    					e.printStackTrace();
			    				}
			    				game.ingame=true;
			    				game.map.affichageEquipe();
				    			game.map.selectionne = game.map.plateau[51];
			    				game.map.render(gc);
			    				game.map.joueurs.get(game.entrainjouer).actiondesbatiments();
			    				inmenuprin=false;
			    				
			    			}else {System.out.println("Le fichier n'existe pas");}
			    			
			    		}
			    		else if (positioncurseur == 1) {
			    			File f = new File(crea.namesave); // nom du fichier contenant la sauvegarde
			    			if(f.exists()) { 
			    				try { // si la sauvegarde existe on reconstruit la map a l'aide du code
			    					FileInputStream fis = new FileInputStream(crea.namesave);
			    					ObjectInputStream ois = new ObjectInputStream(fis);
			    					Sauvegardemap sauvegarde = (Sauvegardemap) ois.readObject();
			    					crea.mapcode = sauvegarde.grillemap;
			    					for(int k=0; k<2500; k++) {
				    						int codeS = crea.mapcode[k];
				    						crea.remaketerrain(k,codeS,crea.map);
				    						crea.remakebatiment(k,codeS,crea.map,false);
				    						crea.remakeunite(k,codeS,crea.map,false);
			    					}
			    					ois.close();
			    				} catch (FileNotFoundException e) {
			    					e.printStackTrace();
			    				} catch (IOException e) {
			    					e.printStackTrace();
			    				}catch (ClassNotFoundException e) {
			    					e.printStackTrace();
			    				}
			    			
			    			}else {Sauvegardemap sauvegarde = new Sauvegardemap(); crea.mapcode = sauvegarde.grillemap;} // si nouveau nom on cree une nouvelle map
			    			crea.map.render(gc);
	        				crea.increa = true;
	        				inmenuprin=false;
			    		}
			    		break; 
				case UP: 
					this.upcurseur();
					break; 
				case DOWN: 
					this.downcurseur();
					break; 
			    default:
		    		break;
			    
			    }
		    }
		else {
				switch(code) {
				case Z:
					if (game.ingame) {
						game.fin();
						gc.clearRect(0, 0, 1000, 600);
						inmenuprin=true;
						this.render();
					}
					if (crea.increa) {
						crea.stop();
						gc.clearRect(0, 0, 1000, 600);
						inmenuprin=true;
						this.render();
					}
					update=true;
					break;
				default:
					break;
					
				}
		}
	}
	    
	    /*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
		
		void upcurseur() {if (positioncurseur != 0) {positioncurseur -= 1; update = true;}}
		void downcurseur() {if (positioncurseur != 1){positioncurseur += 1; update = true;}}	    

}
