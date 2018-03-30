package jeu;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Menuprinc {
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**L'image de fond du menu*/
	Image fond;
	
	/* Constructeur de Menu*/
	Menuprinc(GraphicsContext gc){
		fond = new Image("InsectWorldWar.png", 1000, 600, false, false);
		this.gc=gc;
	}
	
	void render() {
		gc.drawImage(fond, 0, 0);
	}
}
