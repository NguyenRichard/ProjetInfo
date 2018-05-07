package jeu;

public class CreateGrapheDepl {
	
	int depl;
	int[][] cases;
	int firstcase;
	int centre;
	int Nmatricedepl;
	Map map;
	int entraindejouer;
	
	public CreateGrapheDepl(int depl,int firstcase,int centre, int Nmatricedepl, Map map, int entraindejouer) {
		this.depl=depl;
		this.firstcase=firstcase;
		this.centre=centre;
		this.Nmatricedepl=Nmatricedepl;
		this.map=map;
		this.cases=transforme();
		this.entraindejouer=entraindejouer;
	}
	
	Graphe generate() {
		int taille = (2*depl+1)*(2*depl+1);
		Graphe g = new Graphe(taille);
		for (int i=0;i<2*depl+1;i++) {
			for (int j=0;j<2*depl+1;j++) {
				int graphecase = i*(2*depl+1)+j;
				g.ajouterSommet(graphecase);
			}
		}
		for (int i=1;i<2*depl;i++) { // haut
			g.ajouterLiaisons(i,i+(2*depl+1),cases[0][i]);
		}
		for (int i=1;i<2*depl;i++) { // bas
			g.ajouterLiaisons(i+(2*depl)*(2*depl+1),i+(2*depl-1)*(2*depl+1),cases[2*depl][i]);
		}
		for (int i=1;i<2*depl;i++) { // gauche
			g.ajouterLiaisons(i*(2*depl+1),i*(2*depl+1)+1,cases[i][0]);
		}
		for (int i=1;i<2*depl;i++) { // droite
			g.ajouterLiaisons(i*(2*depl+1)+(2*depl+1),i*(2*depl+1)+(2*depl+1)-1,cases[i][2*depl]);
		}
		for (int i=1;i<2*depl;i++) {
			for (int j=1;j<2*depl;j++) {
				int graphecase = i*(2*depl+1)+j;
				g.ajouterLiaisons(graphecase,i*(2*depl+1)+j+1,cases[i][j+1]);
				g.ajouterLiaisons(graphecase,i*(2*depl+1)+j-1,cases[i][j-1]);
				g.ajouterLiaisons(graphecase,(i+1)*(2*depl+1)+j,cases[i+1][j]);
				g.ajouterLiaisons(graphecase,(i-1)*(2*depl+1)+j,cases[i-1][j]);
			}
		}
		return g;
	}
	
	int[][] transforme() {
		int N = 2*depl+1;
		int NN = Nmatricedepl;
		int[][] res = new int[N][N];
		for (int i=0;i<N;i++) {
			for (int j=0;j<N;j++) {
				if ((map.plateau[firstcase+i*NN+j].unite!=null)&&(map.plateau[firstcase+i*NN+j].unite.joueur!=map.joueurs.get(entraindejouer))) {
					res[i][j]=100;
				}
				else {
					res[i][j]=map.plateau[firstcase+i*NN+j].terrain.deplacement;
				}
			}
		}
		cases=res;
		return res;
	}
	
	int[] reversechemin(int[] chemin) {
		int longueur = 0;
		int i=0;
		while ((chemin[i]!=-1)&&(i<chemin.length)) {
			longueur++;
			i++;
		}
		int[] res = new int[longueur];
		for (int j=0;j<=longueur/2;j++) {
			res[j]=chemin[longueur-1-j];
			res[longueur-1-j]=chemin[j];
		}
	
		return res;
	}
	
	

}
