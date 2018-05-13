package jeu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Menuaction {
	/**Image de fond du menu lateral droit**/
	Image menucache;
	/**Image de fond du menu lateral droit**/
	Image menu1;
	/**Image de fond du menu changer tour**/
	Image menu2;
	/**Image du curseur pour les menu**/
	Image curseur;
	/**Cache des options qui ne sont plus valide */
	Image cache;
	int positionxmenu;
	GraphicsContext gc;
	/**Position du curseur dans le menu 0 : attaquer; 1 : deplacer; 2 : capturer*/
	int positioncurseur1;
	
	Menuaction(GraphicsContext gc, int positionxmenu,int width, int height){
		this.positionxmenu=positionxmenu;
		menucache = new Image("wood.jpg", width-positionxmenu,height,false,false);
		menu1 = new Image("menu1(10x16).png", 200, 320, false, false);
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
				jeu.gc.drawImage(menu1, positionxmenu*1.05, 50);
				if (jeu.map.selectionnemenu.unite.restdeplacement == 0) {
	    			jeu.gc.drawImage(cache, positionxmenu*1.05,50+1*52);
	    		}
				if ((jeu.map.selectionnemenu.batiment == null)||(jeu.map.selectionnemenu.batiment.joueur == jeu.map.selectionnemenu.unite.joueur)) {
					jeu.gc.drawImage(cache, positionxmenu*1.05,50+2*52);
				}
				gc.drawImage(curseur, positionxmenu*1.05,50+positioncurseur1*52);
				break;
	    case 2:
	    		jeu.gc.drawImage(menu2, positionxmenu*1.05, 50);
	    		jeu.gc.drawImage(curseur, positionxmenu*1.05,50+positioncurseur1*52);
	    		break;
	
	    default:
	    		break;
		} 
	}
	

/*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
	

	void upcurseur1() {if (positioncurseur1 != 0) {positioncurseur1 -= 1;}}

	void downcurseur1() {if (positioncurseur1 != 2){positioncurseur1 += 1;}}


	
}
