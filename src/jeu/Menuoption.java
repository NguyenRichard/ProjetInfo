package jeu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Menuoption {
	
	/** Entier pour le type de menu: 0 en jeu; 1 en editeur de map;*/
	int menu;
	/** Position du menu sur l'axe des abscisses*/
	int positionxmenu;
	/** Position du menu sur l'axe des ordonnées*/
	int positionymenu;
	/** Position du curseur */
	int positioncurseur;
	/** Position maximale du curseur dans le menu 0*/
	int maxpos;
	/** Curseur du popup confiramtion */
	Image curseur;
	/** Image du menu 0*/
	Image menu0;
	/** Image du menu 1*/
	Image menu1;
	/** Boolean qui decrit si on est dans le menu option ou non*/
	boolean inmenuop;
	boolean update;
	
	Menuoption(int type, int width, int height){
		menu0=new Image("menuoption/menuoption0.png",250,370,false,false);
		curseur=new Image("menuoption/curseurmenuoption.png",250,370,false,false);
		menu1=new Image("menuoption/menuoption1.png",250,370,false,false);
		positionxmenu=(int) width/2-125;
		positionymenu=(int) height/3-100;
		menu=type;
		inmenuop=false;
		maxpos=4;
		update=false;
		
	}
	
	public void render(GraphicsContext gc){
		if (menu==0) {
			gc.drawImage(menu0, positionxmenu, positionymenu);
			switch(positioncurseur) {
			case 0:
				gc.drawImage(curseur, positionxmenu, positionymenu);
				break;
			case 1:
				gc.drawImage(curseur, positionxmenu, positionymenu+80);
				break;
			case 2:
				gc.drawImage(curseur, positionxmenu, positionymenu+170);
				break;
			case 3:
				gc.drawImage(curseur, positionxmenu, positionymenu+260);
				break;
			default:
				break;
			}
		}
		else if (menu==1) {
			gc.drawImage(menu1, positionxmenu, positionymenu);
			switch(positioncurseur) {
			case 0:
				gc.drawImage(curseur, positionxmenu, positionymenu);
				break;
			case 1:
				gc.drawImage(curseur, positionxmenu, positionymenu+80);
				break;
			case 2:
				gc.drawImage(curseur, positionxmenu, positionymenu+170);
				break;
			case 3:
				gc.drawImage(curseur, positionxmenu, positionymenu+260);
				break;
			default:
				break;
			}
		}
		update=false;
		
	}
	
	/** Deplacement du curseur vers le haut*/
	public void upcurseur(){
		positioncurseur-=1;
		if (menu==0) {
			if (positioncurseur<=-1) {
				positioncurseur=maxpos-1;
			}
		}
		else if (menu==1) {
			if (positioncurseur<=-1) {
				positioncurseur=maxpos-1;
			}
		}
		System.out.println("poscurseur: "+positioncurseur);
	}
	/** Deplacement du curseur vers le bas*/
	public void downcurseur() {
		positioncurseur+=1;
		if (menu==0) {
			if (positioncurseur>=maxpos) {
				positioncurseur=0;
			}
		}
		else if (menu==1) {
			if (positioncurseur>=maxpos) {
				positioncurseur=0;
			}
		}
		System.out.println("poscurseur: "+positioncurseur);
	}
	
	
}
