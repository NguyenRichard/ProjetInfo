package jeu;

public class Dijkstra {
	
	/**Type Graphe le graphe sur lequel on utilise l'algorithme du plus chemin */
	Graphe g;
	/**Noeud de depart */
	int sommetcible;
	/**Noeud d'arrivee */
	int sommetdepart;
	/**Matrice semblable a  celle d'adjacence avec plus d'informations : { {distance,provenance,visite (1 non ou 0 oui)},...} */
	int[][] sommets;
	/**Nombre de sommets du graphe */
	int nbsommets;
	/**entier static symbolisant INF */
	static final int INF = 1000;
	/**entier static symbolisant le vide */
	public final static int vide = -999;
	
	/**
	 * Constructeur de Dijkstra.
	 * 
	 * @param g 
	 * 		Gaphe sur lequel on applique l'alorithme du plus court chemin
	 * 			
	 */
	Dijkstra(Graphe g, int sommetdepart, int sommetcible) {
		this.g = g;
		this.sommetcible=sommetcible;
		this.sommetdepart =sommetdepart;
		nbsommets = g.nbSommet();
		sommets = new int[nbsommets][3];
		//System.out.println(nbsommets);
		for (int k=0;k<nbsommets;k++) {
			sommets[k][0] = INF;
			sommets[k][2] = 1;
		}
		
		sommets[sommetdepart][0] = 0;
		sommets[sommetdepart][1] = sommetdepart;
		sommets[sommetdepart][2] = 1;
		
	}
	
	/**
	 * Changer le graphe de la classe
	 * 			
	 */
	void changeGrapheDij(Graphe g) {
		this.g = g;
	}
	
	/**
	 * Algorithme trouvant le plus court chemin entre deux Noeuds
	 * 
	 * Renvoie le "poids" du chemin
	 * 			
	 */
	int runDijkstra() {
		int sommetenvisite = sommetdepart;
		//displaytot(sommets);
		while (sommets[sommetcible][2]==1) {
			//System.out.println(sommetenvisite);
			visiteSommet(sommetenvisite);
			//displaytot(sommets);
			sommetenvisite = plusPetitNonVisite();
			if (sommetenvisite==-1) {
				return sommets[sommetcible][0];
			}
		}
		return sommets[sommetcible][0];
	}
	
	/**
	 * Trouve le plus petit sommets (en distance) non visite
	 * 			
	 */
	int pluspetitsommet() {
		int res = INF;
		int rang = sommetdepart;
		for(int i = 0; i<sommets.length;i++) {
			if (res > sommets[i][0]) {
				res = sommets[i][0];
				rang = i;
			}
		}
		return rang;
	}
	
	/**
	 * 
	 * Visite tous les voisins d'un sommet
	 * 			
	 */
	void visiteSommet(int sommet) {
		int poids = sommets[sommet][0];
		for (int i = 0; i<g.valid.length;i++) {
			int a = g.liaisons[sommet][i];
			if (g.valideLiaison(sommet, i)) {
				if ((poids+a<sommets[i][0])&&(sommets[i][2]==1)) {
					sommets[i][0]=poids+a;
					sommets[i][1]=sommet;
				}
			}
		}
		sommets[sommet][2]=0;
	}
	
	/**
	 * Trouve le plus petit sommets (en distance) non visite
	 * 			
	 */
	int plusPetitNonVisite() {
		int res = INF;
		int rang = -1;
		for (int i = 0;i<sommets.length;i++) {
			if ((sommets[i][0]<res)&&(sommets[i][2]==1)) {
				res = sommets[i][0];
				rang = i;
			}
		}
		return rang;
	}
	
	/**
	 * Affichage tableau de tableau d'entier
	 * 			
	 */
	void displaytot(int[][] tab) {
		System.out.print("{");
		display(tab[0]);
		for (int i = 1; i<tab.length;i++) {
			System.out.print(", ");
			display(tab[i]);
		}
		System.out.println("}");
	}
	
	/**
	 * affichage tableau d'entier
	 * 			
	 */
	void display(int[] tab) {
		System.out.print("{" + tab[0]);
		for (int i = 1; i<tab.length;i++) {
			System.out.print(", " + tab[i]);
		}
		System.out.print("}");
	}
	
	/**
	 * Renvoie le chemin le plus court entre le sommets de depart et celui d'arrivee en prenant la matrice final
	 * 			
	 */
	int[] chemin() {
		int[] chemin = new int[sommets.length];
		for (int i=0;i<sommets.length;i++) {
			chemin[i]=-1;
		}
		int suiveurchemin = sommetcible;
		chemin[0]=sommetcible;
		int k = 1;
		while (suiveurchemin != sommetdepart) {
			suiveurchemin = sommets[suiveurchemin][1];
			chemin[k]=suiveurchemin;
			k++;
		}
		return chemin;
	}
	

}
