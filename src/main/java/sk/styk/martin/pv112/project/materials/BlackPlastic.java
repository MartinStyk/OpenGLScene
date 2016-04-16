package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class BlackPlastic extends Material {
    private static Material instance;

    private BlackPlastic() {
        super(new Vec3(0.0f, 0.0f, 0.0f),
                new Vec3(0.01f, 0.01f, 0.01f),
                new Vec3(0.50f, 0.50f, 0.50f),
                0.25f);
    }

    public static Material getInstance() {
        if (instance == null) {
            instance = new BlackPlastic();
        }
        return instance;
    }
}
