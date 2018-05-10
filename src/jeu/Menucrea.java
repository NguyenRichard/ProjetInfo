package jeu;

import java.util.ArrayList;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Menucrea {
	/**Image de fond du Mencucrea */
	Image fond;
	/**Image du curseur de Menucrea */
	Image curseur;
	/**Image de l'element en cours de placement*/
	Image image;
	/**code de l'element en cours de placement */
	int codesave;
	/**booleen vrai si on est dans le menucrea*/
	boolean choix;
	/**Position du curseur dans le menucrea, 0 = choix batiment/unite/terrain, 1= division interne */
	int positioncurseurcrea;
	/**0:on choisi un terrain, 1:on choisi une unite et 2:on choisi le batiment*/
	int choixtype;
	/**numero du joueur auxquel apartiendra le batiment ou l'unite cree */
	int numjoueur;
	/**Tableau d'element permettant de faire le lien entre le code de l'element et le type de terrain */
	ArrayList<Terrain> referencecodeterrain;
	GraphicsContext gc;
	Case visualisation;
	/**Entier a partir du quel affiche le menu lateral droit**/
    int positionxmenu;
	
	Menucrea(GraphicsContext gc,ArrayList<Terrain> referencecodeterrain, Case visu,int positionxmenu){
		fond = new Image("menucrea.png", 200, 320, false, false);
		curseur = new Image("curseurmenu1.png",200, 320, false, false);
		this.positionxmenu=positionxmenu+50;
		codesave = 1+50+125000;
		choix = false;
		positioncurseurcrea = 0;
		choixtype=0;
		numjoueur=0;
		visualisation = visu;
		this.referencecodeterrain = referencecodeterrain;
		this.gc = gc;
	}
	
	void render() {
		gc.drawImage(fond, positionxmenu, 50);
		//affichage du numjoueur
		String txt = "joueur : " + numjoueur;
        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        gc.setFill(Color.BISQUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.fillText(txt, positionxmenu+30, 310);
        gc.strokeText(txt, positionxmenu+30, 310);
        //affichage du choixtype
        if (choixtype==0) {
        	String type = "terrain";
        	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
            gc.setFill(Color.BISQUE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.fillText(type, positionxmenu+50, 135);
            gc.strokeText(type, positionxmenu+50, 135);
            visualisation.terrain.render(gc, positionxmenu+65, 200);
        }
        if (choixtype==1) {
        	String type = "unitee";
        	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
            gc.setFill(Color.BISQUE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.fillText(type, positionxmenu+50, 135);
            gc.strokeText(type, positionxmenu+50, 135);
            visualisation.unite.render(gc, positionxmenu+65, 200);
            
        }
        if (choixtype==2) {
        	String type = "batiment";
        	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
            gc.setFill(Color.BISQUE);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.fillText(type, positionxmenu+50, 135);
            gc.strokeText(type, positionxmenu+50, 135);
            visualisation.batiment.render(gc, positionxmenu+65, 200);
            
        }
        
		if (choix) {
			gc.drawImage(curseur, positionxmenu,100+positioncurseurcrea*85);
		}
	}
	
	void downcurseur() {
		positioncurseurcrea += 1;
		if (positioncurseurcrea >= 3) {
			positioncurseurcrea = 0;
		}
	}
	void upcurseur() {
		positioncurseurcrea -= 1;
		if (positioncurseurcrea <= -1) {
			positioncurseurcrea = 2;
		}
	}
	
	/**
	 * @param k le nombre total de type d'unite existante
	 * @param l le nombre total de type de batiment existant
	 */
	void rightcurseur(int k, int l) {
		if(positioncurseurcrea==0) { //on change le type d'element (batiment/terrain/unite)
			choixtype += 1;
			if (choixtype == 3) {
				choixtype = 0;
			}
			else if (choixtype == 1){
				numjoueur = (codesave/(50*50))%50; //on s'assure d'afficher le bon numjoueur
			}
			else if (choixtype == 2){
				numjoueur = (codesave/(50*50*50*50))%50;
			}
		}
		
		else if (positioncurseurcrea==1) { //on change au sein de la division interne
			if (choixtype==0) {
				codesave += 1;
				if (codesave%50==referencecodeterrain.size()) { //on a depasse le nombre de type de terrain
					codesave -= codesave%50 - 1; //donc on remet le dernier chiffre du code a 1
				}
				visualisation.terrain = referencecodeterrain.get(codesave%50);
			}
			else if (choixtype==1) {
				codesave += 50;
				if ((codesave/50)%50==k+1) { //on a depasse le nombre de type d'unite
					codesave -= ((codesave/50)%50-1)*50; //donc on remet l'avant dernier chiffre du code a 1
				}
			}
			else if (choixtype==2) {
				codesave += 50*50*50;
				if ((codesave/(50*50*50))%50==l+1) { //on a depasse le nombre de type de batiment
					codesave -= ((codesave/(125000))%50-1)*125000; //donc on remet le deuxieme chiffre du code a 0
				}
			}
		}
		else if (positioncurseurcrea==2) { //on change l'appartenance au joueur
			if (choixtype==1){ //on change le numjoueur correspondant a l'unite
				if (numjoueur == 3) {
					numjoueur = 0;
				}
				else {
					numjoueur += 1; //pour l'affichage
				}
				codesave = codesave - ((codesave/(50*50))%50)*50*50 + numjoueur*50*50; 
				
			}
			else if(choixtype==2){ //on change le numjoueur correspondant au batiment
				if (numjoueur == 4) {
					numjoueur = 0;
				}
				else {
					numjoueur += 1; //pour l'affichage
				}
				codesave = codesave - ((codesave/(6250000))%50)*6250000 + numjoueur*6250000; 
				
			}
		}
	}
	
	/**
	 * @param k le nombre total de type d'unite existante
	 * @param l le nombre total de type de batiment existant
	 */
	void leftcurseur(int k, int l) {
		if(positioncurseurcrea==0) { //on change le type d'element (batiment/terrain/unite)
			choixtype -= 1;
			if (choixtype == -1) {
				choixtype = 2;
			}
			else if (choixtype == 1){
				numjoueur = (codesave/(50*50))%50; //on s'assure d'afficher le bon numjoueur
			}
			else if (choixtype == 2){
				numjoueur = (codesave/(50*50*50*50))%50;
			}
		}
		
		else if (positioncurseurcrea==1) { //on change au sein de la division interne
			if (choixtype==0) {
				if (codesave%50 != 1) {
					codesave -= 1;
				}
				else { //on revient au maximum au lieu d'arriver sur le terrain vide
					codesave -= codesave%50 - referencecodeterrain.size() + 1;
				}
				visualisation.terrain = referencecodeterrain.get(codesave%50);
			}
			else if (choixtype==1) {
				if ((codesave/50)%50==1) {
					codesave += 50*(k-1); //on remet l'avant dernier chiffre au maximum d'unite
				}
				else { 
					codesave -= 50;
				}
			}
			else if (choixtype==2) {
				if ((codesave/125000)%50==1) {
					codesave += 125000*(l-1); //on remet le second chiffre au maximum de batiment
				}
				else { 
					codesave -= 125000;
				}
			}
		}
		else if (positioncurseurcrea==2) { //on change l'appartenance au joueur
			if ((choixtype==1)){ //on change le numjoueur correspondant a l'unite
				if (numjoueur == 0) {
					numjoueur = 3;
				}
				else {
					numjoueur -= 1; //pour l'affichage
				}
				codesave = codesave - ((codesave/(50*50))%50)*50*50 + numjoueur*50*50; 
			}
			else if((choixtype==2)){ //on change le numjoueur correspondant au batiment
				if (numjoueur == 0) {
					numjoueur = 4;
				}
				else {
					numjoueur -= 1; //pour l'affichage
				}
				codesave = codesave - ((codesave/(6250000))%50)*6250000 + numjoueur*6250000; 
			}
		}
	}
}
