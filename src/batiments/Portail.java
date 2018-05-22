package batiments;

import java.util.ArrayList;

import javafx.scene.image.Image;
import jeu.Batiment;
import jeu.Unite;
import unite.*;

public class Portail extends Batiment {
	
	/**La liste des unites invocable a partir du portail*/
	public ArrayList<Unite> listeinvoc;
	/**L'unite selectionnee pour etre invoquee au debut du prochain tour du joueur */
	public Unite uniteainvoque;
	/**Le tableau d'images pour l'animation du portail au repos*/
	public Image[] images1;
	/**Le tableau d'images pour l'animation du portail qui invoquera une unite au debut du prochain tour*/
	public Image[] images2;
	/**Entier pour regler la vitesse de l'animation du portail au repos */
	public int maxcompteur1;
	/**Entier pour regler la vitesse de l'animation du portail qui va invoquer */
	public int maxcompteur2;
	
	
	public Portail(int taille, int joueur,int typearmee){
		super(taille,joueur);
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
	
	/**
	 * Fonction a lancer pour mettre un portail dans son etat initial.
	 * C'est ici qu'est stocke l'appartenance a une armee des unites.
	 * @param typearmee <br/>- 0 : armee des insectes <br/>- 1 :
	 * armee mythologique chinoise<br/>- 2 : armee des morts
	 */
	public void reset(int typearmee) {
		images=images1;
		maxcompteur=maxcompteur1;
		listeinvoc = new ArrayList<Unite>();
		uniteainvoque = null;
		switch(typearmee) {
			case 0:
					listeinvoc.add(new Abeille(taille,joueur));
					listeinvoc.add(new Fourmis(taille,joueur));
					listeinvoc.add(new Moustique(taille,joueur));
					listeinvoc.add(new AbeilleSamourai(taille,joueur));
					listeinvoc.add(new Scarabe(taille,joueur));
					listeinvoc.add(new PapillonPsychique(taille,joueur));
					
					break;
			case 1:
					listeinvoc.add(new EpeisteVolant(taille,joueur));
					break;
			case 2:
					listeinvoc.add(new SkeletonSoldier(taille,joueur));
					listeinvoc.add(new ArcherSquelette(taille,joueur));
					listeinvoc.add(new ZombieMineur(taille,joueur));
					listeinvoc.add(new TankSquelette(taille,joueur));
					listeinvoc.add(new CraneVolant(taille,joueur));
					break;
			default:
					break;
		}
		
	}
	
	
	
}
