package sk.styk.martin.pv112.project.lights;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.Scene;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class AttenuationLight extends Light {

    private float attenuationConst = 0.5f;
    private float attenuationLinear = 0.1f;
    private float attenuationQuadratic = 0.05f;

    public AttenuationLight() {
        super();
    }

    public AttenuationLight(Vec4 lightPosition) {
        super(lightPosition);
    }

    public AttenuationLight(Vec4 lightPosition, Vec3 ambientColor, Vec3 diffuseColor, Vec3 specularColor) {
        super(lightPosition, ambientColor, diffuseColor, specularColor);
    }

    public AttenuationLight(Vec4 lightPosition, float attenuationConst, float attenuationLinear, float attenuationQuadratic) {
        super(lightPosition);
        this.attenuationConst = attenuationConst;
        this.attenuationLinear = attenuationLinear;
        this.attenuationQuadratic = attenuationQuadratic;
    }

    public AttenuationLight(Vec4 lightPosition, Vec3 ambientColor, Vec3 diffuseColor, Vec3 specularColor, float attenuationConst, float attenuationLinear, float attenuationQuadratic) {
        super(lightPosition, ambientColor, diffuseColor, specularColor);
        this.attenuationConst = attenuationConst;
        this.attenuationLinear = attenuationLinear;
        this.attenuationQuadratic = attenuationQuadratic;
    }

    public void bindUniforms(GL3 gl, int positionLocation, int ambientColorLocation, int diffuseColorLocation,
                             int specularColorLocation, int attenuationConstLocation, int attenuationLinearLocation,
                             int attenuationQuadraticLocation) {
        super.bindUniforms(gl, positionLocation, ambientColorLocation, diffuseColorLocation, specularColorLocation);

        gl.glUniform1f(attenuationConstLocation, attenuationConst);
        gl.glUniform1f(attenuationLinearLocation, attenuationLinear * Scene.lightPower );
        gl.glUniform1f(attenuationQuadraticLocation, attenuationQuadratic * Scene.lightPower );
    }

    @Override
    public void animateLight(float t){

    }
}
