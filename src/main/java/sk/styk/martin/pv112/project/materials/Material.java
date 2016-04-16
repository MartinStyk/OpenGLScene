package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;
import com.jogamp.opengl.GL3;

public class Material {

    private Vec3 ambientColor;
    private Vec3 diffuseColor;
    private Vec3 specularColor;
    private float shininess;

    public Material(Vec3 ambientColor, Vec3 diffuseColor, Vec3 specularColor, float shininess) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.shininess = shininess;
    }

    public Vec3 getAmbientColor() {
        return ambientColor;
    }

    public Vec3 getDiffuseColor() {
        return diffuseColor;
    }

    public Vec3 getSpecularColor() {
        return specularColor;
    }

    public float getShininess() {
        return shininess;
    }

    public void bindUniforms(GL3 gl, int ambientColorLoc, int diffuseColorLoc, int specularColorLoc, int shininessLoc) {
        gl.glUniform3fv(ambientColorLoc, 1, ambientColor.getBuffer());
        gl.glUniform3fv(diffuseColorLoc, 1, diffuseColor.getBuffer());
        gl.glUniform3fv(specularColorLoc, 1, specularColor.getBuffer());
        gl.glUniform1f(shininessLoc, shininess);
    }

}
