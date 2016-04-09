/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.programs;

import com.hackoeur.jglm.Vec3;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.Camera;
import sk.styk.martin.pv112.project.CenterPointCamera;
import sk.styk.martin.pv112.project.Lights.AttenuationLight;
import sk.styk.martin.pv112.project.Lights.Light;

/**
 * Program for textured and not textred indoor objects
 * Supports only point lights with attenuation
 *
 * @author Martin Styk
 */
public class BasicProgram extends Program {

    private static final String VERTEX_SHADER = "/shaders/model.vs.glsl";
    private static final String FRAGMENT_SHADER = "/shaders/model.fs.glsl";

    private static final int NUMBER_LIGHTS = 10;

    public static final String MODEL = "model";
    public static final String MVP = "MVP";
    public static final String N = "N";
    public static final String COLOR = "color";

    public static final String MATERIAL_AMBIENT_COLOR = "materialAmbientColor";
    public static final String MATERIAL_DIFFUSE_COLOR = "materialDiffuseColor";
    public static final String MATERIAL_SPECULAR_COLOR = "materialSpecularColor";
    public static final String MATERIAL_SHININESS = "materialShininess";

    public static final String LIGHT_GLOBAL_AMBIENT_COLOR = "lightGlobalAmbientColor";

    public static final String LIGHT_POSITION_BASE = "lightPosition_";
    public static final String LIGHT_AMBIENT_COLOR_BASE = "lightAmbientColor_";
    public static final String LIGHT_DIFFUSE_COLOR_BASE = "lightDiffuseColor_";
    public static final String LIGHT_SPECULAR_COLOR_BASE = "lightSpecularColor_";
    public static final String LIGHT_ATTENUATION_1_BASE = "lightAttenuationConst1_";
    public static final String LIGHT_ATTENUATION_2_BASE = "lightAttenuationConst2_";
    public static final String LIGHT_ATTENUATION_3_BASE = "lightAttenuationConst3_";

//    public static final String LIGHT1_POSITION = "light1Position";
//    public static final String LIGHT1_AMBIENT_COLOR = "light1AmbientColor";
//    public static final String LIGHT1_DIFFUSE_COLOR = "light1DiffuseColor";
//    public static final String LIGHT1_SPECULAR_COLOR = "light1SpecularColor";
//    public static final String LIGHT1_ATTENUATION_1 = "light1AttenuationConst1";
//    public static final String LIGHT1_ATTENUATION_2 = "light1AttenuationConst2";
//    public static final String LIGHT1_ATTENUATION_3 = "light1AttenuationConst3";
//
//    public static final String LIGHT2_POSITION = "light2Position";
//    public static final String LIGHT2_AMBIENT_COLOR = "light2AmbientColor";
//    public static final String LIGHT2_DIFFUSE_COLOR = "light2DiffuseColor";
//    public static final String LIGHT2_SPECULAR_COLOR = "light2SpecularColor";
//    public static final String LIGHT2_ATTENUATION_1 = "light2AttenuationConst1";
//    public static final String LIGHT2_ATTENUATION_2 = "light2AttenuationConst2";
//    public static final String LIGHT2_ATTENUATION_3 = "light2AttenuationConst3";
//
//    public static final String LIGHT3_POSITION = "light3Position";
//    public static final String LIGHT3_AMBIENT_COLOR = "light3AmbientColor";
//    public static final String LIGHT3_DIFFUSE_COLOR = "light3DiffuseColor";
//    public static final String LIGHT3_SPECULAR_COLOR = "light3SpecularColor";
//    public static final String LIGHT3_ATTENUATION_1 = "light3AttenuationConst1";
//    public static final String LIGHT3_ATTENUATION_2 = "light3AttenuationConst2";
//    public static final String LIGHT3_ATTENUATION_3 = "light3AttenuationConst3";
//
//    public static final String LIGHT4_POSITION = "light4Position";
//    public static final String LIGHT4_AMBIENT_COLOR = "light4AmbientColor";
//    public static final String LIGHT4_DIFFUSE_COLOR = "light4DiffuseColor";
//    public static final String LIGHT4_SPECULAR_COLOR = "light4SpecularColor";
//    public static final String LIGHT4_ATTENUATION_1 = "light4AttenuationConst1";
//    public static final String LIGHT4_ATTENUATION_2 = "light4AttenuationConst2";
//    public static final String LIGHT4_ATTENUATION_3 = "light4AttenuationConst3";
//
//    public static final String LIGHT5_POSITION = "light5Position";
//    public static final String LIGHT5_AMBIENT_COLOR = "light5AmbientColor";
//    public static final String LIGHT5_DIFFUSE_COLOR = "light5DiffuseColor";
//    public static final String LIGHT5_SPECULAR_COLOR = "light5SpecularColor";
//    public static final String LIGHT5_ATTENUATION_1 = "light5AttenuationConst1";
//    public static final String LIGHT5_ATTENUATION_2 = "light5AttenuationConst2";
//    public static final String LIGHT5_ATTENUATION_3 = "light5AttenuationConst3";
//
//    public static final String LIGHT6_POSITION = "light6Position";
//    public static final String LIGHT6_AMBIENT_COLOR = "light6AmbientColor";
//    public static final String LIGHT6_DIFFUSE_COLOR = "light6DiffuseColor";
//    public static final String LIGHT6_SPECULAR_COLOR = "light6SpecularColor";
//    public static final String LIGHT6_ATTENUATION_1 = "light6AttenuationConst1";
//    public static final String LIGHT6_ATTENUATION_2 = "light6AttenuationConst2";
//    public static final String LIGHT6_ATTENUATION_3 = "light6AttenuationConst3";
//
//    public static final String LIGHT7_POSITION = "light7Position";
//    public static final String LIGHT7_AMBIENT_COLOR = "light7AmbientColor";
//    public static final String LIGHT7_DIFFUSE_COLOR = "light7DiffuseColor";
//    public static final String LIGHT7_SPECULAR_COLOR = "light7SpecularColor";
//    public static final String LIGHT7_ATTENUATION_1 = "light7AttenuationConst1";
//    public static final String LIGHT7_ATTENUATION_2 = "light7AttenuationConst2";
//    public static final String LIGHT7_ATTENUATION_3 = "light7AttenuationConst3";
//
//    public static final String LIGHT8_POSITION = "light8Position";
//    public static final String LIGHT8_AMBIENT_COLOR = "light8AmbientColor";
//    public static final String LIGHT8_DIFFUSE_COLOR = "light8DiffuseColor";
//    public static final String LIGHT8_SPECULAR_COLOR = "light8SpecularColor";
//    public static final String LIGHT8_ATTENUATION_1 = "light8AttenuationConst1";
//    public static final String LIGHT8_ATTENUATION_2 = "light8AttenuationConst2";
//    public static final String LIGHT8_ATTENUATION_3 = "light8AttenuationConst3";
//
//    public static final String LIGHT9_POSITION = "light9Position";
//    public static final String LIGHT9_AMBIENT_COLOR = "light9AmbientColor";
//    public static final String LIGHT9_DIFFUSE_COLOR = "light9DiffuseColor";
//    public static final String LIGHT9_SPECULAR_COLOR = "light9SpecularColor";
//    public static final String LIGHT9_ATTENUATION_1 = "light9AttenuationConst1";
//    public static final String LIGHT9_ATTENUATION_2 = "light9AttenuationConst2";
//    public static final String LIGHT9_ATTENUATION_3 = "light9AttenuationConst3";
//
//    public static final String LIGHT10_POSITION = "light10Position";
//    public static final String LIGHT10_AMBIENT_COLOR = "light10AmbientColor";
//    public static final String LIGHT10_DIFFUSE_COLOR = "light10DiffuseColor";
//    public static final String LIGHT10_SPECULAR_COLOR = "light10SpecularColor";
//    public static final String LIGHT10_ATTENUATION_1 = "light10AttenuationConst1";
//    public static final String LIGHT10_ATTENUATION_2 = "light10AttenuationConst2";
//    public static final String LIGHT10_ATTENUATION_3 = "light10AttenuationConst3";


