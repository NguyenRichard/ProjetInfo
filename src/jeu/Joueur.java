package jeu;

import java.util.ArrayList;

public class Joueur {
	String name;
	boolean isalive;
	ArrayList<Unite> arm�e;
	ArrayList<Batiment> possessions;
	String couleur;
	
	Joueur(String name) {this.name = name; isalive=true; arm�e= new ArrayList<Unite>(); possessions=new ArrayList<Batiment>(); couleur = "BLACK";}
	
	public String toString() {return name; }
	
	boolean add(Unite unite) {System.out.println("Demande ajout unit�"); return arm�e.add(unite); }

	boolean add(Batiment batiment) {System.out.println("demande nouveau batiment"); return possessions.add(batiment); }
	
	void changename(String name) {this.name=name; }
}
