package jeu;

public abstract class Terrain extends Element{
	/**cout en deplacement pour se rendre sur la case */
	protected int deplacement;
	
	/*Methode de base de l'objet__ */
    /**
     * Constructeur de terrain:
     *      Constructeur mere, il se declinera en constructeurs fille pour les differents terrains.
     * @param taille
     *             Entier qui decrit la taille de l'unite en pixel lors de l'affichage.
     * @see jeu.Case.Case
     * 
     * La taille du terrain est fixee par la taille de la case
     *
     */
    protected Terrain(int taille){
        super(taille);
    }
    
    public abstract String toString();


}
