/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * @author Martin Styk
 */
public class PewterMaterial extends Material {

    private static Material instance;

    private PewterMaterial() {
        super(new Vec3(0.105882f, 0.058824f, 0.113725f),
                new Vec3(0.427451f, 0.470588f, 0.541176f),
                new Vec3(0.333333f, 0.333333f, 0.521569f),
                9.84615f);
    }

    public static Material getInstance() {
        if (instance == null) {
            instance = new PewterMaterial();
        }
        return instance;
    }

}
