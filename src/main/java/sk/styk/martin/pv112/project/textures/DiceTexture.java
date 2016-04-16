package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class DiceTexture extends ConfigurableTexture {

    private List<DiceTexturePartial> list = new ArrayList<DiceTexturePartial>();

    public DiceTexture(GL3 gl) {
        super();
        for (int i = 0; i < 6; i++) {
            list.add(new DiceTexturePartial(gl, TexturesFactory.Types.values()[i + 2]));
        }
    }

    public static String getPath(int i) {
        return "/textures/dice" + i + ".png";
    }

    public static String getType() {
        return TextureIO.PNG;
    }

    public DiceTexturePartial getPartial(int i) {
        return list.get(i);
    }

}
