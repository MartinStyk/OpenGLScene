package sk.styk.martin.pv112.project.camera;

import com.hackoeur.jglm.Vec3;

import java.awt.event.MouseEvent;


/**
 * Created by Martin Styk on 09.04.2016.
 */
public class MyCamera implements Camera {

    private static final float MIN_ELEVALITON = -1.5f;
    ///		- Maximum elevation in radians
    private static final float MAX_ELEVATION = 1.5f;

    // position
    Vec3 position = new Vec3(0, 0, -5);
    Vec3 direction = new Vec3(0, 0, 0);
    Vec3 rightDirection = new Vec3(0, 1, 0);
    // horizontal angle : toward -Z
    float horizontalAngle = 3.14f;
    // vertical angle : 0, look at the horizon
    float verticalAngle = 0.0f;

    float speed = 0.7f; //
    float mouseSpeed = 0.005f;

    int xPosition = 0, yPosition = 0;

    private boolean rotating = false;

    public void onMousePress(int x, int y, int mouseButton, boolean pressed) {

        if (mouseButton != MouseEvent.BUTTON1) {
            return;
        }
        if (pressed) {
            xPosition = x;
            yPosition = y;
            rotating = true;
        } else {
            rotating = false;
        }
    }

    public void onMouseMove(int x, int y) {

        if(!rotating)
            return;

        horizontalAngle += mouseSpeed * (xPosition - x);
        verticalAngle += mouseSpeed * (yPosition - y);

        if(verticalAngle > MAX_ELEVATION){
            verticalAngle = MAX_ELEVATION;
        }
        if(verticalAngle < MIN_ELEVALITON){
            verticalAngle = MIN_ELEVALITON;
        }

        xPosition = x;
        yPosition = y;

        direction = new Vec3(
                (float) (Math.cos(verticalAngle) * Math.sin(horizontalAngle)),
                (float) (Math.sin(verticalAngle)),
                (float) (Math.cos(verticalAngle) * Math.cos(horizontalAngle))
        );
        rightDirection = new Vec3(
                (float) (Math.sin(horizontalAngle - 3.14f / 2.0f)),
                0f,
                (float) (Math.cos(horizontalAngle - 3.14f / 2.0f))
        );

    }

    public void onKeyPressed(char c) {
        switch (c) {
            case 'w':
                position = position.add(direction.multiply(speed));
                break;
            case 's':
                position = position.subtract(direction.multiply(speed));
                break;
            case 'a':
                position = position.subtract(rightDirection.multiply(speed));
                break;
            case 'd':
                position = position.add(rightDirection.multiply(speed));
                break;
        }

    }

    public Vec3 getEyePosition() {
        return position;
    }

    public Vec3 getCenterPosition() {
        return position.add(direction);
    }

}
