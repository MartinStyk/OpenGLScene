package sk.styk.martin.pv112.project.tooling;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 16.04.2016.
 */
public class VertexUtils {

    public static float getDistanceAbsolute(Vec3 vec1, Vec3 vec2) {
        return Math.abs(getDistance(vec1, vec2));
    }

    public static float getDistance(Vec3 vec1, Vec3 vec2) {
        if (vec1 == null || vec2 == null) {
            throw new IllegalArgumentException("vector is null");
        }
        return getDistance(vec1.getX(), vec1.getY(), vec1.getZ(), vec2.getX(), vec2.getY(), vec2.getZ());
    }

    public static float getDistance(float x1, float y1, float z1, float x2, float y2, float z2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float dz = z1 - z2;

        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
