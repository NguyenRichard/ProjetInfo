package jeu;

public class Graphe {
	
	public int [][] liaisons;
	public boolean[] valid;
	public final static int vide = -999;
	
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
	
	boolean valideSommet(int i) {
		if ((i >= valid.length) || (i<0)) {
			return false;
		}
		else {
			return valid[i];
		}
	}
	
	int nbSommet() {
		int res = 0;
		while ((res<valid.length) && (valid[res])) {
			res++;
		}
		return res;
	}
	
	boolean valideLiaison(int i,int j) {
		return (valideSommet(i)&&valideSommet(j))&&(liaisons[i][j]!=vide);
	}
	
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
