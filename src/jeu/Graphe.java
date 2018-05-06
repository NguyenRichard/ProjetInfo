package jeu;

public class Graphe {
	
	/**Matrice des liaisons (matrice d'adjacence) */
	public int [][] liaisons;
	/**tableau des sommets valides*/
	public boolean[] valid;
	/**Entier fixe symbolisant le vide */
	public final static int vide = -999;
	
	/**
	 * Constructeur de Graphe.
	 * 
	 * @param Nmax 
	 * 		Nombre de sommets maximum du graphe (tableau fixe)
	 * 			
	 */
	Graphe(int Nmax) {
		liaisons = new int [Nmax][Nmax];
		valid = new boolean [Nmax];
		for (int i=0;i<Nmax;i++) {
			valid[i]=false;
			for (int j=0;j<Nmax;j++) {
				liaisons[i][j]=vide; 
			}
		}
	}
	
	/**
	 * Regarde si un sommet du graphe est valide
	 * 			
	 */
	boolean valideSommet(int i) {
		if ((i >= valid.length) || (i<0)) {
			return false;
		}
		else {
			return valid[i];
		}
	}
	
	/**
	 * 	Donne le nombre de sommets	
	 */
	int nbSommet() {
		int res = 0;
		while ((res<valid.length) && (valid[res])) {
			res++;
		}
		return res;
	}
	
	/**
	 * Regarde si une liaisons du graphe est valide
	 * 			
	 */
	boolean valideLiaison(int i,int j) {
		return (valideSommet(i)&&valideSommet(j))&&(liaisons[i][j]!=vide);
	}
	
	/**
	 * retourne le poids d'une liaison
	 * 			
	 */
	int Poidliaison(int i, int j) {
		if (!valideLiaison(i,j)) {
			System.out.println("Liaison inexistante");
			System.exit(-1);
		}
			return liaisons[i][j];	
	}
	
	
	void ajouterSommet(int i) {
		if (valideSommet(i)) {
			System.out.println("sommmet déjà existant");
		} else {
			valid[i]=true;
		}
	}
	
	void supprimerSommet(int i) {
		if (!valideSommet(i)) {
			System.out.println("sommmet déjà inexistant");
		} else {
			valid[i]=false;
			for (int j=0;j<valid.length;j++) {
				liaisons[i][j]=vide;
				liaisons[j][i]=vide;
			}
		}
	}
	
	void ajouterLiaisons(int i, int j, int p) {
		if (!valideSommet(i) || !valideSommet(j)) {
			System.out.println("sommet entrés inexistants");
		} else if (valideLiaison(i,j)) {
			System.out.println("liaison déjà existante");
		} else {
			liaisons[i][j]=p;
		}
	}
	
	void supprimerLiaisons(int i, int j) {
		if (!valideSommet(i) || !valideSommet(j)) {
			System.out.println("sommet entrés inexistants");
		}
		else if (!valideLiaison(i,j)) {
			System.out.println("liaison inexistante");
		} else {
			liaisons[i][j]=vide;
		}
	}
	

}
