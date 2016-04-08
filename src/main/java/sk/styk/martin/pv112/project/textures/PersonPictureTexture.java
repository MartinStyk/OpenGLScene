package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;


/**
 * Created by Martin Styk on 07.04.2016.
 */
public class PersonPictureTexture extends ConfigurableTexture{

    public PersonPictureTexture(GL3 gl) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.PHOTO_PERSONAL));
    }

    public PersonPictureTexture(GL3 gl, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.PHOTO_PERSONAL), wrapS, wrapT, wrapR, coordinatesMultiplier, coordinatesOffset);
    }

    public PersonPictureTexture(GL3 gl, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset, int minFilter, int magFilter, int dimensions) {
        super(TexturesFactory.getInstance(gl).get(TexturesFactory.Types.PHOTO_PERSONAL), wrapS, wrapT, wrapR, coordinatesMultiplier, coordinatesOffset, minFilter, magFilter, dimensions);
    }

    public static String getPath(){
        return "/textures/photo_personal.jpg";
    }
    public static String getType(){
        return TextureIO.JPG;
    }

}
