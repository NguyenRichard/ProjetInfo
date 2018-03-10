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

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    launch(args);    
	}
	public void start(Stage stage){
	    int WIDTH = 1000;	// Taille la carte 600x600 et le menu 400x600 a droite
	    int HEIGHT = 600;	// Taille carte affichee 12x12 case (une case 50x50)

	    stage.setTitle("Projet info");
	    stage.setResizable(false);

	    Group root = new Group();
	    Scene scene = new Scene(root);
	    Canvas canvas = new Canvas(WIDTH, HEIGHT);
	    root.getChildren().add(canvas);
	    System.out.println("coucou");

	    GraphicsContext gc = canvas.getGraphicsContext2D();

	    Image fond = new Image("wood.jpg", WIDTH, HEIGHT, false, false); //sert pour le fond du menu a droite
	    
	    Jeu game = new Jeu(gc);	// Creation d'une partie
	    game.map.map1(2); // Selection de la map
	    gc.drawImage(fond, 0, 0);
	    
	    	/* Mouvement curseur */
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent e) {
	        	game.touch(e.getCode());
	        }
	    });
	    
	    	/* Refresh animation */
	    new AnimationTimer() {          
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
	   }.start();
	   
	   		/* Affichage scene */
	   stage.setScene(scene);
	   stage.show();
	}
	
	
}
