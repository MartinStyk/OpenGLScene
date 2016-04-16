package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat4;
import sk.styk.martin.pv112.project.materials.BlackPlastic;
import sk.styk.martin.pv112.project.materials.BlackRubber;
import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.materials.WhiteRubber;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ClockTexture;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;
import sk.styk.martin.pv112.project.tooling.ClockUtils;

/**
 * Created by Martin Styk on 13.04.2016.
 */
public class Clock extends Drawable {

    private ClockHourHand clockHourHand;
    private ClockMinuteHand clockSecondHand;
    private ClockMinuteHand clockMinuteHand;
    private ClockUtils clockUtils = new ClockUtils();

    public Clock(Program program) {
        this(program, WhiteRubber.getInstance(), new ClockTexture(program.getGL()));
    }

    public Clock(Program program, Material material, ConfigurableTexture texture) {
        super(program, material, texture, "/models/clock.obj");
        //ClockHandTexture texture1 = new ClockHandTexture(program.getGL());
        clockHourHand = new ClockHourHand(program, BlackPlastic.getInstance());
        clockMinuteHand = new ClockMinuteHand(program, BlackPlastic.getInstance());
        clockSecondHand = new ClockMinuteHand(program, BlackRubber.getInstance());
    }

    public void draw(Mat4 mvp, Mat4 mvpHour, Mat4 mvpMinute, Mat4 mvpSec) {
        super.draw(mvp);
        clockHourHand.draw(mvpHour);
        clockMinuteHand.draw(mvpMinute);
        clockSecondHand.draw(mvpSec);
    }

    public ClockMinuteHand getClockMinuteHand() {
        return clockMinuteHand;
    }

    public ClockHourHand getClockHourHand() {
        return clockHourHand;
    }

    public ClockMinuteHand getClockSecondHand() {
        return clockSecondHand;
    }

    public ClockUtils getClockUtils() {
        return clockUtils;
    }
}
