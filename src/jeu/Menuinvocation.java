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
	
	
	Menuinvocation(Portal portail) {
		
		fond = new Image("menuinvoc/fondmenuinvoc.png", 1100, 750, false, false);
		curseur = new Image("menuinvoc/curseurinvoc.png", 730, 75, false, false );
		curseurconf= new Image("menuinvoc/confirmationcurseur.png", 90, 40,false,false);
		confirmation= new Image("menuinvoc/confirmation.png",250,100,false,false);
		this.portail = portail;
		positioncurseur = 0;
		positionxmenu=25;
		positionymenu=25;
		positioncurseurconf=0;
	}
	
	void render(GraphicsContext gc) {
		gc.drawImage(fond, 0, 0);
		gc.drawImage(curseur, 10 ,25+positioncurseur*100);
        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        gc.setLineWidth(1);
		int compteur=0;
		for (Unite cur : portail.listeinvoc) {
			cur.render(gc, positionxmenu, positionymenu+compteur);
			gc.setFill(Color.WHITE);
	        gc.fillText(cur.toString()+" -  Cout: "+cur.cost, positionxmenu+100, positionymenu+50+compteur );
	        gc.strokeText(cur.toString()+" -  Cout: "+cur.cost, positionxmenu+100, positionymenu+50+compteur );
			compteur+=100;
		}
		portail.render(gc, 885, 660);
		if(portail.uniteainvoque!=null) {
			portail.uniteainvoque.render(gc, 885, 660);
		}
		if (confirmationencours) {
			this.renderconfirmation(gc);
		}
	}
	
	/** Deplacement du curseur vers le bas pour la selection de l'unite a invoque*/
	void downcurseur() {
		positioncurseur += 1;
		if (positioncurseur >= portail.listeinvoc.size()) {
			positioncurseur = 0;
		}
	}
	/** Deplacement du curseur vers le haut pour la selection de l'unite a invoque*/
	void upcurseur() {
		positioncurseur -= 1;
		if (positioncurseur <= -1) {
			positioncurseur = portail.listeinvoc.size()-1;
		}
	}
	/** Deplacement du curseur vers la droite pour confirmer la selection de l'unite*/
	void rightcurseurconf() {
		positioncurseurconf+=1;
		if (positioncurseurconf>=2) {
			positioncurseurconf=0;
		}
	}
	/** Deplacement du curseur vers la gauche pour confirmer la selection de l'unite*/
	void leftcurseurconf() {
		positioncurseurconf-=1;
		if (positioncurseurconf<=-1) {
			positioncurseurconf=1;
		}
	}
	
	/** Render du popup de confirmation*/
	void renderconfirmation(GraphicsContext gc) {
		gc.drawImage(confirmation, 350, 350);
		if(positioncurseurconf==0) {
			gc.drawImage(curseurconf, 360, 400);
		}
		else {
			gc.drawImage(curseurconf, 480, 400);
		}
	}
	
	/** Change l'unite a invoque si le coût des ressources est suffisant*/
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
