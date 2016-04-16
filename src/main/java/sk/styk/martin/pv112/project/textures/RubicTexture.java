package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class RubicTexture extends ConfigurableTexture {

    private List<RubicTexturePartial> list = new ArrayList<RubicTexturePartial>();

    public RubicTexture(GL3 gl) {
        super();
        for (int i = 0; i < 6; i++) {
            list.add(new RubicTexturePartial(gl, TexturesFactory.Types.values()[i + 13]));
        }
    }

    public static String getPath(int i) {
        return "/textures/rubic" + i + ".png";
    }

    public static String getType() {
        return TextureIO.PNG;
    }

    public RubicTexturePartial getPartial(int i) {
        return list.get(i);
    }

}
