package jeu;

public class Dijkstra {
	
	Graphe g;
	int sommetcible;
	int sommetdepart;
	int[][] sommets; // { {distance,provenance,visité (1 non ou 0 oui)},...}
	int nbsommets;
	static final int INF = 1000;
	public final static int vide = -999;
	
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
	
	void changeGrapheDij(Graphe g) {
		this.g = g;
	}
	
	int runDijkstra() {
		int sommetenvisite = sommetdepart;
		//displaytot(sommets);
		while (sommets[sommetcible][2]==1) {
			//System.out.println(sommetenvisite);
			visiteSommet(sommetenvisite);
			//displaytot(sommets);
			sommetenvisite = plusPetitNonVisité();
			if (sommetenvisite==-1) {
				return sommets[sommetcible][0];
			}
		}
		return sommets[sommetcible][0];
	}
	
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
	
	int plusPetitNonVisité() {
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
	
	void displaytot(int[][] tab) {
		System.out.print("{");
		display(tab[0]);
		for (int i = 1; i<tab.length;i++) {
			System.out.print(", ");
			display(tab[i]);
		}
		System.out.println("}");
	}
	
	void display(int[] tab) {
		System.out.print("{" + tab[0]);
		for (int i = 1; i<tab.length;i++) {
			System.out.print(", " + tab[i]);
		}
		System.out.print("}");
	}
	
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
