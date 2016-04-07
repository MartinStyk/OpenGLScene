package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import sk.styk.martin.pv112.project.LoadUtils;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_REPEAT;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;

/**
 * Created by Martin Styk on 07.04.2016.
 */
public class RockTexture {
    private static Texture texture;

    protected RockTexture(){};

    public static Texture get(GL3 gl){
        if(texture == null){
            texture = LoadUtils.loadTexture(gl, "/textures/rocks.jpg", TextureIO.JPG);
        }
        texture.bind(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        gl.glGenerateMipmap(GL_TEXTURE_2D);
        return texture;
    }

}
