package jeu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MenuAction {
	/**Image de fond du menu lateral droit**/
	Image menucache;
	/**Image du menu d'action quand l'unite attaque**/
	Image menu1attaque;
	/**Image du menu d'action quand l'unite soigne**/
	Image menu1soin;
	/**Image de fond du menu changer tour**/
	Image menu2;
	/**Image du curseur pour les menu**/
	Image curseur;
	/**Cache des options qui ne sont plus valide */
	Image cache;
	/**Entier decrivant le nombre de pixel en abscisse a partir du quel affiche le menu lateral droit**/
	int positionxmenu;
	/**Entier decrivant le nombre de pixel en ordonnee a partir du quel affiche le menu lateral droit**/
	int positionymenu;
	GraphicsContext gc;
	/**Position du curseur dans le menu 0 : attaquer; 1 : deplacer; 2 : capturer*/
	int positioncurseur1;
	
	MenuAction(GraphicsContext gc, int positionxmenu,int width, int height){
		this.positionxmenu=positionxmenu;
		positionymenu= 110;
		menucache = new Image("fondmenu.png", width-positionxmenu,height,false,false);
		menu1attaque = new Image("menuaction/menu1attaque(10x16).png", 200, 320, false, false);
		menu1soin = new Image("menuaction/menu1soin(10x16).png", 200, 320, false, false);
		curseur = new Image("curseurmenu1.png",200, 320, false, false);
		menu2 = new Image("menu2(10x16).jpg", 200, 320, false, false);
		cache = new Image("cache.png",200, 320, false, false);
		this.gc=gc;
		positioncurseur1=0;

	}

	/**
	 * Affichage du menu de deplacement, d'attaque et de capture
	 */	
	void menurender(Jeu jeu) {
		gc.drawImage(menucache, positionxmenu, 0);
	    switch(jeu.menu) {
	    case 1:
	    		if (jeu.carte.selectionnemenu.unite.type.compareTo("soigneur")!=0){
	    			gc.drawImage(menu1attaque, positionxmenu*1.05, positionymenu);
	    		}
	    		else {
	    			gc.drawImage(menu1soin, positionxmenu*1.05, positionymenu);
	    		}
				if (jeu.carte.selectionnemenu.unite.restdeplacement == 0) {
	    			gc.drawImage(cache, positionxmenu*1.05,positionymenu+1*52);
	    		}
				if ((jeu.carte.selectionnemenu.batiment == null)||(jeu.carte.selectionnemenu.batiment.joueur == jeu.carte.selectionnemenu.unite.joueur)) {
					gc.drawImage(cache, positionxmenu*1.05,positionymenu+2*52);
				}
				gc.drawImage(curseur, positionxmenu*1.05,positionymenu+positioncurseur1*52);
				break;
	    case 2:
	    		gc.drawImage(menu2, positionxmenu*1.05, positionymenu);
	    		gc.drawImage(curseur, positionxmenu*1.05,positionymenu+positioncurseur1*52);
	    		break;
	
	    default:
	    		assert false;
	    		break;
		} 
	}
	

/*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
	

	void upcurseur1() {if (positioncurseur1 != 0) {positioncurseur1 -= 1;}}

	void downcurseur1() {if (positioncurseur1 != 2){positioncurseur1 += 1;}}


	
}
