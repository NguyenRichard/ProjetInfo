package unit;

import javafx.scene.image.Image;
import jeu.Unite;

public class EpeisteVolant extends Unite {
	
	public EpeisteVolant(int taille, int joueur) {
		super(taille,joueur);
		images = new Image[3];
		for (int k = 0; k < images.length; k++ ) {
			int l = k+1;
			images[k] = new Image("epeistevolant/epeiste-volant"+l+".png",taille, taille,false,false);
		}
		deplacement=2;
		restdeplacement=deplacement;
		valable=true;
		maxcompteur = 40;
		portee = new int[] {0,1};
		pvmax=99;
		pv=pvmax;
		dmg=1;
		cost=20;
	}
		
	public String toString() {
		return "Epeiste volant";
	}
}
