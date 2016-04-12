/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.programs;

import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.lights.Light;

/**
 *
 * @author Martin Styk
 */
@Deprecated
public abstract class BasicTextureProgram extends Program {

    private static final String VERTEX_SHADER = "/shaders/texture.vs.glsl";
    private static final String FRAGMENT_SHADER = "/shaders/texture.fs.glsl";

    private static final int NUMBER_LIGHTS = 2;

    public static final String MODEL = "model";
    public static final String MVP = "MVP";
    public static final String N = "N";
    public static final String COLOR = "color";

    public static final String MATERIAL_AMBIENT_COLOR = "materialAmbientColor";
    public static final String MATERIAL_DIFFUSE_COLOR = "materialDiffuseColor";
    public static final String MATERIAL_SPECULAR_COLOR = "materialSpecularColor";
    public static final String MATERIAL_SHININESS = "materialShininess";

    public static final String LIGHT1_POSITION = "light1Position";
    public static final String LIGHT1_AMBIENT_COLOR = "light1AmbientColor";
    public static final String LIGHT1_DIFFUSE_COLOR = "light1DiffuseColor";
    public static final String LIGHT1_SPECULAR_COLOR = "light1SpecularColor";

    public static final String LIGHT2_POSITION = "light2Position";
    public static final String LIGHT2_AMBIENT_COLOR = "light2AmbientColor";
    public static final String LIGHT2_DIFFUSE_COLOR = "light2DiffuseColor";
    public static final String LIGHT2_SPECULAR_COLOR = "light2SpecularColor";

    public static final String EYE_POSITION = "eyePosition";

    public static final String TEXTURE = "texture";

    @Deprecated
    public BasicTextureProgram(GL3 gl) {
        super(gl, VERTEX_SHADER, FRAGMENT_SHADER);
        addUniformLocation(MODEL,
                MVP,
                N,
                COLOR,
                MATERIAL_AMBIENT_COLOR,
                MATERIAL_DIFFUSE_COLOR,
                MATERIAL_SHININESS,
                MATERIAL_SPECULAR_COLOR,
                LIGHT1_POSITION,
                LIGHT1_AMBIENT_COLOR,
                LIGHT1_DIFFUSE_COLOR,
                LIGHT1_SPECULAR_COLOR,
                LIGHT2_POSITION,
                LIGHT2_AMBIENT_COLOR,
                LIGHT2_DIFFUSE_COLOR,
                LIGHT2_SPECULAR_COLOR,
                EYE_POSITION,
                TEXTURE);
    }

    @Override
    public void bindLight(int i, Light light) {
        int position;
        int ambient;
        int diffuse;
        int specular;

        switch(i){
            case 1:
                position = uniformManager.getUniformLocation(LIGHT1_POSITION);
                ambient = uniformManager.getUniformLocation(LIGHT1_AMBIENT_COLOR);
                diffuse = uniformManager.getUniformLocation(LIGHT1_DIFFUSE_COLOR);
                specular = uniformManager.getUniformLocation(LIGHT1_SPECULAR_COLOR);
                break;
            case 2:
                position = uniformManager.getUniformLocation(LIGHT2_POSITION);
                ambient = uniformManager.getUniformLocation(LIGHT2_AMBIENT_COLOR);
                diffuse = uniformManager.getUniformLocation(LIGHT2_DIFFUSE_COLOR);
                specular = uniformManager.getUniformLocation(LIGHT2_SPECULAR_COLOR);
                break;
            default:throw new IllegalArgumentException("number of light is incorrect");
        }

        light.bindUniforms(gl,position, ambient, diffuse, specular);
    }

    @Override
    public void bindCamera(Camera camera){
        gl.glUniform3fv(uniformManager.getUniformLocation(EYE_POSITION), 1, camera.getEyePosition().getBuffer());
    }

}
