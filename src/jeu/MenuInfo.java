package jeu;

import batiments.Portal;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MenuInfo {
	/**Carte du jeu */
	Map map;
	/**Image de l'element qu'il faut afficher */
	Image[] images;
	/**position x du menu*/
	int positionxmenuinfo;
	/**position y du menu*/
	int positionymenuinfo;

	MenuInfo(Map map, int positionxmenu) {
		this.map = map;
		this.positionxmenuinfo=positionxmenu+10;
		positionymenuinfo=500;
	}

	MenuInfo(int positionxmenu){
		this.positionxmenuinfo=positionxmenu+10;

	}

	void MenuInforender(GraphicsContext gc) {
		Case selectionne = map.selectionne;
		if (selectionne != null) {

			if (selectionne.unite!=null) {
				InfoUniterender(gc, selectionne.unite, positionymenuinfo, false);
			} else if (selectionne.batiment!=null) {
				int animcompteur= selectionne.batiment.animcompteur;
				int maxcompteur = selectionne.batiment.maxcompteur;
				this.images = selectionne.batiment.images;
				int k = animcompteur / (maxcompteur/images.length);
				if (k >= images.length) {
					animcompteur = 0;
					k=0;
				}
				gc.drawImage(images[k],positionxmenuinfo+10,positionymenuinfo+50);
				gc.fillText(selectionne.batiment.toString(), positionxmenuinfo*1.03, positionymenuinfo);
				gc.strokeText(selectionne.batiment.toString(), positionxmenuinfo*1.03, positionymenuinfo);
				if (selectionne.batiment instanceof Portal) {
					Portal portail = (Portal) selectionne.batiment;
					if (portail.uniteainvoque!=null) {
						portail.uniteainvoque.render(gc, positionxmenuinfo+150, positionymenuinfo+50);
					}
				}
			} else {
				this.images = selectionne.terrain.images;
				gc.drawImage(images[0],positionxmenuinfo*1.04,positionymenuinfo+50);
				gc.fillText(selectionne.terrain.toString(), positionxmenuinfo*1.03, positionymenuinfo);
				gc.strokeText(selectionne.terrain.toString(), positionxmenuinfo*1.03, positionymenuinfo);
			}

		}
	}

	/**
	 * Permet d'afficher les infos d'une unité ainsi qu'un visuel
	 * 
	 * Parameters :
	 * 
	 * gc : GarphicsContext dans lequel le jeu se passe
	 * unite : Unite à afficher
	 * positionymenuinfo : position verticale de l'affichage
	 * pourinvocation : booleen indiquant si on veut afficher le cout (utile lors des invocations
	 * uniteinvoquee : indique si l'unite est celle en train d'être invoquée (pour être indiqué au joueur)
	 * */
	void InfoUniterender(GraphicsContext gc, Unite unite, int positionymenuinfo, boolean pourinvocation) {
		int animcompteur= unite.animcompteur;
		int maxcompteur = unite.maxcompteur;
		this.images = unite.images;
		int k = animcompteur / (maxcompteur/images.length);
		if (k >= images.length) {
			animcompteur = 0;
			k=0;
		}
		gc.drawImage(images[k],positionxmenuinfo+10,positionymenuinfo+50);
		
		String txtatq;
		if (unite.dmg>0) {txtatq = "attaque : " + unite.dmg; }
		else {txtatq = "soin : " + (-1)*unite.dmg;}
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.fillText(txtatq, positionxmenuinfo*1.15, positionymenuinfo+50);
		gc.strokeText(txtatq, positionxmenuinfo*1.15, positionymenuinfo+50);

		String txtvie = "vie : " + unite.pv;
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.fillText(txtvie, positionxmenuinfo*1.15, positionymenuinfo+100);
		gc.strokeText(txtvie, positionxmenuinfo*1.15, positionymenuinfo+100);

		int porteemin = unite.portee[0] + 1;
		if (porteemin != 1) { //affichage dans menuinfo de la portee avec un minimum
			String txtportee = "portee : " + porteemin + "~" + unite.portee[1];
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(txtportee, positionxmenuinfo*1.15, positionymenuinfo+150);
			gc.strokeText(txtportee, positionxmenuinfo*1.15, positionymenuinfo+150);
		} 
		else { //affichage dans menuinfo de la portee sans minimum
			String txtportee = "portee : " + unite.portee[1];
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(txtportee, positionxmenuinfo*1.15, positionymenuinfo+150);
			gc.strokeText(txtportee, positionxmenuinfo*1.15, positionymenuinfo+150);
		}

		if (unite.volant) { //affichage dans menuinfo des specifications liees au type de l'unite
			String type = "type : " + unite.type + " | " + "volant";
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(type, positionxmenuinfo*1.03, positionymenuinfo+200);
			gc.strokeText(type, positionxmenuinfo*1.03, positionymenuinfo+200);
		}
		else {
			String type = "type : " + unite.type + " | " + "terrestre";
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(type, positionxmenuinfo*1.03, positionymenuinfo+200);
			gc.strokeText(type, positionxmenuinfo*1.03, positionymenuinfo+200);
		}


		String txtnom = unite.toString();
		gc.fillText(txtnom, positionxmenuinfo*1.03, positionymenuinfo);
		gc.strokeText(txtnom, positionxmenuinfo*1.03, positionymenuinfo);

		if(pourinvocation) {
			String deplacement = "deplacement : " + unite.deplacement;
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(deplacement, positionxmenuinfo*1.03, positionymenuinfo+250);
			gc.strokeText(deplacement, positionxmenuinfo*1.03, positionymenuinfo+250);

			String descriptiontype = "----------------\n";
			switch(unite.type){
			case "soldat" :
				descriptiontype += "Unite specialise dans le \ncorp a corp";
				break;
			case "soigneur" :
				descriptiontype += "Unite capable de soigner \nmais pas de se battre";
				break;
			case "assassin" :
				descriptiontype += "Unite qui peut rejouer \nchaque fois qu'elle \nporte le coup de grace";
				break;
			case "zone1" :
				descriptiontype += "Unite infligeant ses degats \ndans une petite zone\n(peut toucher des allies)";
				break;
			case "zone2" :
				descriptiontype += "Unite infligeant ses degats \ndans une grande zone\n(peut toucher des allies)";
				break;
			case "ligne" :
				descriptiontype += "Unite infligeant ses degats \nsur une ligne\n(peut toucher des allies)";
				break;
			case "mineur" :
				descriptiontype += "Unite capable de capturer \nen 1 tour au lieu de 2 \nmais pas de se battre";
				break;
			case "zonesoin" :
				descriptiontype += "Unite capable de soigner \ndans une petite zone \n(peut toucher des ennemis)";
				break;
			}
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(descriptiontype, positionxmenuinfo*1.03, positionymenuinfo+300);
			gc.strokeText(descriptiontype, positionxmenuinfo*1.03, positionymenuinfo+300);
		}
		else {
			int ratiopvpvmax = (int) 100*unite.pv/unite.pvmax;
			gc.setFill(Color.LIMEGREEN);
			gc.fillRect(positionxmenuinfo*1.4, positionymenuinfo+50, 15, 100);
			gc.setFill(Color.RED);
			gc.fillRect(positionxmenuinfo*1.4, positionymenuinfo+50, 15, 100-ratiopvpvmax);
		}
	}
}
