package jeu;


import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;

public class MenuPrinc {
	/**L'image de fond du menu*/
	Image fond;
	int positioncurseur;
	Jeu game;
	CreationCarte crea;
   	boolean update;
	FXDialogs fx;
   	
	/* Constructeur de Menu*/
	MenuPrinc(Jeu game,CreationCarte crea,int width, int height){
		fond = new Image("DimensionalWar.png", width, height, false, false);
		this.game=game;
		this.crea=crea;
		update = true;
		fx = new FXDialogs();
	}
	
	void render(GraphicsContext gc) {
		gc.drawImage(fond, 0, 0);
		
	}
	
	@SuppressWarnings("static-access")
	void touch(KeyCode code) throws CloneNotSupportedException {

		switch(code) {
		case A:
			if (positioncurseur == 0) {
				File f = new File(crea.namesave); // nom du fichier contenant la sauvegarde
				if (f.exists()){
					game.carte.remakecarte(f,true);
					game.ingame=true;
					game.carte.affichageEquipe();
					game.carte.joueurs.get(game.entrainjouer).actiondesbatiments();
				}
				else {
					fx.showWarning("Attention", "Le fichier n'existe pas");
					System.out.println("Le fichier n'existe pas");
				}

			}
			else if (positioncurseur == 1) {
				File f = new File(crea.namesave); // nom du fichier contenant la sauvegarde
				if (f.exists()){
					crea.cartecode=crea.carte.remakecarte(f,false);
				}
				else {
					SauvegardeCarte sauvegarde = new SauvegardeCarte(); crea.cartecode = sauvegarde.grillecarte; // si nouveau 
				}
				crea.increa=true;
			} else {assert false;}
			break; 
		case UP: 
			this.upcurseur();
			break; 
		case DOWN: 
			this.downcurseur();
			break; 
		default:
			break;

		}
	}
	
	void update() {
		GraphicsContext gc = game.gc;
		String start = "Commencer la partie";
		String edit = "Editer la carte";
		if (update) {
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 32));
			gc.setFill(Color.WHITE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			if (positioncurseur == 0) {
				render(gc);
				gc.strokeText(edit, 360, 450 );
				gc.fillText(edit, 360, 450 );
				gc.setFill(Color.YELLOW);
				gc.fillText(start, 360, 400 );
			}
			else if (positioncurseur == 1) {
				render(gc);
				gc.fillText(start, 360, 400 );
				gc.strokeText(start, 360, 400 );
				gc.setFill(Color.YELLOW);
				gc.fillText(edit, 360, 450 );
			}
			update = false;

		}
	}
	
	    /*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
		
		void upcurseur() {if (positioncurseur != 0) {positioncurseur -= 1;}}
		void downcurseur() {if (positioncurseur != 1){positioncurseur += 1;}}	    

}
