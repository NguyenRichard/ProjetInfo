package jeu;

import batiments.Portal;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Menuinvocation {
	/**Image de fond du menuinvocation */
	Image fond;
	/**Image du curseur du menuinvocation */
	Image curseur;
	GraphicsContext gc;
	Portal portail;
	int positioncurseur;
	int positionxmenu;
	int positionymenu;
	
	
	
	Menuinvocation(GraphicsContext gc, Portal portail) {
		
		fond = new Image("fondmenuinvoc.png", 1100, 750, false, false);
		curseur = new Image("curseurinvoc.png", 730, 75, false, false );
		this.portail = portail;
		this.gc = gc;
		positioncurseur = 0;
		positionxmenu=25;
		positionymenu=25;
	}
	
	void render() {
		gc.drawImage(fond, 0, 0);
		gc.drawImage(curseur, 10 ,25+positioncurseur*100);
        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        gc.setLineWidth(1);
		int compteur=0;
		for (Unite cur : portail.listeinvoc) {
			cur.render(gc, positionxmenu, positionymenu+compteur);
			gc.setFill(Color.WHITE);
	        gc.fillText(cur.toString(), positionxmenu+100, positionymenu+50+compteur );
	        gc.strokeText(cur.toString(), positionxmenu+100, positionymenu+50+compteur );
			compteur+=100;
		}
		if(portail.uniteainvoque!=null) {
			portail.uniteainvoque.render(gc, 885, 660);
		}
	}
	
	void downcurseur() {
		positioncurseur += 1;
		if (positioncurseur >= portail.listeinvoc.size()) {
			positioncurseur = 0;
		}
	}
	void upcurseur() {
		positioncurseur -= 1;
		if (positioncurseur <= -1) {
			positioncurseur = portail.listeinvoc.size()-1;
		}
	}
	
	void changeinvoque() {
		portail.uniteainvoque=portail.listeinvoc.get(positioncurseur);
		portail.images=portail.images2;
		portail.maxcompteur=portail.maxcompteur2;
		
	}
	
}
