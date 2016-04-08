package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;
import sk.styk.martin.pv112.project.LoadUtils;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class TexturesFactory {

    private static TexturesFactory instance;

    private GL3 gl;

    public enum Types {
        WOOD, ROCKS
    }

    private Texture wood;
    private Texture rocks;
    ;

    protected TexturesFactory(GL3 gl) {
        this.gl = gl;
    }

    public static TexturesFactory getInstance(GL3 gl) {
        if (instance == null) {
            instance = new TexturesFactory(gl);
        }
        instance.gl = gl;
        return instance;
    }

    public Texture get(Types type) {
        switch (type) {
            case WOOD:
                if (wood == null) {
                    wood = LoadUtils.loadTexture(gl, WoodTexture.getPath(), WoodTexture.getType());
                }
                return wood;
            case ROCKS:
                if (rocks == null) {
                    rocks = LoadUtils.loadTexture(gl, RockTexture.getPath(), RockTexture.getType());
                }
                return rocks;
            default:
                throw new IllegalArgumentException("Texture type does not exist");
        }
    }

}
