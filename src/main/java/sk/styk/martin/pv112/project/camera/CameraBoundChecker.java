package sk.styk.martin.pv112.project.camera;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 16.04.2016.
 */
public class CameraBoundChecker {

    private float xMax;
    private float xMin;
    private float yMax;
    private float yMin;
    private float zMax;
    private float zMin;

    public CameraBoundChecker(float xMax, float yMax, float zMax, float xMin, float yMin, float zMin) {
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
        this.zMax = zMax;
        this.zMin = zMin;
    }

    protected boolean checkBounds(Vec3 position) {
        return !(xMax < position.getX() || position.getX() < xMin
                || yMax < position.getY() || position.getY() < yMin
                || zMax < position.getZ() || position.getZ() < zMin);
    }
}
