package batiments;

import java.util.ArrayList;

import javafx.scene.image.Image;
import jeu.Batiment;
import jeu.Unite;
import unit.*;

public class Portal extends Batiment {
	
	public ArrayList<Unite> listeinvoc;
	public Unite uniteainvoque;
	int taille;
	public Image[] images1;
	public Image[] images2;
	public int maxcompteur1;
	public int maxcompteur2;
	
	
	public Portal(int taille, int joueur,int typearmee){
		super(taille);
		this.taille=taille;
		this.joueur = joueur;
		pv = 100;
		images1 = new Image[7];
		for (int k = 0; k < images1.length; k++ ) {
			images1[k] = new Image("portal/portal_"+k+".png",taille, taille,false,false);
		}
		maxcompteur1 = 75;
		images2 = new Image[16];
		for (int k = 0; k < images2.length;k++) {
			images2[k] = new Image("portal/portal1_"+k+".png",taille,taille,false,false);
		}
		maxcompteur2= 150;
		this.reset(typearmee);
	}
	
	
	public String toString() {
		return "Portail: "+uniteainvoque;
	}
	
	public void reset(int typearmee) {
		images=images1;
		maxcompteur=maxcompteur1;
		listeinvoc = new ArrayList<Unite>();
		switch(typearmee) {
			case 0:
					listeinvoc.add(new Abeille(taille,joueur));
					listeinvoc.add(new AbeilleSamourai(taille,joueur));
					listeinvoc.add(new Fourmis(taille,joueur));
					listeinvoc.add(new Scarabe(taille,joueur));
					listeinvoc.add(new Moustique(taille,joueur));
					break;
			case 1:
					listeinvoc.add(new EpeisteVolant(taille,joueur));
					break;
			case 2:
					listeinvoc.add(new TankSquelette(taille,joueur));
					listeinvoc.add(new SkeletonSoldier(taille,joueur));
					listeinvoc.add(new ArcherSquelette(taille,joueur));
					break;
			default:
					break;
		}
		
	}
	
	
	
}
