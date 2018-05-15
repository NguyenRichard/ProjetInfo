package jeu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Menuaction {
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
	int positionxmenu;
	int positionymenu;
	GraphicsContext gc;
	/**Position du curseur dans le menu 0 : attaquer; 1 : deplacer; 2 : capturer*/
	int positioncurseur1;
	
	Menuaction(GraphicsContext gc, int positionxmenu,int width, int height){
		this.positionxmenu=positionxmenu;
		positionymenu= 110;
		menucache = new Image("fondmenu.png", width-positionxmenu,height,false,false);
		menu1attaque = new Image("menu1attaque(10x16).png", 200, 320, false, false);
		menu1soin = new Image("menu1soin(10x16).png", 200, 320, false, false);
		curseur = new Image("curseurmenu1.png",200, 320, false, false);
		menu2 = new Image("menu2(10x16).jpg", 200, 320, false, false);
		cache = new Image("cache.png",200, 320, false, false);
		this.gc=gc;
		positioncurseur1=0;

	}

	/**
	 * Affichage du menu de deplacement et d'attaque, true le montre en false le cache en recouvrant tout le coter(a adapter plus tard)
	 * @param jeu TODO
	 * 			
	 */	
	void menurender(Jeu jeu) {
		jeu.gc.drawImage(menucache, positionxmenu, 0);
	    switch(jeu.menu) {
	    case 1:
	    		if (jeu.map.selectionnemenu.unite.type.compareTo("healer")!=0){
	    			jeu.gc.drawImage(menu1attaque, positionxmenu*1.05, positionymenu);
	    		}
	    		else {
	    			jeu.gc.drawImage(menu1soin, positionxmenu*1.05, positionymenu);
	    		}
				if (jeu.map.selectionnemenu.unite.restdeplacement == 0) {
	    			jeu.gc.drawImage(cache, positionxmenu*1.05,positionymenu+1*52);
	    		}
				if ((jeu.map.selectionnemenu.batiment == null)||(jeu.map.selectionnemenu.batiment.joueur == jeu.map.selectionnemenu.unite.joueur)) {
					jeu.gc.drawImage(cache, positionxmenu*1.05,positionymenu+2*52);
				}
				gc.drawImage(curseur, positionxmenu*1.05,positionymenu+positioncurseur1*52);
				break;
	    case 2:
	    		jeu.gc.drawImage(menu2, positionxmenu*1.05, positionymenu);
	    		jeu.gc.drawImage(curseur, positionxmenu*1.05,positionymenu+positioncurseur1*52);
	    		break;
	
	    default:
	    		break;
		} 
	}
	

/*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
	

	void upcurseur1() {if (positioncurseur1 != 0) {positioncurseur1 -= 1;}}

	void downcurseur1() {if (positioncurseur1 != 2){positioncurseur1 += 1;}}


	
}
