package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;


/**
 * Created by Martin Styk on 07.04.2016.
 */
public class CivicPictureTexture extends ConfigurableTexture {

    public CivicPictureTexture(GL3 gl) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.PHOTO_CIVIC));
    }

    public CivicPictureTexture(GL3 gl, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.PHOTO_CIVIC), wrapS, wrapT, wrapR, coordinatesMultiplier, coordinatesOffset);
    }

    public CivicPictureTexture(GL3 gl, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset, int minFilter, int magFilter, int dimensions) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.PHOTO_CIVIC), wrapS, wrapT, wrapR, coordinatesMultiplier, coordinatesOffset, minFilter, magFilter, dimensions);
    }

    public static String getPath() {
        return "/textures/photo_civic.jpg";
    }

    public static String getType() {
        return TextureIO.JPG;
    }

}
