/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat4;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;
import sk.styk.martin.pv112.project.Geometry;
import sk.styk.martin.pv112.project.LoadUtils;
import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;

/**
 *
 * @author Martin Styk
 */
public abstract class Drawable {
    
    protected Program program;
    protected Geometry geometry;
    protected Material material;
    protected ConfigurableTexture texture;
    protected Mat4 mvp;
    protected Mat4 model;

    public Drawable(Program program, String path){
        this(program, null,null, path);
    }
    
    public Drawable(Program program, Material material, ConfigurableTexture texture,String path){
        this.program = program;
        this.texture = texture;
        this.material = material;
        geometry = LoadUtils.loadGeometry(program.getGL(), program.getID(), path);
    }
    
    /**
     * @return the gl
     */
    public GL3 getGl() {
        return program.getGL();
    }

    /**
     * @return the program
     */
    public Program getProgram() {
        return program;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(Program program) {
        this.program = program;
    }

    /**
     * @return the geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * @param geometry the geometry to set
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * @return the mvp
     */
    public Mat4 getMvp() {
        return mvp;
    }

    /**
     * @param mvp the mvp to set
     */
    public void setMvp(Mat4 mvp) {
        this.mvp = mvp;
    }

    /**
     * @return the model
     */
    public Mat4 getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(Mat4 model) {
        this.model = model;
    }

    /**
     * @return the texture
     */
    public ConfigurableTexture getTexture() {
        return texture;
    }

    /**
     * @param texture the texture to set
     */
    public void setTexture(ConfigurableTexture texture) {
        this.texture = texture;
    }
    
    public abstract void draw();
    
    public void draw(Mat4 model, Mat4 mvp){
        this.setModel(model);
        this.setMvp(mvp);
        draw();
    }
}
