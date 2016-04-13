package sk.styk.martin.pv112.project.tooling;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Created by Martin Styk on 13.04.2016.
 */
public class SoundRunnable implements Runnable {
    private AudioInputStream audioIn;
    private Clip clip;

    public SoundRunnable(String path) {
        // Open an audio input stream.
        URL url = this.getClass().getResource(path);
        try {
            audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            System.out.println("Error loading sound" + e);
        }
    }

    @Override
    public void run() {
        try {
            clip.setFramePosition(0);
            clip.start();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
