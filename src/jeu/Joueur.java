package jeu;

import java.util.ArrayList;
import batiments.Crystal;
import batiments.Portail;


public class Joueur {
	private String name;
	boolean isalive;
	public ArrayList<Unite> armee;
	public ArrayList<Case> possessions;
	int ressources;
	int typearmee;
	
/*_Creation du joueur de base________________________________________________________________________*/
	Joueur(String name) {
		this.name = name;
		isalive=false; //on met les bon joueurs en vie lors de la recreation de la carte ingame
		armee= new ArrayList<Unite>();
		possessions=new ArrayList<Case>();
		ressources = 100;
		typearmee = 0;
	}
	
/*_Personalisation et infos du joueur________________________________________________________________*/
	public String toString() {return name; }
	
	void changename(String name) {this.name=name; }
	
	public void printSituation() {
		if (isalive) {
			System.out.print("Situation Joueur " + name + " : " + "\n" + "armee : " + armee + "\n" + "possessions : " + "[ ");
			for (Case cur : possessions) {
				System.out.print(cur.batiment + " ");
			}
			System.out.println("]\n");
		}
	}
	
/*Gestion unites et batiments du joueur______________________________________________________________*/
	boolean add(Unite unite) {return armee.add(unite); }
	
	boolean remove(Unite unite) {return armee.remove(unite); }
	
	boolean add(Case casebatiment) {return possessions.add(casebatiment); }

	boolean remove(Case casebatiment) {return possessions.remove(casebatiment);	}
	
	/**
	 * retourne vrai si le joueur possede au moins une unite
	 */
	boolean possedeunite() {return !(armee.isEmpty());}
	
	/**
	 * retourne vrai si le joueur possede au moins un batiment
	 */
	boolean possedebatiment() {return !(possessions.isEmpty());}
	
	void rendreValable() {
		for (Unite unite : armee) {
			unite.valable=true;
			unite.restdeplacement=unite.deplacement;
		}
	}

	/**
	 * verifie si isalive est a jour
	 * renvoie true si le jeu ne savait pas que le joueur etait mort
	 */
	public boolean verifvivant() {
		for (Case cur : possessions) {
			if (cur.batiment instanceof Portail) {
				return false;
			}
		}
		if (isalive) {
			isalive=false;
			return true;
		}
		return false;
	}
	
	/**
	 * a chaque tour cette fonction effectue les actions des batiments du joueur
	 * @throws CloneNotSupportedException 
	 */
	public void actiondesbatiments() throws CloneNotSupportedException {
		for (Case cur : possessions) {
			if (cur.batiment instanceof Crystal) {
				ressources+=50;
			}
			if (cur.batiment instanceof Portail) {
				if (cur.unite==null) {
					Portail portail = (Portail) cur.batiment;
					if (portail.uniteainvoque!=null) {
						cur.unite = portail.uniteainvoque.clone();
						armee.add(cur.unite);
						portail.uniteainvoque=null;
						portail.images=portail.images1;
						portail.maxcompteur=portail.maxcompteur1;
					}					

				}
			}
		}
	}
}