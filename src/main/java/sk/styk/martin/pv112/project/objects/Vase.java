package sk.styk.martin.pv112.project.objects;

import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;

/**
 * Created by Martin Styk on 12.04.2016.
 */
public class Vase extends Drawable {

    public Vase(Program program) {
        this(program, null);
    }

    public Vase(Program program, Material material) {
        this(program, material, null);
    }

    public Vase(Program program, Material material, ConfigurableTexture texture) {
        super(program, material, texture, "/models/vase.obj");
    }
}
