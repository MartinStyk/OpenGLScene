/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.programs;

import com.hackoeur.jglm.Vec3;
import com.jogamp.opengl.GL3;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.lights.Light;
import sk.styk.martin.pv112.project.tooling.LoadUtils;

/**
 *
 * @author Martin Styk
 */
public abstract class Program {
    
    protected GL3 gl;
    protected UniformManager uniformManager;
        
    protected int id;
    
    protected Program(GL3 gl, String vertexShaderPath, String fragmentShaderPath){
        this.gl = gl;
        id = LoadUtils.loadProgram(gl,vertexShaderPath ,fragmentShaderPath);
        uniformManager = new UniformManager(this);
    }
    
    public int getUniformLoc(String uniformName){
        return uniformManager.getUniformLocation(uniformName);
    }
    
    protected void addUniformLocation(String... uniformName){
        for(String actualName : uniformName){
            uniformManager.addUniformLocation(actualName);   
        }
    }
    
    public void use(){
        gl.glUseProgram(id);
    }
   
    public int getID(){
        return id;
    }
    public GL3 getGL(){
        return gl;
    }

    public abstract void bindLight(int i, Light light);
    
    public abstract void bindCamera(Camera camera);

    public abstract void setGlobalAmbientLight(Vec3 color);
}
