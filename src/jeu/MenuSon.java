package jeu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import son.Son;

import javax.sound.sampled.Clip;

public class MenuSon {
	/** Position du menu sur l'axe des abscisses*/
	int positionxmenu;
	/** Position du menu sur l'axe des ordonnees*/
	int positionymenu;
	/** Position du curseur */
	int positioncurseur;
	/** Position maximale du curseur dans le menu 0*/
	int maxpos;
	/** Curseur du popup confiramtion */
	Image curseur;
	/** Image du menu 0*/
	Image menuim;
	/** Boolean qui decrit si on est dans le menu option ou non*/
	boolean inmenusd;
	boolean update;
	Clip clip;
	Son sd;
	
	
	MenuSon(int width, int height, Clip clip, Son sd){
		menuim=new Image("menuson.png",250,370,false,false);
		curseur=new Image("curseurmenuson.png",250,370,false,false);
		positionxmenu=(int) width/2-125;
		positionymenu=(int) height/3-100;
		inmenusd=false;
		maxpos=2;
		update=false;
		this.clip = clip;
		this.sd = sd;
		
	}
	
	private void rendertxt(GraphicsContext gc) {
		String txt1;
		int script = sd.soundlevelmusic*(-1)+30+1;
		if (sd.soundlevelmusic==0) {
			txt1 = "Max";
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt1, positionxmenu+85, 278);
	        gc.strokeText(txt1, positionxmenu+85, 278);
		} else if (sd.soundlevelmusic==80) {
			txt1 = "Sans Son";
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt1, positionxmenu+65, 278);
	        gc.strokeText(txt1, positionxmenu+65, 278);
		} else {
			txt1 = ""+ script;
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt1, positionxmenu+105, 278);
	        gc.strokeText(txt1, positionxmenu+105, 278);
		}
        
        String txt2;
        int script2 = sd.soundleveleffect*(-1)+30+1;
		if (sd.soundleveleffect==0) {
			txt2 = "Max";
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt2, positionxmenu+85, 278+160);
	        gc.strokeText(txt2, positionxmenu+85, 278+160);
		} else if (sd.soundleveleffect==80) {
			txt2 = "Sans Son";
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
	        gc.setFill(Color.BISQUE);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(1);
	        gc.fillText(txt2, positionxmenu+65, 278+160);
	        gc.strokeText(txt2, positionxmenu+65, 278+160);
		} else {
			txt2 = ""+ script2;
			gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
			gc.setFill(Color.BISQUE);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.fillText(txt2, positionxmenu+105, 278+160);
			gc.strokeText(txt2, positionxmenu+105, 278+160);
		}
	}
	
	public void render(GraphicsContext gc){
		gc.drawImage(menuim, positionxmenu, positionymenu);
		rendertxt(gc);
		switch(positioncurseur) {
		case 0:
			gc.drawImage(curseur, positionxmenu, positionymenu);
			break;
		case 1:
			gc.drawImage(curseur, positionxmenu, positionymenu+160);
			break;
		default:
			break;
		}
	}
	
	/** Deplacement du curseur vers le bas*/
	public void upcurseur(){
		positioncurseur-=1;
			if (positioncurseur<=-1) {
				positioncurseur=0;
			}
	}
	
	/** Deplacement du curseur vers le bas*/
	public void downcurseur() {
		positioncurseur+=1;
			if (positioncurseur>=maxpos) {
				positioncurseur=1;
			}
	}
	
	/** Augmente le niveau de la musique*/
	public void moreMusic() {
		if (sd.soundlevelmusic==80) {
			sd.changeVolumeMusic(clip, -50);
		}
		else if ((sd.soundlevelmusic<=30)&&(sd.soundlevelmusic>0)) {
			sd.changeVolumeMusic(clip, -1);
		}
	}
	
	/** Diminue le niveau de la musique*/
	public void lessMusic() {
		if (sd.soundlevelmusic==30) {
			sd.changeVolumeMusic(clip, 50);
		}
		else if ((sd.soundlevelmusic<30)&&(sd.soundlevelmusic>=0)) {
			sd.changeVolumeMusic(clip, 1);
		}
	}
	
	/** Augmente le niveau des effets*/
	public void moreEffect() {
		if (sd.soundleveleffect==80) {
			sd.changeVolumeEffects(-50);
		}
		else if ((sd.soundleveleffect<=30)&&(sd.soundleveleffect>0)) {
			sd.changeVolumeEffects(-1);
		}
	}
	
	/** Diminue le niveau des effets*/
	public void lessEffect() {
		if (sd.soundleveleffect==30) {
			sd.changeVolumeEffects(50);
		}
		else if ((sd.soundleveleffect<30)&&(sd.soundleveleffect>=0)) {
			sd.changeVolumeEffects(1);
		}
	}

}
