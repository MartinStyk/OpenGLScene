package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat3;
import com.hackoeur.jglm.MatricesUtils;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.materials.ChromeMaterial;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;
import sk.styk.martin.pv112.project.textures.DiceTexture;

import static com.jogamp.opengl.GL.*;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class Dice extends Cube {
    public Dice(Program program) {
        super(program, ChromeMaterial.getInstance(), new DiceTexture(program.getGL()));
    }

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

        gl.glUniform1i(program.getUniformLoc(BasicProgram.IS_TEXTURE), 1);

        gl.glActiveTexture(GL_TEXTURE0 + getTexture().getBufferNumber());
        gl.glUniform1i(program.getUniformLoc(BasicProgram.TEXTURE), getTexture().getBufferNumber());
        gl.glUniform1f(program.getUniformLoc(BasicProgram.TEXTURE_COORDINATES_MULTIPLIER), getTexture().getCoordinatesMultiplier());
        gl.glUniform1f(program.getUniformLoc(BasicProgram.TEXTURE_COORDINATES_OFFSET), getTexture().getCoordinatesOffset());

        DiceTexture diceTexture = (DiceTexture) getTexture();

        bindTexture(diceTexture, 0, gl);
        geometry.drawTriangles(gl, 0, 6);
        bindTexture(diceTexture, 1, gl);
        geometry.drawTriangles(gl, 6, 6);
        bindTexture(diceTexture, 5, gl);
        geometry.drawTriangles(gl, 12, 6);
        bindTexture(diceTexture, 4, gl);
        geometry.drawTriangles(gl, 18, 6);
        bindTexture(diceTexture, 3, gl);
        geometry.drawTriangles(gl, 24, 6);
        bindTexture(diceTexture, 2, gl);
        geometry.drawTriangles(gl, 30, 6);

        //not texture
        gl.glUniform1i(program.getUniformLoc(BasicProgram.IS_TEXTURE), 0);

        gl.glUseProgram(0);
    }

    private void bindTexture(DiceTexture diceTexture, int i, GL3 gl) {
        ConfigurableTexture t = diceTexture.getPartial(i);
        t.get().bind(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, t.getMinFilter());
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, t.getMagFilter());
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, t.getWrapS());
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, t.getWrapT());
        gl.glGenerateMipmap(GL_TEXTURE_2D);
    }
}
