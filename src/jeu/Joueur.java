package jeu;

import java.util.ArrayList;

import batiments.Crystal;
import batiments.Portal;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Joueur {
	private String name;
	boolean isalive;
	private ArrayList<Unite> armee;
	private ArrayList<Batiment> possessions;
	int ressources;
	
/*_Creation du joueur de base________________________________________________________________________*/
	Joueur(String name) {
		this.name = name;
		isalive=false; //on met les bon joueurs en vie lors de la recréation de la map ingame
		armee= new ArrayList<Unite>();
		possessions=new ArrayList<Batiment>();
		ressources = 100;
	}
	
/*_Personalisation et infos du joueur________________________________________________________________*/
	public String toString() {return name; }
	
	void changename(String name) {this.name=name; }
	
	public void printSituation() {
		if (isalive) {
			System.out.println("Situation Joueur " + name + " : " + "\n" + "armée : " + armee + "\n" + "possessions : " + possessions + "\n");
		}
	}
	
/*Gestion unités et batiments du joueur______________________________________________________________*/
	boolean add(Unite unite) {return armee.add(unite); }
	
	boolean remove(Unite unite) {return armee.remove(unite); }
	
	boolean add(Batiment batiment) {return possessions.add(batiment); }

	boolean remove(Batiment batiment) {return possessions.remove(batiment);	}
	
	/**
	 * retourne vrai si le joueur possede au moins une unite
	 */
	boolean possedeunite() {return !(armee.isEmpty());}
	
	void rendreValable() {
		for (Unite unite : armee) {
			unite.valable=true;
			unite.restdeplacement=unite.deplacement;
		}
	}

	/**
	 * verifie si isalive est à jour
	 * renvoie true si le jeu ne savait pas que le joueur était mort
	 */
	public boolean verifvivant() {
		for (Batiment cur : possessions) {
			if (cur instanceof Portal) {
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
	 */
	public void actiondesbatiments() {
		for (Batiment cur : possessions) {
			if (cur instanceof Crystal) {
				ressources+=50;
			}
		}
	}
}