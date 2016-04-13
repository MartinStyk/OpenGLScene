package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;


/**
 * Created by Martin Styk on 13.04.2016.
 */
public class ClockHandTexture extends ConfigurableTexture{

    public ClockHandTexture(GL3 gl) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.CLOCK_HAND));
    }

    public ClockHandTexture(GL3 gl, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.CLOCK_HAND), wrapS, wrapT, wrapR, coordinatesMultiplier, coordinatesOffset);
    }

    public ClockHandTexture(GL3 gl, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset, int minFilter, int magFilter, int dimensions) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.CLOCK_HAND), wrapS, wrapT, wrapR, coordinatesMultiplier, coordinatesOffset, minFilter, magFilter, dimensions);
    }

    public static String getPath(){
        return "/textures/clock-hand.png";
    }
    public static String getType(){
        return TextureIO.PNG    ;
    }

}
