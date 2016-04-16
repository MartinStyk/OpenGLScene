/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat3;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.MatricesUtils;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;
import sk.styk.martin.pv112.project.tooling.Geometry;
import sk.styk.martin.pv112.project.tooling.LoadUtils;

/**
 * @author Martin Styk
 */
public abstract class Drawable {

    protected Program program;
    protected Geometry geometry;
    protected Material material;
    protected ConfigurableTexture texture;
    protected Mat4 mvp = Mat4.MAT4_IDENTITY;
    protected Mat4 model = Mat4.MAT4_IDENTITY;

    public Drawable(Program program, String path) {
        this(program, null, null, path);
    }

    public Drawable(Program program, Material material, ConfigurableTexture texture, String path) {
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

    /**
     * basic draw scenation in BasicProgram
     * materials and textures supported
     */
    public void draw() {
        GL3 gl = program.getGL();
        gl.glUseProgram(program.getID());

        Mat3 n = MatricesUtils.inverse(MatricesUtils.getMat3(model).transpose());
        gl.glUniformMatrix3fv(program.getUniformLoc(BasicProgram.N), 1, false, n.getBuffer());
        gl.glUniformMatrix4fv(program.getUniformLoc(BasicProgram.MODEL), 1, false, model.getBuffer());
        gl.glUniformMatrix4fv(program.getUniformLoc(BasicProgram.MVP), 1, false, mvp.getBuffer());
        gl.glUniform3f(program.getUniformLoc(BasicProgram.COLOR), 1f, 1f, 0.2f);

        if (material != null) {
            material.bindUniforms(gl,
                    program.getUniformLoc(BasicProgram.MATERIAL_AMBIENT_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_DIFFUSE_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_SPECULAR_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_SHININESS)
            );
        }

        if (texture != null) {
            gl.glUniform1i(program.getUniformLoc(BasicProgram.IS_TEXTURE), 1);
            texture.use(gl,
                    program.getUniformLoc(BasicProgram.TEXTURE_COORDINATES_MULTIPLIER),
                    program.getUniformLoc(BasicProgram.TEXTURE_COORDINATES_OFFSET),
                    program.getUniformLoc(BasicProgram.TEXTURE));
        }

        geometry.draw(gl);

        //not texture
        gl.glUniform1i(program.getUniformLoc(BasicProgram.IS_TEXTURE), 0);

        gl.glUseProgram(0);
    }

    ;

    public void draw(Mat4 mvp) {
        this.setMvp(mvp);
        draw();
    }

    public void draw(Mat4 model, Mat4 mvp) {
        this.setModel(model);
        this.setMvp(mvp);
        draw();
    }
}
