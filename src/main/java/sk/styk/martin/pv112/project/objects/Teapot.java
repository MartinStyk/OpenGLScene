/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat3;
import com.hackoeur.jglm.MatricesUtils;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;
import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;

/**
 *
 * @author Martin Styk
 */
public class Teapot extends Drawable {

    public Teapot(Program program) {
        this(program, null, null);
    }

    public Teapot(Program program, Material material) {
        super(program, material, null, "/models/teapot-high.obj");
    }

    public Teapot(Program program, Material material, Texture texture) {
        super(program, material, texture, "/models/teapot-high.obj");
    }

    @Override
    public void draw() {
        GL3 gl = program.getGL();
        gl.glUseProgram(program.getID());

        Mat3 n = MatricesUtils.inverse(MatricesUtils.getMat3(model).transpose());
        gl.glUniformMatrix3fv(program.getUniformLoc(BasicProgram.N), 1, false, n.getBuffer());

        gl.glUniformMatrix4fv(program.getUniformLoc(BasicProgram.MODEL), 1, false, model.getBuffer());

        if (material != null) {
            material.bindUniforms(gl,
                    program.getUniformLoc(BasicProgram.MATERIAL_AMBIENT_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_DIFFUSE_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_SPECULAR_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_SHININESS)
            );
        }

        gl.glUniformMatrix4fv(program.getUniformLoc(BasicProgram.MVP), 1, false, mvp.getBuffer());

        gl.glUniform3f(program.getUniformLoc(BasicProgram.COLOR), 1f, 1f, 0.2f);

        geometry.draw(gl);

        gl.glUseProgram(0);
    }
}
