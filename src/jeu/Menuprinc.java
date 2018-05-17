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
	/**L'image de fond du menu*/
	Image fond;
	int positioncurseur;
	Jeu game;
	CreationMap crea;
   	boolean update;
	FXDialogs fx;
   	
	/* Constructeur de Menu*/
	Menuprinc(Jeu game,CreationMap crea,int width, int height){
		fond = new Image("InsectWorldWar.png", width, height, false, false);
		this.game=game;
		this.crea=crea;
		update = true;
		fx = new FXDialogs();
	}
	
	void render(GraphicsContext gc) {
		gc.drawImage(fond, 0, 0);
		
	}
	
	void touch(KeyCode code) throws CloneNotSupportedException {

		switch(code) {
		case A:
			if (positioncurseur == 0) {
				File f = new File(crea.namesave); // nom du fichier contenant la sauvegarde
				if (f.exists()){
					game.map.remakemap(f,true);
					game.ingame=true;
					game.map.affichageEquipe();
					game.map.joueurs.get(game.entrainjouer).actiondesbatiments();
				}
				else {
					fx.showWarning("Attention", "Le fichier n'existe pas");
					System.out.println("Le fichier n'existe pas");
				}

			}
			else if (positioncurseur == 1) {
				File f = new File(crea.namesave); // nom du fichier contenant la sauvegarde
				if (f.exists()){
					crea.mapcode=crea.map.remakemap(f,false);
				}
				else {
					Sauvegardemap sauvegarde = new Sauvegardemap(); crea.mapcode = sauvegarde.grillemap; // si nouveau 
				}
				crea.increa=true;
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

	    
	    /*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
		
		void upcurseur() {if (positioncurseur != 0) {positioncurseur -= 1;}}
		void downcurseur() {if (positioncurseur != 1){positioncurseur += 1;}}	    

}
