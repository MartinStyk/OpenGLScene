package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class ShinyRedMaterial extends Material {
    private static Material instance;

    public static Material getInstance() {
        if (instance == null) {
            instance = new ShinyRedMaterial();
        }
        return instance;
    }

    private ShinyRedMaterial() {
        super(new Vec3(0.8f, 0.0f, 0.0f),
                new Vec3(0.8f, 0, 0),
                new Vec3(0.8f, 0, 0),
                0.5f);
    }
}
