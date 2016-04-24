package sk.styk.martin.pv112.project.tooling;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 24.04.2016.
 */
public class SceneConstants {

    //AXIS
    public static final Vec3 X_AXIS = new Vec3 (1,0,0);
    public static final Vec3 Y_AXIS = new Vec3 (0,1,0);
    public static final Vec3 Z_AXIS = new Vec3 (0,0,1);

    //ROOM BOUNDS
    public static final int CEILING_POS = 8;
    public static final int FLOOR_POS = -8;
    public static final float CEILING_LIGHT_HEIGHT = CEILING_POS - 0.2f;
    public static final float LIGHT_DIST_FROM_CENTER = 7.0f;
}
