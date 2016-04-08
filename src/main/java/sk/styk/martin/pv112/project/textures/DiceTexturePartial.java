package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class DiceTexturePartial extends ConfigurableTexture {
    public DiceTexturePartial(GL3 gl, TexturesFactory.Types type) {
        super(TexturesFactory.getInstance(gl).get(type));
    }
}
