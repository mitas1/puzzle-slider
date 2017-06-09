package Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Global.SoundRepository;

public class SimpleSound extends Sound {

	public SimpleSound(String src) {
		super(src);
	}

	@Override
	public void play() {
		  new Thread(new Runnable() {
			    public void run() {
			      try {
			        Clip clip = AudioSystem.getClip();
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
			        		this.getClass().getClassLoader().getResourceAsStream(mSrc));
			        clip.open(inputStream);
			        clip.start(); 
			      } catch (Exception e) {
//			    	  Probably nothing happens - sound just won't play
			      }
			    }
			  }).start();

	}
	
	
	public static void playSlideSound(){
		new SimpleSound(SoundRepository.SLIDE_SOUND).play();
	}
	
	public static void playButtonSound(){
		new SimpleSound(SoundRepository.BUTTON_SOUND).play();
	}
	
	public static void playVictorySound(){
		new SimpleSound(SoundRepository.VICTORY_SOUND).play();
	}
	

}
