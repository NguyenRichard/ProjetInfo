package jeu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import son.Son;
import javax.sound.sampled.Clip;


public class Main extends Application { //Nouveau test

	public static void main(String[] args) {
		// TODO Auto-generated method stub      
	    launch(args); 
	    
	}
	
	public void start(Stage stage){   
		
	    int width = 1100;
	    int height = 750;

	    stage.setTitle("Projet info : un projet de Richard, Jean, Arthur et Fabien");
	    stage.setResizable(false);
	    
	    // Pour l'instant je le mets ici mais on peut le changer de place ensuite
	 	Son sd = new Son();
	 	Clip clip = sd.boucle();

	    Group root = new Group();
	    Scene scene = new Scene(root);
	    Canvas canvas = new Canvas(width, height);
	    root.getChildren().add(canvas);
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    Jeu game = new Jeu(gc,width,height,sd,clip);	// Creation d'une partie
	    CreationCarte crea = new CreationCarte(gc, "creacarte.ser",width,height,sd,clip);	// Creation de l'editeur. C'est ici qu'on choisi le nom de la sauvegarde !!
	   	MenuPrinc menu = new MenuPrinc(game,crea,width,height); //Creation du menu
		menu.render(gc);
		
		
		
    	/* Refresh animation */
		new AnimationTimer() {          
			public void handle(long arg0) {              

				if (game.ingame) {
					game.update();
				}
				if (crea.increa) {
					crea.update();
				}
				if (!(menu.game.ingame || menu.crea.increa)) {
					menu.update();
				}
			}
		}.start();


		/* Mouvement curseur */
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (game.ingame) {
					try {
						game.touch(e.getCode());
						game.update=true;
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (crea.increa) {
					crea.touch(e.getCode());
					crea.update=true;
				}
				else {
					try {
						menu.touch(e.getCode());
						menu.update=true;
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	    				
				}

			}
		});


		/* Affichage scene */
		stage.setScene(scene);
		stage.show();
	}


}
