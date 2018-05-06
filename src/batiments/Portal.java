package batiments;

import javafx.scene.image.Image;
import jeu.Batiment;
import jeu.Joueur;

public class Portal extends Batiment {
	
	public Portal(int taille, Joueur joueur){
		super(taille, joueur);
		pv = 100;
		images = new Image[1];
		images[0] = new Image("portal"+".png",taille, taille,false,false);
	}
}
