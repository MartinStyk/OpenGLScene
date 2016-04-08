/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.Lights;

import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.MatricesUtils;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.jogamp.opengl.GL3;

/**
 *
 * @author Martin Styk
 */
public class Light {
    
    private Light tempLight;
    private Vec4 position;
    private Vec3 ambientColor;
    private Vec3 specularColor;
    private Vec3 diffuseColor;
    
    
    public Light(){
        this(new Vec4(0.0f,0.0f,0.0f,0.0f), new Vec3(0.1f,0.1f,0.1f), new Vec3(1.0f,1.0f,1.0f), new Vec3(1.0f,1.0f,1.0f));
    }
    
    public Light(Vec4 lightPosition){
        this(lightPosition, new Vec3(0.1f,0.1f,0.1f), new Vec3(1.0f,1.0f,1.0f), new Vec3(1.0f,1.0f,1.0f));
    }
    
    public Light(Vec4 lightPosition,Vec3 ambientColor,Vec3 diffuseColor,Vec3 specularColor){
        this.position = lightPosition;
        this.ambientColor = ambientColor;
        this.specularColor = specularColor;
        this.diffuseColor = diffuseColor;
    }

    public void bindUniforms(GL3 gl, int positionLocation, int ambientColorLocation, int diffuseColorLocation, int specularColorLocation){
        if(tempLight != null){
            Vec4 tempPosition = tempLight.getLightPosition();
            gl.glUniform4f(positionLocation, tempPosition.getX(), tempPosition.getY(), tempPosition.getZ(), tempPosition.getW());
            gl.glUniform3fv(ambientColorLocation, 1, tempLight.getAmbientColor().getBuffer());
            gl.glUniform3fv(diffuseColorLocation, 1, tempLight.getDiffuseColor().getBuffer());
            gl.glUniform3fv(specularColorLocation, 1,tempLight.getSpecularColor().getBuffer());
            return;
        }
        gl.glUniform4f(positionLocation, position.getX(), position.getY(), position.getZ(), position.getW());
        gl.glUniform3fv(ambientColorLocation, 1, ambientColor.getBuffer());
        gl.glUniform3fv(diffuseColorLocation, 1, diffuseColor.getBuffer());
        gl.glUniform3fv(specularColorLocation, 1,specularColor.getBuffer());
    }
    
    public void animateLight(float t){
        position = MatricesUtils.transform(Matrices.rotate(t, new Vec3(0.0f,1.0f,0.0f)), position);
    }
    
    public void switchOff(float percentage){
        Vec3 ambient = new Vec3(ambientColor.getX() * percentage, ambientColor.getY() * percentage, ambientColor.getZ() * percentage);
        Vec3 diffuse = new Vec3(diffuseColor.getX() * percentage, diffuseColor.getY() * percentage, diffuseColor.getZ() * percentage);
        Vec3 specular = new Vec3(specularColor.getX() * percentage, specularColor.getY() * percentage, specularColor.getZ() * percentage);
        tempLight = new Light(position, ambient, diffuse, specular);
    }
    
    public void switchOn(){
        tempLight = null;
    }
    
    /**
     * @return the lightPosition
     */
    public Vec4 getLightPosition() {
        return position;
    }

    /**
     * @param lightPosition the lightPosition to set
     */
    public void setLightPosition(Vec4 lightPosition) {
        this.position = lightPosition;
    }

    /**
     * @return the ambientColor
     */
    public Vec3 getAmbientColor() {
        return ambientColor;
    }

    /**
     * @param ambientColor the ambientColor to set
     */
    public void setAmbientColor(Vec3 ambientColor) {
        this.ambientColor = ambientColor;
    }

    /**
     * @return the specularColor
     */
    public Vec3 getSpecularColor() {
        return specularColor;
    }

    /**
     * @param specularColor the specularColor to set
     */
    public void setSpecularColor(Vec3 specularColor) {
        this.specularColor = specularColor;
    }

    /**
     * @return the diffuseColor
     */
    public Vec3 getDiffuseColor() {
        return diffuseColor;
    }

    /**
     * @param diffuseColor the diffuseColor to set
     */
    public void setDiffuseColor(Vec3 diffuseColor) {
        this.diffuseColor = diffuseColor;
    }    
}
