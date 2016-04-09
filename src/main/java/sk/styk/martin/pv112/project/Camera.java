package sk.styk.martin.pv112.project;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 09.04.2016.
 */
public interface Camera {

    Vec3 getEyePosition();
    Vec3 getCenterPosition();
    void onMouseMove(int x, int y,int mouseButton);
    void onKeyPressed(char c);
}
