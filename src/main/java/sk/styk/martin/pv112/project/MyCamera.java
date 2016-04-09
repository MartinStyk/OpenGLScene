package sk.styk.martin.pv112.project;

import com.hackoeur.jglm.Vec3;


/**
 * Created by Martin Styk on 09.04.2016.
 */
public class MyCamera implements Camera {
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

    public void onMouseMove(int x, int y, int mouseButton) {

        horizontalAngle += mouseSpeed * (xPosition - x);
        verticalAngle += mouseSpeed * (yPosition - y);

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
            case 'd':
                position = position.subtract(rightDirection.multiply(speed));
                break;
            case 'a':
                position = position.add(rightDirection.multiply(speed));
                break;
        }

    }

    public Vec3 getEyePosition() {
        return position;
    }

    public Vec3 getCenterPosition() {
        if (direction.equals(new Vec3(0, 0, 0))) {
            return new Vec3(0, 0, 0);
        }
        return position.add(direction);
    }

}
