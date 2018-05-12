package unit;
import javafx.scene.image.Image;
import jeu.Unite;

public class ArcherSquelette extends Unite{

	public ArcherSquelette(int taille, int joueur) {
			super(taille);
			this.joueur = joueur;
			images = new Image[6];
			for (int k = 0; k < images.length; k++ ) {
				images[k] = new Image("Archer_Squelette/Archer_Squelette_"+k+".png",taille, taille,false,false);
			}
			deplacement=3;
			restdeplacement=deplacement;
			valable=true;
			maxcompteur = 75;
			portee = new int[] {0,1};
			pvmax=99;
			pv=pvmax;
			dmg=20;
		}
		
		public String toString() {
			return "Archer Squelette";
		}
		

}