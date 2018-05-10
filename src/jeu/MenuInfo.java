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
	int positionxmenuinfo;
	int positionymenuinfo;
	
	MenuInfo(GraphicsContext gc, Map map, int positionxmenu) {
		this.gc = gc;
		this.map = map;
		this.positionxmenuinfo=positionxmenu+10;
		positionymenuinfo=500;
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
			gc.drawImage(images[k],positionxmenuinfo+10,positionymenuinfo+50);
			
			String txt = "attaque : " + map.selectionne.unite.dmg;
	        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt, positionxmenuinfo*1.15, positionymenuinfo+50);
	        gc.strokeText(txt, positionxmenuinfo*1.15, positionymenuinfo+50);
	        
	        String txt2 = "vie : " + map.selectionne.unite.pv;
	        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt2, positionxmenuinfo*1.15, positionymenuinfo+100);
	        gc.strokeText(txt2, positionxmenuinfo*1.15, positionymenuinfo+100);
	        
	        int porteemin = map.selectionne.unite.portee[0] + 1;
	        if (porteemin != 1) { //affichage dans menuinfo de la portee avec un minimum
	        	String txt3 = "portee : " + porteemin + "~" + map.selectionne.unite.portee[1];
	        	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		        gc.setFill(Color.BISQUE);
		        gc.setStroke(Color.BLACK);
		        gc.setLineWidth(1);
		        gc.fillText(txt3, positionxmenuinfo*1.15, positionymenuinfo+150);
		        gc.strokeText(txt3, positionxmenuinfo*1.15, positionymenuinfo+150);
	        } 
	        else { //affichage dans menuinfo de la portee sans minimum
	        	String txt3 = "portee : " + map.selectionne.unite.portee[1];
	        	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		        gc.setFill(Color.BISQUE);
		        gc.setStroke(Color.BLACK);
		        gc.setLineWidth(1);
		        gc.fillText(txt3, positionxmenuinfo*1.15, positionymenuinfo+150);
		        gc.strokeText(txt3, positionxmenuinfo*1.15, positionymenuinfo+150);
	        }
	        gc.fillText(selectionne.unite.toString(), positionxmenuinfo*1.03, positionymenuinfo);
	        gc.strokeText(selectionne.unite.toString(), positionxmenuinfo*1.03, positionymenuinfo);
	        
	        int ratiopvpvmax = (int) 100*map.selectionne.unite.pv/map.selectionne.unite.pvmax;
			gc.setFill(Color.LIMEGREEN);
	        gc.fillRect(positionxmenuinfo*1.4, positionymenuinfo+50, 15, 100);
	        gc.setFill(Color.RED);
	        gc.fillRect(positionxmenuinfo*1.4, positionymenuinfo+50, 15, 100-ratiopvpvmax);
	        
		} else if (selectionne.batiment!=null) {
			this.images = selectionne.batiment.images;
			gc.drawImage(images[0],positionxmenuinfo*1.04,positionymenuinfo+50);
	        gc.fillText(selectionne.batiment.toString(), positionxmenuinfo*1.03, positionymenuinfo);
	        gc.strokeText(selectionne.batiment.toString(), positionxmenuinfo*1.03, positionymenuinfo);
		} else {
			this.images = selectionne.terrain.images;
			gc.drawImage(images[0],positionxmenuinfo*1.04,positionymenuinfo+50);
	        gc.fillText(selectionne.terrain.toString(), positionxmenuinfo*1.03, positionymenuinfo);
	        gc.strokeText(selectionne.terrain.toString(), positionxmenuinfo*1.03, positionymenuinfo);
		}
			
		}
	}
	
}
