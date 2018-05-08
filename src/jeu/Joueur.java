package jeu;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Joueur {
	private String name;
	boolean isalive;
	private ArrayList<Unite> arm�e;
	private ArrayList<Batiment> possessions;
	String couleur;
	
/*_Creation du joueur de base________________________________________________________________________*/
	Joueur(String name) {
		this.name = name;
		isalive=true;
		arm�e= new ArrayList<Unite>();
		possessions=new ArrayList<Batiment>();
		couleur = "BLACK";
	}
	
/*_Personalisation et infos du joueur________________________________________________________________*/
	public String toString() {return name; }
	
	void changename(String name) {this.name=name; }
	
	public void printSituation() {
		if (isalive) {
			System.out.println("Situation Joueur " + name + " : " + "\n" + "arm�e : " + arm�e + "\n" + "possessions : " + possessions + "\n");
		}else {System.out.println("Le joueur " + name + " est mort" + "\n"); }
	}
	
/*Gestion unit�s et batiments du joueur______________________________________________________________*/
	boolean add(Unite unite) {return arm�e.add(unite); }
	
	boolean remove(Unite unite) {return arm�e.remove(unite); }
	
	boolean add(Batiment batiment) {return possessions.add(batiment); }

	boolean remove(Batiment batiment) {return possessions.remove(batiment);	}
	
	void rendreValable() {
		for (Unite unite : arm�e) {
			unite.valable=true;
			unite.restedeplacement=unite.deplacement;
		}
	}

	/**
	 * verifie si isalive est � jour
	 * renvoie true si le jeu savait que le joueur �tait mort
	 */
	public boolean verifvivant() {
		if (possessions.size()==0) {
			if (isalive) {
				isalive=false;
				return false;
			}
		}
		return true;
	}
}
