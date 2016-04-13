package sk.styk.martin.pv112.project.tooling;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Martin Styk on 13.04.2016.
 */
public class SoundUtils extends Thread{

    private AudioInputStream audioIn;
    private Clip clip;

    public SoundUtils(String path) {
        // Open an audio input stream.
        URL url = this.getClass().getResource(path);
        try {
            audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        }catch (Exception e){
            System.out.println("Error loading sound" + e);
        }
    }

    public void run(){

        // Open audio clip and load samples from the audio input stream.
        try {
            clip.start();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    public void close(){
        clip.close();
        try {
            audioIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
