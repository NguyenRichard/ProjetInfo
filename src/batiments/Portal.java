package batiments;

import java.util.ArrayList;

import javafx.scene.image.Image;
import jeu.Batiment;
import jeu.Unite;
import unit.Abeille;
import unit.AbeilleSamourai;
import unit.EpeisteVolant;
import unit.Fourmis;
import unit.Moustique;
import unit.Scarabe;

public class Portal extends Batiment {
	
	public ArrayList<Unite> listeinvoc;
	public Unite uniteainvoque;
	int taille;
	
	
	public Portal(int taille, int joueur,int typearmee){
		super(taille);
		this.taille=taille;
		this.joueur = joueur;
		pv = 100;
		images = new Image[1];
		images[0] = new Image("portal"+".png",taille, taille,false,false);
		this.reset(typearmee);
	}
	
	
	public String toString() {
		return "Portail: "+uniteainvoque;
	}
	
	public void reset(int typearmee) {
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
			default:
					break;
		}
		
	}
	
	
	
}
