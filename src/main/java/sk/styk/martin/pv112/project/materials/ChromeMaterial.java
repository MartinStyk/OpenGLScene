/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Martin Styk
 */
public class ChromeMaterial extends Material{
    
    private static Material instance;
    
    public static Material getInstance(){
        if(instance == null){
            instance = new ChromeMaterial();
        }
        return instance;
    }
    
    private ChromeMaterial(){
        super(new Vec3(0.25f,0.25f,0.25f),
                new Vec3(0.4f,0.4f,0.4f),
                new Vec3(0.774597f,0.774597f,0.774597f),
                76.8f);
    }    
    
}
