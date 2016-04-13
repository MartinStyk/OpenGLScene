package sk.styk.martin.pv112.project.tooling;

import com.hackoeur.jglm.Mat;

import java.time.LocalDateTime;

/**
 * Created by Martin Styk on 13.04.2016.
 */
public class ClockRotate {

    public static float getMinuteHandRotation() {
        int minute = LocalDateTime.now().getMinute();
        float ret = (float)(minute * Math.PI / 30 );
        return ret - ((float)Math.PI /2);
    }

    public static float getHourHandRotation() {
        int hour = LocalDateTime.now().getHour();
        return (float)(hour * Math.PI / 6 );
    }

    public static float getSecondsHandRotation() {
        int second = LocalDateTime.now().getSecond();
        float ret = (float)(second * Math.PI / 30 );
        return ret - ((float)Math.PI /2);
    }
}
