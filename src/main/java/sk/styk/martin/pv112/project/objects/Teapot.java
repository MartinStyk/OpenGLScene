/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.objects;

import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;

/**
 * @author Martin Styk
 */
public class Teapot extends Drawable {

    public Teapot(Program program) {
        this(program, null, null);
    }

    public Teapot(Program program, Material material) {
        super(program, material, null, "/models/teapot-high.obj");
    }

    public Teapot(Program program, Material material, ConfigurableTexture texture) {
        super(program, material, texture, "/models/teapot-high.obj");
    }
}
