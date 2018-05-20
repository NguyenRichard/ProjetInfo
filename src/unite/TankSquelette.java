package unite;
import javafx.scene.image.Image;
import jeu.Unite;

public class TankSquelette extends Unite{

	public TankSquelette(int taille, int joueur) {
			super(taille,joueur);
			images = new Image[6];
			for (int k = 0; k < images.length; k++ ) {
				images[k] = new Image("Tank_Squelette/Tank_Squelette_"+k+".png",taille, taille,false,false);
			}
			deplacement=3;
			restdeplacement=deplacement;
			valable=true;
			maxcompteur = 100;
			portee = new int[] {0,1};
			pvmax=99;
			pv=pvmax;
			dmg=20;
		}
		
		public String toString() {
			return "Tank Squelette";
		}
		

}

