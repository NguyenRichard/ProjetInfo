package jeu;


import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import java.io.File;
import javafx.scene.canvas.GraphicsContext;

public class MenuPrinc {
	/**L'image de fond du menu*/
	Image fond;
	int positioncurseur;
	Jeu game;
	CreationMap crea;
   	boolean update;
	FXDialogs fx;
   	
	/* Constructeur de Menu*/
	MenuPrinc(Jeu game,CreationMap crea,int width, int height){
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
					game.map.remakemap(f,true);
					game.ingame=true;
					game.map.affichageEquipe();
					game.map.joueurs.get(game.entrainjouer).actiondesbatiments();
				}
				else {
					fx.showWarning("Attention", "Le fichier n'existe pas");
					System.out.println("Le fichier n'existe pas");
				}

			}
			else if (positioncurseur == 1) {
				File f = new File(crea.namesave); // nom du fichier contenant la sauvegarde
				if (f.exists()){
					crea.mapcode=crea.map.remakemap(f,false);
				}
				else {
					SauvegardeMap sauvegarde = new SauvegardeMap(); crea.mapcode = sauvegarde.grillemap; // si nouveau 
				}
				crea.increa=true;
			}
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

	    
	    /*_Mettre a jour la position du curseur du menu1__________________________________________________________________________________ */		
		
		void upcurseur() {if (positioncurseur != 0) {positioncurseur -= 1;}}
		void downcurseur() {if (positioncurseur != 1){positioncurseur += 1;}}	    

}
