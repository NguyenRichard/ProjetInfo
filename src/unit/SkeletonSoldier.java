package unit;
import javafx.scene.image.Image;
import jeu.Unite;

public class SkeletonSoldier extends Unite{

	public SkeletonSoldier(int taille, int joueur) {
			super(taille);
			this.joueur = joueur;
			images = new Image[6];
			for (int k = 0; k < images.length; k++ ) {
				images[k] = new Image("Skeleton_Soldier/Skeleton_Soldier_"+k+".png",taille, taille,false,false);
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
			return "Soldat Squelette";
		}
		

}