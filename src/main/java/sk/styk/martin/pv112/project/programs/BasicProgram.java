/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.programs;

import com.hackoeur.jglm.Vec3;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.lights.AttenuationLight;
import sk.styk.martin.pv112.project.lights.Light;

/**
 * Program for textured and not textred indoor objects
 * Supports only point lights with attenuation
 *
 * @author Martin Styk
 */
public class BasicProgram extends Program {

    public static final String MODEL = "model";
    public static final String MVP = "MVP";
    public static final String N = "N";
    public static final String COLOR = "color";
    public static final String MATERIAL_AMBIENT_COLOR = "materialAmbientColor";
    public static final String MATERIAL_DIFFUSE_COLOR = "materialDiffuseColor";
    public static final String MATERIAL_SPECULAR_COLOR = "materialSpecularColor";
    public static final String MATERIAL_SHININESS = "materialShininess";
    public static final String LIGHT_GLOBAL_AMBIENT_COLOR = "lightGlobalAmbientColor";
    public static final String ALFA = "alfa";
    public static final String LIGHT_POSITION_BASE = "lightPosition_";
    public static final String LIGHT_AMBIENT_COLOR_BASE = "lightAmbientColor_";
    public static final String LIGHT_DIFFUSE_COLOR_BASE = "lightDiffuseColor_";
    public static final String LIGHT_SPECULAR_COLOR_BASE = "lightSpecularColor_";
    public static final String LIGHT_ATTENUATION_1_BASE = "lightAttenuationConst1_";
    public static final String LIGHT_ATTENUATION_2_BASE = "lightAttenuationConst2_";
    public static final String LIGHT_ATTENUATION_3_BASE = "lightAttenuationConst3_";
    public static final String EYE_POSITION = "eyePosition";
    public static final String TEXTURE = "texture";
    public static final String IS_TEXTURE = "isTexture";

    public static final String TEXTURE_COORDINATES_MULTIPLIER = "texCoordMultiplier";
    public static final String TEXTURE_COORDINATES_OFFSET = "texCoordOffset";
    private static final String VERTEX_SHADER = "/shaders/model.vs.glsl";
    private static final String FRAGMENT_SHADER = "/shaders/model.fs.glsl";
    private static final int NUMBER_LIGHTS = 10;

    public BasicProgram(GL3 gl) {
        super(gl, VERTEX_SHADER, FRAGMENT_SHADER);
        addUniformLocation(MODEL,
                MVP,
                N,
                COLOR,
                MATERIAL_AMBIENT_COLOR,
                MATERIAL_DIFFUSE_COLOR,
                MATERIAL_SHININESS,
                MATERIAL_SPECULAR_COLOR,
                EYE_POSITION,
                TEXTURE,
                IS_TEXTURE,
                TEXTURE_COORDINATES_MULTIPLIER,
                TEXTURE_COORDINATES_OFFSET,
                ALFA,
                LIGHT_GLOBAL_AMBIENT_COLOR);
        for (int i = 1; i <= NUMBER_LIGHTS; i++) {
            addUniformLocation(
                    LIGHT_POSITION_BASE + i,
                    LIGHT_AMBIENT_COLOR_BASE + i,
                    LIGHT_DIFFUSE_COLOR_BASE + i,
                    LIGHT_SPECULAR_COLOR_BASE + i,
                    LIGHT_ATTENUATION_1_BASE + i,
                    LIGHT_ATTENUATION_2_BASE + i,
                    LIGHT_ATTENUATION_3_BASE + i
            );
        }
    }

    @Override
    public void bindLight(int i, Light light) {

        int position = uniformManager.getUniformLocation(LIGHT_POSITION_BASE + i);
        int ambient = uniformManager.getUniformLocation(LIGHT_AMBIENT_COLOR_BASE + i);
        int diffuse = uniformManager.getUniformLocation(LIGHT_DIFFUSE_COLOR_BASE + i);
        int specular = uniformManager.getUniformLocation(LIGHT_SPECULAR_COLOR_BASE + i);
        int att1 = uniformManager.getUniformLocation(LIGHT_ATTENUATION_1_BASE + i);
        int att2 = uniformManager.getUniformLocation(LIGHT_ATTENUATION_2_BASE + i);
        int att3 = uniformManager.getUniformLocation(LIGHT_ATTENUATION_3_BASE + i);

        if (light instanceof AttenuationLight) {
            ((AttenuationLight) light).bindUniforms(gl, position, ambient, diffuse, specular, att1, att2, att3);
        } else {
            light.bindUniforms(gl, position, ambient, diffuse, specular);
        }

    }

    @Override
    public void bindCamera(Camera camera) {
        gl.glUniform3fv(uniformManager.getUniformLocation(EYE_POSITION), 1, camera.getEyePosition().getBuffer());
    }

    public void setGlobalAmbientLight(Vec3 color) {
        gl.glUniform3fv(getUniformLoc(LIGHT_GLOBAL_AMBIENT_COLOR), 1, color.getBuffer());
    }

    public void setAlpha(float lvl) {
        gl.glUniform1f(getUniformLoc(ALFA), lvl);
    }

}
