package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.util.texture.Texture;

import static com.jogamp.opengl.GL.*;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public abstract class ConfigurableTexture {

    private Texture texture;
    private int dimensions = GL_TEXTURE_2D;
    private int minFilter = GL_LINEAR_MIPMAP_LINEAR;
    private int magFilter = GL_LINEAR;
    private int wrapS = GL_MIRRORED_REPEAT;
    private int wrapT = GL_REPEAT;
    private int wrapR = GL_REPEAT;
    private int coordinatesMultiplier = 1;
    private int coordinatesOffset = 0;

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
}