    public static final String EYE_POSITION = "eyePosition";

    public static final String TEXTURE = "texture";
    public static final String IS_TEXTURE = "isTexture";

    public static final String TEXTURE_COORDINATES_MULTIPLIER = "texCoordMultiplier";
    public static final String TEXTURE_COORDINATES_OFFSET = "texCoordOffset";

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
//                LIGHT1_POSITION,
//                LIGHT1_AMBIENT_COLOR,
//                LIGHT1_DIFFUSE_COLOR,
//                LIGHT1_SPECULAR_COLOR,
//                LIGHT1_ATTENUATION_1,
//                LIGHT1_ATTENUATION_2,
//                LIGHT1_ATTENUATION_3,
//                LIGHT2_POSITION,
//                LIGHT2_AMBIENT_COLOR,
//                LIGHT2_DIFFUSE_COLOR,
//                LIGHT2_SPECULAR_COLOR,
//                LIGHT2_ATTENUATION_1,
//                LIGHT2_ATTENUATION_2,
//                LIGHT2_ATTENUATION_3,
//                LIGHT3_POSITION,
//                LIGHT3_AMBIENT_COLOR,
//                LIGHT3_DIFFUSE_COLOR,
//                LIGHT3_SPECULAR_COLOR,
//                LIGHT3_ATTENUATION_1,
//                LIGHT3_ATTENUATION_2,
//                LIGHT3_ATTENUATION_3,
//                LIGHT4_POSITION,
//                LIGHT4_AMBIENT_COLOR,
//                LIGHT4_DIFFUSE_COLOR,
//                LIGHT4_SPECULAR_COLOR,
//                LIGHT4_ATTENUATION_1,
//                LIGHT4_ATTENUATION_2,
//                LIGHT4_ATTENUATION_3,
//                LIGHT5_POSITION,
//                LIGHT5_AMBIENT_COLOR,
//                LIGHT5_DIFFUSE_COLOR,
//                LIGHT5_SPECULAR_COLOR,
//                LIGHT5_ATTENUATION_1,
//                LIGHT5_ATTENUATION_2,
//                LIGHT5_ATTENUATION_3,
//                LIGHT6_POSITION,
//                LIGHT6_AMBIENT_COLOR,
//                LIGHT6_DIFFUSE_COLOR,
//                LIGHT6_SPECULAR_COLOR,
//                LIGHT6_ATTENUATION_1,
//                LIGHT6_ATTENUATION_2,
//                LIGHT6_ATTENUATION_3,
//                LIGHT7_POSITION,
//                LIGHT7_AMBIENT_COLOR,
//                LIGHT7_DIFFUSE_COLOR,
//                LIGHT7_SPECULAR_COLOR,
//                LIGHT7_ATTENUATION_1,
//                LIGHT7_ATTENUATION_2,
//                LIGHT7_ATTENUATION_3,
//                LIGHT8_POSITION,
//                LIGHT8_AMBIENT_COLOR,
//                LIGHT8_DIFFUSE_COLOR,
//                LIGHT8_SPECULAR_COLOR,
//                LIGHT8_ATTENUATION_1,
//                LIGHT8_ATTENUATION_2,
//                LIGHT8_ATTENUATION_3,
//                LIGHT9_POSITION,
//                LIGHT9_AMBIENT_COLOR,
//                LIGHT9_DIFFUSE_COLOR,
//                LIGHT9_SPECULAR_COLOR,
//                LIGHT9_ATTENUATION_1,
//                LIGHT9_ATTENUATION_2,
//                LIGHT9_ATTENUATION_3,
//                LIGHT10_POSITION,
//                LIGHT10_AMBIENT_COLOR,
//                LIGHT10_DIFFUSE_COLOR,
//                LIGHT10_SPECULAR_COLOR,
//                LIGHT10_ATTENUATION_1,
//                LIGHT10_ATTENUATION_2,
//                LIGHT10_ATTENUATION_3,
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

}
