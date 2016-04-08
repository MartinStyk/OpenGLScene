package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat3;
import com.hackoeur.jglm.MatricesUtils;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;
import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;

import static com.jogamp.opengl.GL.*;

/**
 * Created by Martin Styk on 07.04.2016.
 */
public class Cube extends Drawable {

    public Cube(Program program) {
        this(program, null);
    }

    public Cube(Program program, Material material) {
        this(program, material, null);
    }

    public Cube(Program program, Material material, ConfigurableTexture texture) {
        super(program, material, texture, "/models/cube.obj");
    }


    @Override
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
            gl.glActiveTexture(GL_TEXTURE0);
            texture.get().bind(gl);
            gl.glUniform1f(program.getUniformLoc(BasicProgram.TEXTURE_COORDINATES_MULTIPLIER), texture.getCoordinatesMultiplier());
            gl.glUniform1f(program.getUniformLoc(BasicProgram.TEXTURE_COORDINATES_OFFSET), texture.getCoordinatesOffset());
            gl.glUniform1i(program.getUniformLoc(BasicProgram.TEXTURE),0);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, texture.getMinFilter());
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, texture.getMagFilter());
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, texture.getWrapS());
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, texture.getWrapT());
            gl.glGenerateMipmap(GL_TEXTURE_2D);
        }

        geometry.draw(gl);

        //not texture
        gl.glUniform1i(program.getUniformLoc(BasicProgram.IS_TEXTURE), 0);

        gl.glUseProgram(0);
    }

}
