package jeu;


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
	    CreationMap crea = new CreationMap(gc, "creamap.ser",width,height);	// Creation de l'editeur
	    Jeu game = new Jeu(gc,width,height);	// Creation d'une partie
	   	Menuprinc menu = new Menuprinc(gc,game,crea,width,height); //Creation du menu
		String start = "Commencer la partie";
		String edit = "Editer la carte";
		menu.render();
    	/* Refresh animation */
		new AnimationTimer() {          
	        public void handle(long arg0) {              
	          
		          if (game.ingame) {
			          game.update();
			          String txt = "Tour: " + game.tour+"	"+"Joueur: "+game.entrainjouer;
			          gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			          gc.setFill(Color.BISQUE);
			          gc.setStroke(Color.BLACK);
			          gc.setLineWidth(1);
			          gc.fillText(txt, game.positionxmenu*1.05, 50 );
			          gc.strokeText(txt, game.positionxmenu*1.05, 50 );
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
			        			menu.render();
			        			gc.strokeText(edit, 360, 450 );
			        			gc.fillText(edit, 360, 450 );
						        gc.setFill(Color.YELLOW);
			        			gc.fillText(start, 360, 400 );
			        	  }
			        	  else if (menu.positioncurseur == 1) {
			        			menu.render();
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
	    				game.touch(e.getCode());
	    			}
	    			if (crea.increa) {
	    				crea.touch(e.getCode());
	    			}
	        		menu.touch(e.getCode());
	        	
	        }
	    });

	   
	   		/* Affichage scene */
	   stage.setScene(scene);
	   stage.show();
	}
	
	
}
