package sk.styk.martin.pv112.project.tooling;

/**
 * Sound of ticking clocks
 * Triggered in Clock utils on first second clock move, plays tick sounds repeatedly in one socond intervals
 * <p>
 * Created by Martin Styk on 16.04.2016.
 */
public class TickSoundRunnable extends SoundRunnable {
    public TickSoundRunnable(String path) {
        super(path);
    }

    @Override
    public void run() {
        try {
            while (true) {
                clip.setFramePosition(0);
                clip.start();
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
