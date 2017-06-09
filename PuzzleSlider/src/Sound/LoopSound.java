package Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import Global.SoundRepository;

public class LoopSound extends Sound {

	public LoopSound(String src) {
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
			        
			        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			        volume.setValue(-10);
			        
			        clip.loop(Clip.LOOP_CONTINUOUSLY);
			      } catch (Exception e) {
//			    	  Probably nothing happens - sound just won't play
			      }
			    }
			  }).start();

	}
	
	
	public static void playBackgroundMusic(){
		new LoopSound(SoundRepository.BACKGROUND_MUSIC).play();
	}

}
