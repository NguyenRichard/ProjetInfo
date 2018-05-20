package son;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Son  {
	public int soundlevelmusic; // max 0, min 30
	public int soundleveleffect; // max 0, min 30
	
	
	public Son() {
		soundlevelmusic=5;
		soundleveleffect=10;
	}
	
	public void changeVolumeMusic(Clip clip, int a) {
		soundlevelmusic+=a;
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-1 * soundlevelmusic);
	}
	
	public void changeVolumeEffects(int a) {
		soundleveleffect+=a;
		try {
	         // Open an audio input stream.
	         URL url = this.getClass().getClassLoader().getResource("Sons/soin2.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         Clip clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	         volume.setValue(-1 * soundleveleffect);
	         clip.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}
	
	public void runSoundattack() {
	 try {
         // Open an audio input stream.
         URL url = this.getClass().getClassLoader().getResource("Sons/attaque.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
         volume.setValue(-1 * soundleveleffect);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
	}
	
	public void runSoundheal() {
		 try {
	         // Open an audio input stream.
	         URL url = this.getClass().getClassLoader().getResource("Sons/soin2.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         Clip clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	         volume.setValue(-1 * soundleveleffect);
	         clip.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
		}
	
	public void runSoundrefresh() {
		try {
	         // Open an audio input stream.
	         URL url = this.getClass().getClassLoader().getResource("Sons/soin2.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         Clip clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	         volume.setValue(-1 * soundleveleffect);
	         clip.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
		}

	public Clip boucle()  {
		try {
	         // Open an audio input stream.
	         URL url = this.getClass().getClassLoader().getResource("Sons/musique_jeu.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         Clip clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	         volume.setValue(-1 * soundlevelmusic);
	         clip.setLoopPoints(1, 3042800);
	         clip.setFramePosition(1);
	         clip.loop(10000);
	         //System.out.println(clip.getFrameLength());
	         //System.out.println(clip.getMicrosecondLength());
	         return clip;
	         
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
		return null;
	}
	
}