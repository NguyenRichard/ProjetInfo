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
	/**Image du popup de confirmation de la selection d'une unite */
	Image confirmation;
	/** Cursuer du popup confiramtion */
	Image curseurconf;
	/**Contexte graphique dans lequel on affiche le jeu */
	GraphicsContext gc;
	/** Portail ou se deroule l'invocation*/
	Portal portail;
	/** Position du curseur */
	int positioncurseur;
	/** Position du menu sur l'axe des abscisses*/
	int positionxmenu;
	/** Position du menu sur l'axe des ordonnées*/
	int positionymenu;
	/** Boolean qui décrit si on est en train de confirmer la selection d'une unite ou non*/
	boolean confirmationencours;
	/** Position du curseur dans le popup de confirmation*/
	int positioncurseurconf;
	
	
	
	Menuinvocation(GraphicsContext gc, Portal portail) {
		
		fond = new Image("menuinvoc/fondmenuinvoc.png", 1100, 750, false, false);
		curseur = new Image("menuinvoc/curseurinvoc.png", 730, 75, false, false );
		curseurconf= new Image("menuinvoc/confirmationcurseur.png", 90, 40,false,false);
		confirmation= new Image("menuinvoc/confirmation.png",250,100,false,false);
		this.portail = portail;
		this.gc = gc;
		positioncurseur = 0;
		positionxmenu=25;
		positionymenu=25;
		positioncurseurconf=0;
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
		portail.render(gc, 885, 660);
		if(portail.uniteainvoque!=null) {
			portail.uniteainvoque.render(gc, 885, 660);
		}
		if (confirmationencours) {
			this.renderconfirmation();
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
	
	void rightcurseurconf() {
		positioncurseurconf+=1;
		if (positioncurseurconf>=2) {
			positioncurseurconf=0;
		}
	}
	
	void leftcurseurconf() {
		positioncurseurconf-=1;
		if (positioncurseurconf<=-1) {
			positioncurseurconf=1;
		}
	}
	
	void renderconfirmation() {
		gc.drawImage(confirmation, 350, 350);
		if(positioncurseurconf==0) {
			gc.drawImage(curseurconf, 360, 400);
		}
		else {
			gc.drawImage(curseurconf, 480, 400);
		}
	}
	
	void changeinvoque(Joueur joueur) {
		Unite unite = portail.listeinvoc.get(positioncurseur);
		if ( 0 <= joueur.ressources-unite.cost ) {
			portail.uniteainvoque=unite;
			portail.images=portail.images2;
			portail.maxcompteur=portail.maxcompteur2;	
			joueur.ressources-=unite.cost;
		}
		
	}
	
}
