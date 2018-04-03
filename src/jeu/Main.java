package jeu;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
		    int WIDTH = 1000;	// Taille la carte 600x600 et le menu 400x600 a droite
		    int HEIGHT = 600;	// Taille carte affichee 12x12 case (une case 50x50)
	
		    stage.setTitle("Projet info : un projet de Richard, Jean, Arthur et Fabien");
		    stage.setResizable(false);
	
		    Group root = new Group();
		    Scene scene = new Scene(root);
		    Canvas canvas = new Canvas(WIDTH, HEIGHT);
		    root.getChildren().add(canvas);
		    GraphicsContext gc = canvas.getGraphicsContext2D();
		   	Menuprinc menu = new Menuprinc(gc); //Creation du menu
		    Jeu game = new Jeu(gc);	// Creation d'une partie
			menu.render();
	
	    	/* Refresh animation */
		   AnimationTimer animation = new AnimationTimer() {          
		        public void handle(long arg0) {              

		          game.update();
		          String txt = "Tour: " + game.tour+"	"+"Joueur: "+game.entrainjouer;
		          gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		          gc.setFill(Color.BISQUE);
		          gc.setStroke(Color.BLACK);
		          gc.setLineWidth(1);
		          gc.fillText(txt, 650, 50 );
		          gc.strokeText(txt, 650, 50 ); 
	        	}
	    };
	    

	    	/* Mouvement curseur */
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent e) {
	        	if (game.ingame == false){
	        		switch(e.getCode()) {
	        		case ENTER:	               //Lorsqu'on appuye sur entree ca lance une partie avec la map1
	        				game.map.map1(2);
	        		    	game.map.render(gc);
	        		    	animation.start();
	        		    	game.ingame = true;
					default:
						break;
	        		}
	        	}
	        	else {
	        			switch(e.getCode()) {
	        			case Z: 				// Si on appuye sur Z pendant une partie, on arrete la partie et on reset jeu.
	        				animation.stop();
	        				game.fin();
	        				gc.clearRect(0, 0, 1000, 600);
	        				menu.render();
						default:
							break;
	        			}
		        		game.touch(e.getCode());
	        	}
	        	
	        }
	    });

	   
	   		/* Affichage scene */
	   stage.setScene(scene);
	   stage.show();
	}
	
	
}
