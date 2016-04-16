package sk.styk.martin.pv112.project.camera;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 09.04.2016.
 */
public interface Camera {

    /**
     * @return current point in which camera is located
     */
    Vec3 getEyePosition();

    /**
     * @return current direction of camera orientation - e.g. eye position + orientation
     */
    Vec3 getEyeDirection();

    /**
     * @param x data from mouse x axis
     * @param y data from mouse y axis
     */
    void onMouseMove(int x, int y);

    /**
     *
     * @param x data from mouse x axis
     * @param y data from mouse y axis
     * @param mouseButton data from mouse button affected
     * @param pressed data from mouse button pressed
     */
    void onMousePress(int x, int y, int mouseButton, boolean pressed);

    /**
     * Should implement camera position reaction according to pressed key (movement of camera)
     * @param c pressed character
     */
    void onKeyPressed(char c);
}
