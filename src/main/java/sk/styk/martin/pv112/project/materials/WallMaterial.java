package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class WallMaterial extends Material {
    private static Material instance;

    private WallMaterial() {
        super(new Vec3(0.7f, 0.7f, 0.7f),
                new Vec3(1.0f, 1.0f, 1.0f),
                new Vec3(0.10f, 0.10f, 0.10f),
                1f);
    }

    public static Material getInstance() {
        if (instance == null) {
            instance = new WallMaterial();
        }
        return instance;
    }
}
