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
public class GoldMaterial extends Material {

    private static Material instance;

    private GoldMaterial() {
        super(new Vec3(0.24725f, 0.1995f, 0.0745f),
                new Vec3(0.75164f, 0.60648f, 0.22648f),
                new Vec3(0.628281f, 0.555802f, 0.366065f),
                51f);
    }

    public static Material getInstance() {
        if (instance == null) {
            instance = new GoldMaterial();
        }
        return instance;
    }

}
