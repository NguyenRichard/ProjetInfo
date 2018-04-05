package jeu;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	Menuprinc(GraphicsContext gc,Jeu game,CreationMap crea){
		fond = new Image("InsectWorldWar.png", 1000, 600, false, false);
		this.gc=gc;
		this.game=game;
		this.crea=crea;
		this.inmenuprin = true;
	}
	
	void render() {
		gc.drawImage(fond, 0, 0);
		
	}
	
	void touch(KeyCode code) {
		if (inmenuprin) {
			    switch(code) {
			    case A:
			    		if (positioncurseur == 0) {
		    		    	game.map.render(gc);
		    		    	game.ingame = true;
		    		    	inmenuprin=false;
			    		}
			    		else if (positioncurseur == 1) {
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
						game.fin(crea.map);
						gc.clearRect(0, 0, 1000, 600);
						inmenuprin=true;
						this.render();
					}
					if (crea.increa) {
						crea.stop(crea.namesave);
						gc.clearRect(0, 0, 1000, 600);
						inmenuprin=true;
						this.render();
					}
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
