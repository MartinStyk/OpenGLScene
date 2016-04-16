package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat3;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.MatricesUtils;
import com.hackoeur.jglm.Vec3;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.materials.BlackPlastic;
import sk.styk.martin.pv112.project.materials.ShinyRedMaterial;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;
import sk.styk.martin.pv112.project.textures.RadioTexture;
import sk.styk.martin.pv112.project.tooling.SoundRunnable;
import sk.styk.martin.pv112.project.tooling.VertexUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.jogamp.opengl.GL.*;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class Radio extends Cube implements Interactive {

    private static Random random = new Random(System.currentTimeMillis());
    private final float MAX_DISTANCE = 7f;
    private Camera camera;
    private Vec3 position;
    private boolean isInteracting = false;
    private Mat4 viewProjection;
    private List<SoundRunnable> soundRunnableList;
    private Thread currentThread;
    private SoundRunnable currentSoundRunnable;

    public Radio(Program program, Vec3 position, Camera camera) {
        super(program, BlackPlastic.getInstance(), new RadioTexture(program.getGL()));
        if (camera == null)
            throw new IllegalArgumentException("camera null !");
        this.camera = camera;
        this.position = position;

        soundRunnableList = Arrays.asList(new SoundRunnable("/sounds/ukulele.wav"),
                new SoundRunnable("/sounds/happiness.wav"),
                new SoundRunnable("/sounds/moose.wav"));

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
        if (isInteracting) {
            currentSoundRunnable.stopPlaying();
            isInteracting = false;
        } else {
            currentSoundRunnable = soundRunnableList.get(random.nextInt(soundRunnableList.size()));
            currentThread = new Thread(currentSoundRunnable);
            currentThread.start();
            isInteracting = true;
        }
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

        RadioTexture radioTexture = (RadioTexture) getTexture();

        bindTexture(radioTexture, 0, gl);
        geometry.drawTriangles(gl, 0, 6);
        bindTexture(radioTexture, 1, gl);
        geometry.drawTriangles(gl, 6, 6);
        bindTexture(radioTexture, 1, gl);
        geometry.drawTriangles(gl, 12, 6);
        bindTexture(radioTexture, 1, gl);
        geometry.drawTriangles(gl, 18, 6);
        bindTexture(radioTexture, 1, gl);
        geometry.drawTriangles(gl, 24, 6);
        bindTexture(radioTexture, 1, gl);
        geometry.drawTriangles(gl, 30, 6);

        //not texture
        gl.glUniform1i(program.getUniformLoc(BasicProgram.IS_TEXTURE), 0);

        if (isInRange()) {

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

    private void bindTexture(RadioTexture radioTexture, int i, GL3 gl) {
        ConfigurableTexture t = radioTexture.getPartial(i);
        t.get().bind(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, t.getMinFilter());
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, t.getMagFilter());
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, t.getWrapS());
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, t.getWrapT());
        gl.glGenerateMipmap(GL_TEXTURE_2D);
    }

    public void setViewProjection(Mat4 viewProjection) {
        this.viewProjection = viewProjection;
    }

}
