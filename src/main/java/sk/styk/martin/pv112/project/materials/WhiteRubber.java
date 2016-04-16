package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class WhiteRubber extends Material {
    private static Material instance;

    private WhiteRubber() {
        super(new Vec3(0.05f, 0.05f, 0.05f),
                new Vec3(0.5f, 0.5f, 0.5f),
                new Vec3(0.70f, 0.70f, 0.70f),
                0.78215f);
    }

    public static Material getInstance() {
        if (instance == null) {
            instance = new WhiteRubber();
        }
        return instance;
    }
}
