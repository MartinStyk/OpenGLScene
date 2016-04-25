package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.*;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.materials.PewterMaterial;
import sk.styk.martin.pv112.project.materials.ShinyRedMaterial;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.GlobeTexture;
import sk.styk.martin.pv112.project.tooling.SceneConstants;
import sk.styk.martin.pv112.project.tooling.VertexUtils;


import static com.jogamp.opengl.GL.*;

/**
 * Created by Martin Styk on 25.04.2016.
 */
public class Earth extends Ball implements Interactive {

    private final float MAX_DISTANCE = 8f;
    private Camera camera;
    private Vec3 position;
    private boolean isInteracting = false;
    private Mat4 viewProjection;

    public Earth(Program program, Vec3 position, Camera camera) {
        super(program, PewterMaterial.getInstance(), new GlobeTexture(program.getGL()));
        if (camera == null)
            throw new IllegalArgumentException("camera null !");
        this.camera = camera;
        this.position = position;
        isInteracting = true;
    }

    @Override
    public boolean isInRange() {
        return VertexUtils.getDistanceAbsolute(position, camera.getEyePosition()) < MAX_DISTANCE;
    }

    @Override
    public boolean isInteracting() {
        return isInteracting;
    }

    @Override
    public void interact() {
        isInteracting = !isInteracting;
    }

    public void draw() {
        if(isInteracting){
            model = model.multiply(Matrices.rotate(0.02f, SceneConstants.Y_AXIS));
        }
        super.draw();
        GL3 gl = program.getGL();

        if (isInRange()) {
            gl.glUseProgram(program.getID());
            Mat4 modelActive = model.multiply(MatricesUtils.scale(1.01f, 1.01f, 1.01f));
            Mat3 nActive = MatricesUtils.inverse(MatricesUtils.getMat3(modelActive).transpose());
            gl.glUniformMatrix3fv(program.getUniformLoc(BasicProgram.N), 1, false, nActive.getBuffer());
            gl.glUniformMatrix4fv(program.getUniformLoc(BasicProgram.MODEL), 1, false, modelActive.getBuffer());
            gl.glUniformMatrix4fv(program.getUniformLoc(BasicProgram.MVP), 1, false, viewProjection.multiply(modelActive).getBuffer());

            ShinyRedMaterial.getInstance().bindUniforms(gl,
                    program.getUniformLoc(BasicProgram.MATERIAL_AMBIENT_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_DIFFUSE_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_SPECULAR_COLOR),
                    program.getUniformLoc(BasicProgram.MATERIAL_SHININESS)
            );


            gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            gl.glUniform1f(program.getUniformLoc(BasicProgram.ALFA), 0.3f);

            geometry.draw(gl);

            gl.glUniform1f(program.getUniformLoc(BasicProgram.ALFA), 1f);
        }
        gl.glUseProgram(0);
    }

    @Override
    public void setViewProjection(Mat4 viewProjection) {
        this.viewProjection = viewProjection;
    }
}
