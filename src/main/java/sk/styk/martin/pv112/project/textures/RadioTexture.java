package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Styk on 16.04.2016.
 */
public class RadioTexture extends ConfigurableTexture {

    private List<RadioTexturePartial> list = new ArrayList<>();

    public RadioTexture(GL3 gl) {
        super();
        list.add(new RadioTexturePartial(gl, TexturesFactory.Types.RADIO1));
        list.add(new RadioTexturePartial(gl, TexturesFactory.Types.RADIO2));

    }

    public static String getPath(int i) {
        return "/textures/radio" + i + ".jpg";
    }

    public static String getType() {
        return TextureIO.JPG;
    }

    public RadioTexturePartial getPartial(int i) {
        return list.get(i);
    }

}
