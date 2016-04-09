package sk.styk.martin.pv112.project.camera;

import com.hackoeur.jglm.Vec3;

/**
 * SIMPLE PV112 CAMERA CLASS.
 * <p>
 * This is a VERY SIMPLE class that allows to very simply move with the camera.
 * It is not a perfect, brilliant, smart, or whatever implementation of a camera,
 * but it is sufficient for PV112 lectures.
 * <p>
 * Use left mouse button to change the point of view.
 * Use right mouse button to zoom in and zoom out.
 */
public class CenterPointCamera{

    public enum Button {
        LEFT, RIGHT;
    }

    public enum Direction {
        FORWARD, BACK, LEFT, RIGHT;
    }

    /// Constants that defines the behaviour of the camera
    private static final float ONE_STEP = 0.1f;
    ///		- Minimum elevation in radians
    private static final float MIN_ELEVALITON = -1.5f;
    ///		- Maximum elevation in radians
    private static final float MAX_ELEVATION = 1.5f;
    ///		- Minimum distance from the point of interest
    private static final float MIN_DISTANCE = 1f;
    ///		- Sensitivity of the mouse when changing elevation or direction angles
    private static final float ANGLE_SENSITIVITY = 0.008f;
    ///		- Sensitivity of the mouse when changing zoom
    private static final float ZOOM_SENSITIVITY = 0.003f;

    /// direction is an angle in which determines into which direction in xz plane I look.
    ///		- 0 degrees .. I look in -z direction
    ///		- 90 degrees .. I look in -x direction
    ///		- 180 degrees .. I look in +z direction
    ///		- 270 degrees .. I look in +x direction
    private float directon;

    /// elevation is an angle in which determines from which "height" I look.
    ///		- positive elevation .. I look from above the xz plane
    ///		- negative elevation .. I look from below the xz plane
    private float elevation;

    /// Distance from (0,0,0), the point at which I look
    private float distance;

    /// Final position of the eye in world space coordinates, for LookAt or shaders
    private Vec3 position;
    private Vec3 centerPosition;

    /// Last X and Y coordinates of the mouse cursor
    private int lastX;
    private int lastY;

    public CenterPointCamera() {
        directon = 0.0f;
        elevation = 0.0f;
        distance = 1.0f;
        lastX = 0;
        lastY = 0;
        centerPosition = new Vec3(0,0,0);
        position = new Vec3(0,0,0);
        updateEyePosition();
    }

    /// Recomputes 'eye_position' from 'angle_direction', 'angle_elevation', and 'distance'
    private void updateEyePosition() {
        float x = (float) (distance * Math.cos(elevation) * -Math.sin(directon));
        float y = (float) (distance * Math.sin(elevation));
        float z = (float) (distance * Math.cos(elevation) * Math.cos(directon));
        centerPosition = new Vec3(x, y, z);
    }

    /// Called when the user moves with the mouse cursor (see MainWindow)

    public void  onMouseMove(int x, int y,int mouseButton, boolean pressed) {
 
        float dx = (float) (x - lastX);
        float dy = (float) (y - lastY);
        lastX = x;
        lastY = y;

        directon += dx * ANGLE_SENSITIVITY;
        elevation += dy * ANGLE_SENSITIVITY;

        // Clamp the results
        if (elevation > MAX_ELEVATION) {
            elevation = MAX_ELEVATION;
        }
        if (elevation < MIN_ELEVALITON) {
            elevation = MIN_ELEVALITON;
        }

        updateEyePosition();
    }

    public void onKeyPressed(char c){
        return;
    }

    /// Returns the position of the eye in world space coordinates
    public Vec3 getEyePosition() {
        return position;
    }

    public Vec3 getCenterPosition() {
       // return new Vec3 (1,1,1);
        return centerPosition;
       // return position.add(new Vec3(ONE_STEP, ONE_STEP, ONE_STEP));
        //return polarCoordinates(position.getX(), position.getY());
    }

    public Vec3 polarCoordinates(float azimuth, float altitude) {
        float cartesian[] = new float[3];
        float a = (float) Math.sin(Math.toRadians(altitude));
        cartesian[2] = (float) (a * Math.cos(Math.toRadians(azimuth)));
        cartesian[0] = (float) (a * Math.sin(Math.toRadians(azimuth)));
        cartesian[1] = (float) Math.cos(Math.toRadians(altitude));
        return new Vec3(cartesian[0], cartesian[1], cartesian[2]);
    }
}
