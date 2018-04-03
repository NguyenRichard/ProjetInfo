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

public class MainCrea extends Application { //Nouveau test


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
	    CreationMap crea = new CreationMap(gc,"creamap.ser");	// Creation d'une partie
		crea.map.render(gc);

    	/* Refresh animation */
	   AnimationTimer animation = new AnimationTimer() {          
	        public void handle(long arg0) {
	        	crea.increa = true;
		          crea.update();
	        	}
	    };

	   
	   		/* Affichage scene */
	   
	   animation.start();
	
	scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		public void handle(KeyEvent e) {
		crea.touch(e.getCode());
	}});
	stage.setScene(scene);
	stage.show();
	
	};
	
	
}
