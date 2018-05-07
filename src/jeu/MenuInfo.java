package jeu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MenuInfo {
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/**Carte du jeu */
	Map map;
	/**Image */
	Image[] images;
	
	MenuInfo(GraphicsContext gc, Map map) {
		this.gc = gc;
		this.map = map;
	}
	
	void MenuInforender() {
		Case selectionne = map.selectionne;
		if (selectionne != null) {
		if (selectionne.unite!=null) {
			int animcompteur= selectionne.unite.animcompteur;
			int maxcompteur = selectionne.unite.maxcompteur;
			this.images = selectionne.unite.images;
			int k = animcompteur / (maxcompteur/images.length);
			if (k >= images.length) {
				animcompteur = 0;
				k=0;
			}
			gc.drawImage(images[k],650,450);
			
			String txt = "attaque : " + map.selectionne.unite.dmg;
	        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt, 750, 450);
	        gc.strokeText(txt, 750, 450);
	        
	        String txt2 = "vie : " + map.selectionne.unite.pv;
	        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt2, 750, 500);
	        gc.strokeText(txt2, 750, 500);
	        
	        int porteemin = map.selectionne.unite.portee[0] + 1;
	        if (porteemin != 1) { //affichage dans menuinfo de la portee avec un minimum
	        	String txt3 = "portee : " + porteemin + "~" + map.selectionne.unite.portee[1];
	        	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		        gc.setFill(Color.BISQUE);
		        gc.setStroke(Color.BLACK);
		        gc.setLineWidth(1);
		        gc.fillText(txt3, 750, 550);
		        gc.strokeText(txt3, 750, 550);
	        } 
	        else { //affichage dans menuinfo de la portee sans minimum
	        	String txt3 = "portee : " + map.selectionne.unite.portee[1];
	        	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		        gc.setFill(Color.BISQUE);
		        gc.setStroke(Color.BLACK);
		        gc.setLineWidth(1);
		        gc.fillText(txt3, 750, 550);
		        gc.strokeText(txt3, 750, 550);
	        }
	        gc.fillText(selectionne.unite.toString(), 640, 400);
	        gc.strokeText(selectionne.unite.toString(), 640, 400);
	        
	        int ratiopvpvmax = (int) 100*map.selectionne.unite.pv/map.selectionne.unite.pvmax;
			gc.setFill(Color.LIMEGREEN);
	        gc.fillRect(920, 450, 15, 100);
	        gc.setFill(Color.RED);
	        gc.fillRect(920, 450, 15, 100-ratiopvpvmax);
	        
		} else if (selectionne.batiment!=null) {
			this.images = selectionne.batiment.images;
			gc.drawImage(images[0],650,450);
	        gc.fillText(selectionne.batiment.toString(), 640, 400);
	        gc.strokeText(selectionne.batiment.toString(), 640, 400);
		} else {
			this.images = selectionne.terrain.images;
			gc.drawImage(images[0],650,450);
	        gc.fillText(selectionne.terrain.toString(), 640, 400);
	        gc.strokeText(selectionne.terrain.toString(), 640, 400);
		}
			
		}
	}
	
}
