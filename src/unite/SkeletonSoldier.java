package unite;
import javafx.scene.image.Image;
import jeu.Unite;

public class SkeletonSoldier extends Unite{

	public SkeletonSoldier(int taille, int joueur) {
			super(taille,joueur);
			images = new Image[6];
			for (int k = 0; k < images.length; k++ ) {
				images[k] = new Image("Skeleton_Soldier/Skeleton_Soldier_"+k+".png",taille, taille,false,false);
			}
			deplacement=4;
			restdeplacement=deplacement;
			valable=true;
			maxcompteur = 75;
			portee = new int[] {0,1};
			pvmax=99;
			pv=pvmax;
			cost=20;
			dmg=35;
		}
		
		public String toString() {
			return "Soldat Squelette";
		}
		

}