package sk.styk.martin.pv112.project.tooling;

import java.time.LocalDateTime;

/**
 * Created by Martin Styk on 13.04.2016.
 */
public class ClockUtils {

    private Float previousSecondPosition;
    private SoundRunnable soundRunnable = new TickSoundRunnable("/sounds/tick.wav");
    private boolean isSoundTriggered = false;

    public float getMinuteHandRotation() {
        int minute = LocalDateTime.now().getMinute();
        float ret = (float) (minute * Math.PI / 30);
        return ret - ((float) Math.PI / 2);
    }

    public float getHourHandRotation() {
        int hour = LocalDateTime.now().getHour();
        return  (float) (hour * Math.PI / 6) + getMinuteHandRotation()/12;
    }

    public float getSecondsHandRotation() {
        int second = LocalDateTime.now().getSecond();
        float ret = (float) (second * Math.PI / 30) - ((float) Math.PI / 2);
        if (previousSecondPosition != null && !isSoundTriggered && (previousSecondPosition > ret + 0.05f || previousSecondPosition < ret - 0.05f)) {
            isSoundTriggered = true;
            new Thread(soundRunnable).start();
        }
        previousSecondPosition = ret;

        return previousSecondPosition;
    }
}
