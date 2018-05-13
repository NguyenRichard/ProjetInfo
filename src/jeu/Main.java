package jeu;

import Sounds.Sound;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
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

	    Group root = new Group();
	    Scene scene = new Scene(root);
	    Canvas canvas = new Canvas(width, height);
	    root.getChildren().add(canvas);
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    Jeu game = new Jeu(gc,width,height);	// Creation d'une partie
	    CreationMap crea = new CreationMap(gc, "creamap.ser",width,height,game.ingame);	// Creation de l'editeur
	   	Menuprinc menu = new Menuprinc(game,crea,width,height); //Creation du menu
		String start = "Commencer la partie";
		String edit = "Editer la carte";
		menu.render(gc);
		
		// Pour l'instant je le mets ici mais on peut le changer de place ensuite
		Sound sd = new Sound();
		Clip clip = sd.boucle();
		
    	/* Refresh animation */
		new AnimationTimer() {          
	        public void handle(long arg0) {              
	          
		          if (game.ingame) {
			          game.update();
			          Joueur entrainjouer = game.map.joueurs.get(game.entrainjouer);
			          String infojoueur = "Joueur: "+entrainjouer+"\nRessources: "+entrainjouer.ressources;
			          gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			          gc.setLineWidth(1);
					  switch(game.entrainjouer) {
					    	case 4: 
					    			gc.setFill(Color.WHITE);
					    			break;
					    	case 1:
					    			gc.setFill(Color.BLUE);
					    			break;
					    	case 2:
				    				gc.setFill(Color.RED);
				    				break;
					    	case 3:
					    			gc.setFill(Color.GREEN);
					    			break;
					  }
					    	
			          gc.fillText(infojoueur, game.menudroite.positionxmenu*1.05, 50 );
			          gc.strokeText(infojoueur, game.menudroite.positionxmenu*1.05, 50 );
			          gc.setFill(Color.BISQUE);
			          gc.setStroke(Color.BLACK);
		          }
		          if (crea.increa) {
		        	  crea.update();
		          }
		          if (menu.inmenuprin) {
		        	  if (menu.update) {
				          gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 32));
				          gc.setFill(Color.WHITE);
				          gc.setStroke(Color.BLACK);
				          gc.setLineWidth(1);
			        	  if (menu.positioncurseur == 0) {
			        			menu.render(gc);
			        			gc.strokeText(edit, 360, 450 );
			        			gc.fillText(edit, 360, 450 );
						        gc.setFill(Color.YELLOW);
			        			gc.fillText(start, 360, 400 );
			        	  }
			        	  else if (menu.positioncurseur == 1) {
			        			menu.render(gc);
			        			gc.fillText(start, 360, 400 );
			        			gc.strokeText(start, 360, 400 );
						        gc.setFill(Color.YELLOW);
			        			gc.fillText(edit, 360, 450 );
			        	  }
			        	  menu.update = false;
	        		  
		        	  }
		          }
	        	}
	   	}.start();
	    
		
	    	/* Mouvement curseur */
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent e) {
	    			if (game.ingame) {
	    				try {
							game.touch(e.getCode());
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    			}
	    			if (crea.increa) {
	    				crea.touch(e.getCode());
	    			}
	        		try {
						menu.touch(e.getCode());
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	
	        }
	    });

	   
	   		/* Affichage scene */
	   stage.setScene(scene);
	   stage.show();
	}
	
	
}
