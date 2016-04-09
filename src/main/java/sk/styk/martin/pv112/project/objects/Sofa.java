package sk.styk.martin.pv112.project.objects;

import sk.styk.martin.pv112.project.materials.Material;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.ConfigurableTexture;

/**
 * Created by Martin Styk on 07.04.2016.
 */
public class Sofa extends Drawable {

    public Sofa(Program program) {
        this(program, null);
    }

    public Sofa(Program program, Material material) {
        this(program, material, null);
    }

    public Sofa(Program program, Material material, ConfigurableTexture texture) {
        super(program, material, texture, "/models/sofa.obj");
    }
}
