package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;

import java.util.Random;

import static com.jogamp.opengl.GL.*;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public abstract class ConfigurableTexture {

    protected Texture texture;
    protected int bufferNumber = new Random().nextInt(32);

    protected int dimensions = GL_TEXTURE_2D;
    protected int minFilter = GL_LINEAR_MIPMAP_LINEAR;
    protected int magFilter = GL_LINEAR;
    protected int wrapS = GL_MIRRORED_REPEAT;
    protected int wrapT = GL_REPEAT;
    protected int wrapR = GL_REPEAT;
    protected int coordinatesMultiplier = 1;
    protected int coordinatesOffset = 0;

    protected ConfigurableTexture() {
    }

    protected ConfigurableTexture(Texture texture) {
        if (texture == null) {
            throw new IllegalArgumentException("texture");
        }
        this.texture = texture;
    }

    protected ConfigurableTexture(Texture texture, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset) {
        if (texture == null) {
            throw new IllegalArgumentException("texture");
        }
        this.texture = texture;
        this.wrapR = wrapR;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
        this.coordinatesMultiplier = coordinatesMultiplier;
        this.coordinatesOffset = coordinatesOffset;
    }

    protected ConfigurableTexture(Texture texture, int wrapS, int wrapT, int wrapR, int coordinatesMultiplier, int coordinatesOffset, int minFilter, int magFilter, int dimensions) {
        if (texture == null) {
            throw new IllegalArgumentException("texture");
        }
        this.texture = texture;
        this.wrapR = wrapR;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
        this.coordinatesMultiplier = coordinatesMultiplier;
        this.coordinatesOffset = coordinatesOffset;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.dimensions = dimensions;
    }


    public Texture get() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public int getMinFilter() {
        return minFilter;
    }

    public void setMinFilter(int minFilter) {
        this.minFilter = minFilter;
    }

    public int getMagFilter() {
        return magFilter;
    }

    public void setMagFilter(int magFilter) {
        this.magFilter = magFilter;
    }

    public int getWrapS() {
        return wrapS;
    }

    public void setWrapS(int wrapS) {
        this.wrapS = wrapS;
    }

    public int getWrapT() {
        return wrapT;
    }

    public void setWrapT(int wrapT) {
        this.wrapT = wrapT;
    }

    public int getWrapR() {
        return wrapR;
    }

    public void setWrapR(int wrapR) {
        this.wrapR = wrapR;
    }

    public int getCoordinatesMultiplier() {
        return coordinatesMultiplier;
    }

    public void setCoordinatesMultiplier(int coordinatesMultiplier) {
        this.coordinatesMultiplier = coordinatesMultiplier;
    }

    public int getCoordinatesOffset() {
        return coordinatesOffset;
    }

    public void setCoordinatesOffset(int coordinatesOffset) {
        this.coordinatesOffset = coordinatesOffset;
    }

    public int getBufferNumber() {
        return bufferNumber;
    }

    public void setBufferNumber(int bufferNumber) {
        this.bufferNumber = bufferNumber;
    }

    public void use(GL3 gl, int coordinatesMultiLoc, int coordinatesOffsetLot, int textureNo) {
        gl.glActiveTexture(GL_TEXTURE0 + bufferNumber);
        texture.bind(gl);
        gl.glUniform1f(coordinatesMultiLoc, coordinatesMultiplier);
        gl.glUniform1f(coordinatesOffsetLot, coordinatesOffset);
        gl.glUniform1i(textureNo, bufferNumber);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapS);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapT);
        gl.glGenerateMipmap(GL_TEXTURE_2D);
    }
}
