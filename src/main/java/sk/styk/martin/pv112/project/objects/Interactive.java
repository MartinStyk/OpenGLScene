package sk.styk.martin.pv112.project.objects;

import com.hackoeur.jglm.Mat4;

/**
 * Created by Martin Styk on 16.04.2016.
 */
public interface Interactive {

    /**
     * Checks whether object is in range of camera requred to perform action
     *
     * @return true - camera is in range of object otherwise false
     */
    boolean isInRange();

    /**
     * @return true if object is already in interaction
     */
    boolean isInteracting();

    /**
     * Perform interaction
     */
    void interact();

    /**
     * set viewProjection matrix
     * @param viewProjection
     */
    void setViewProjection(Mat4 viewProjection);
}
