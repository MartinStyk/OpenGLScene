package sk.styk.martin.pv112.project;

import com.hackoeur.jglm.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.lights.AttenuationLight;
import sk.styk.martin.pv112.project.lights.Light;
import sk.styk.martin.pv112.project.materials.*;
import sk.styk.martin.pv112.project.objects.*;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.*;
import sk.styk.martin.pv112.project.tooling.ClockUtils;
import sk.styk.martin.pv112.project.tooling.RandomRotate;
import sk.styk.martin.pv112.project.tooling.SceneConstants;

import java.util.ArrayList;
import java.util.List;

import static com.jogamp.opengl.GL3.*;

/**
 * Represents rendered scene with objects
 */
public class Scene implements GLEventListener {

    //interactive
    public static float lightPower = 1;
    private FPSAnimator animator;
    private Camera camera;
    private int mode = GL_FILL;
    private List<Interactive> interactiveList = new ArrayList<>();

    // window size
    private int width;
    private int height;

    // glsl programs
    private Program basicProgram;

    // models
    private Cube wall;
    private Table table;
    private Vase vase;
    private Bin bin;
    private Teapot teapot;
    private Cube dice;
    private Cube rubic;
    private Clock clock;
    private Box box;
    private Library library;
    private Lamp lamp; //not in scene now
    private Radio radio;
    private Alfa147 alfa147;
    private Earth earth;
    private Lamp earthBottom;

    //lights
    private Light light1;
    private Light light2;
    private Light light3;
    private Light light4;
    private Light light5;
    private Light light6;
    private Light light7;
    private Light light8;
    private Light light9;
    private Light light10;

    //reusable textures
    private ConfigurableTexture floorTexture;
    private ConfigurableTexture carpetTexture;
    private ConfigurableTexture wallTexture;
    private ConfigurableTexture personalPicture;
    private ConfigurableTexture dogPicture;
    private ConfigurableTexture civicPicture;
    private ConfigurableTexture ceramicWhite;
    private ConfigurableTexture ceramicBlue;
    private ConfigurableTexture woodTexture;

    // JOGL resouces
    private int joglArray; // JOGL uses own vertex array for updating GLJPanel

    // helpers
    private RandomRotate cubeRandomRotate = new RandomRotate(10);
    private float t = 0;

    public Scene(FPSAnimator animator, Camera camera) {
        this.animator = animator;
        this.camera = camera;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        // add listener that will inform us when we make an error.
        gl.getContext().addGLDebugListener(new GLDebugListener() {
            @Override
            public void messageSent(GLDebugMessage event) {
                switch (event.getDbgType()) {
                    case GL_DEBUG_TYPE_ERROR:
                    case GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
                    case GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
                        System.err.println(event.getDbgMsg());
                        break;
                }
            }
        });

        // empty scene color
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glLineWidth(3.0f);

        // enable/disable features
        gl.glEnable(GL_DEPTH_TEST);
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_ONE, GL_ZERO);
        gl.glDisable(GL_CULL_FACE);

        // load GLSL program (vertex and fragment shaders)
        basicProgram = new BasicProgram(gl);

        // get JOGL vertex array
        int binding[] = new int[1];
        gl.glGetIntegerv(GL_VERTEX_ARRAY_BINDING, binding, 0);
        joglArray = binding[0];

        // clear current Vertex Array Object state
        gl.glBindVertexArray(joglArray);

        //texture load
        floorTexture = new ParquetTexture(gl, GL_MIRRORED_REPEAT, GL_REPEAT, GL_MIRRORED_REPEAT, 5, 0);
        carpetTexture = new CarpetTexture(gl, GL_MIRRORED_REPEAT, GL_MIRRORED_REPEAT, GL_REPEAT, 12, -1);
        wallTexture = new ProceduralWallColorTexture();
        personalPicture = new PersonPictureTexture(gl);
        civicPicture = new CivicPictureTexture(gl);
        dogPicture = new DogPictureTexture(gl);
        ceramicBlue = new Ceramic2(gl);
        ceramicWhite = new Ceramic1(gl);
        woodTexture = new WoodTexture(gl);


        // create geometry with fixed position
        Vec3 radioPosition = new Vec3(-15f, -1.5f, 25f);
        radio = new Radio(basicProgram, radioPosition, camera);
        radio.setModel(Mat4.MAT4_IDENTITY.translate(radioPosition)
                .multiply(Matrices.rotate((float) Math.PI * 0.9f, SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(2f, 1f, 1f)));

        Vec3 earthPosition = new Vec3(-16, 0, -15);
        earth = new Earth(basicProgram, earthPosition, camera);
        earth.setModel(Mat4.MAT4_IDENTITY.translate(earthPosition)
                .multiply(MatricesUtils.scale(5.5f, 5.5f, 5.5f)));

        table = new Table(basicProgram, ChromeMaterial.getInstance(), new WoodTexture(gl, GL_MIRRORED_REPEAT, GL_MIRRORED_REPEAT, GL_REPEAT, 12, -1));
        table.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-14, -8, +26))
                .multiply(MatricesUtils.scale(0.1f, 0.08f, 0.1f)));

        bin = new Bin(basicProgram, BlackPlastic.getInstance());
        bin.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-5, -7.5f, +26))
                .multiply(MatricesUtils.scale(0.1f, 0.08f, 0.1f)));

        clock = new Clock(basicProgram);
        clock.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(0, 2, 29.6f))
                .multiply(Matrices.rotate((float) Math.PI, SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(0.1f, 0.1f, 0.1f)));

        alfa147 = new Alfa147(basicProgram, ChromeMaterial.getInstance());
        alfa147.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(-10, -2.4f, 25f))
                .multiply(MatricesUtils.scale(0.02f, 0.02f, 0.02f))
                .multiply(Matrices.rotate((float) Math.PI / -2, new Vec3(1, 0, 0)))
                .multiply(Matrices.rotate((float) Math.PI / 2 + 0.1f, new Vec3(0, 0, 1)))
        );

        // create geometry with position adjusted during runtime
        box = new Box(basicProgram, ChromeMaterial.getInstance(), woodTexture);
        dice = new Dice(basicProgram);
        earthBottom = new Lamp(basicProgram, BlackPlastic.getInstance());//use lamp object for bottom of earth
        lamp = new Lamp(basicProgram, RubyMaterial.getInstance());
        library = new Library(basicProgram, ChromeMaterial.getInstance(), woodTexture);
        rubic = new RubicCube(basicProgram);
        teapot = new Teapot(basicProgram, ChromeMaterial.getInstance());
        vase = new Vase(basicProgram, PewterMaterial.getInstance(), new Ceramic1(gl));
        wall = new Cube(basicProgram, WallMaterial.getInstance());

        //create lights
        light1 = new AttenuationLight(new Vec4(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, -15.0f, 1.0f));
        light2 = new AttenuationLight(new Vec4(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, -7.0f, 1.0f));
        light3 = new AttenuationLight(new Vec4(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, 0.0f, 1.0f));
        light4 = new AttenuationLight(new Vec4(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, 7.0f, 1.0f));
        light5 = new AttenuationLight(new Vec4(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, 15.0f, 1.0f));
        light6 = new AttenuationLight(new Vec4(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, -15.0f, 1.0f));
        light7 = new AttenuationLight(new Vec4(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, -7.0f, 1.0f));
        light8 = new AttenuationLight(new Vec4(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, 0.0f, 1.0f));
        light9 = new AttenuationLight(new Vec4(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, 7.0f, 1.0f));
        light10 = new AttenuationLight(new Vec4(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT, 15.0f, 1.0f));
        basicProgram.setGlobalAmbientLight(new Vec3(0.01f, 0.01f, 0.01f));

        //interactive init
        interactiveList.add(radio);
        interactiveList.add(earth);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // not used
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        // animate variables
        if (animator.isAnimating()) {
            t += 0.02f;
        }

        // set perspective projection
        Mat4 projection = Matrices.perspective(60.0f, (float) width / (float) height, 1.0f, 500.0f);

        // set view transform based on camera position and orientation
        Mat4 view = Matrices.lookAt(camera.getEyePosition(), camera.getEyeDirection(), SceneConstants.Y_AXIS);

        //LIGHTS
        basicProgram.use();
        basicProgram.bindLight(1, light1);
        basicProgram.bindLight(2, light2);
        basicProgram.bindLight(3, light3);
        basicProgram.bindLight(4, light4);
        basicProgram.bindLight(5, light5);
        basicProgram.bindLight(6, light6);
        basicProgram.bindLight(7, light7);
        basicProgram.bindLight(8, light8);
        basicProgram.bindLight(9, light9);
        basicProgram.bindLight(10, light10);
        basicProgram.setAlpha(1.0f);

        //CAMERA   
        basicProgram.bindCamera(camera);

        gl.glUseProgram(0);

        // draw filled polygons or lines
        gl.glPolygonMode(GL_FRONT_AND_BACK, mode);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //geometry with adjusting positions
        //walls
        drawWalls(projection, view);

        //lamps
        drawLamps(projection, view);

        //cubes, rubic and dice
        drawCubes(projection, view);

        //clock
        drawClock(projection, view);

        //libraries
        drawLibraries(projection, view);

        //teapots
        drawTeapots(projection, view);

        //vase
        drawVases(projection, view);

        //box
        drawBoxes(projection, view);

        //earth
        drawEarth(projection,view);

        //geometry with fixed position
        radio.setViewProjection(projection.multiply(view)); //neccesary to set because it is interactive
        radio.draw(projection.multiply(view).multiply(radio.getModel()));

        table.draw(projection.multiply(view).multiply(table.getModel()));

        bin.draw(projection.multiply(view).multiply(bin.getModel()));

        alfa147.draw(projection.multiply(view).multiply(alfa147.getModel()));

        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    private void drawEarth(Mat4 projection, Mat4 view) {
        earth.setViewProjection(projection.multiply(view));
        earth.draw(projection.multiply(view).multiply(earth.getModel()));

        earthBottom.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(-16, -3.5f,  -15))
                .multiply(MatricesUtils.scale(0.025f, 2f, 0.025f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        earthBottom.draw(projection.multiply(view).multiply(earthBottom.getModel()));

        earthBottom.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(-16, -8, -15.5f))
                .multiply(MatricesUtils.scale(0.1f, 0.2f, 0.1f))
                .multiply(Matrices.rotate((float) (-0.5f * Math.PI), SceneConstants.X_AXIS)));
        earthBottom.draw(projection.multiply(view).multiply(earthBottom.getModel()));
    }

    private void drawBoxes(Mat4 projection, Mat4 view) {

        box.setTexture(woodTexture);
        box.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-17, -8, -25))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(8)) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(0.07f, 0.07f, 0.07f)));
        box.draw(projection.multiply(view).multiply(box.getModel()));
        box.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-17, -3.8f, -25))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(7)) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(0.07f, 0.07f, 0.07f)));
        box.draw(projection.multiply(view).multiply(box.getModel()));

    }

    private void drawLamps(Mat4 projection, Mat4 view) {

        float heightOffset = 0.25f;

        //left row
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, -15 + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, -7 + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, 0f + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, 7f + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, 15f + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));

        //right row
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, -15 + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, -7 + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, 0f + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, 7f + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));
        lamp.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-SceneConstants.LIGHT_DIST_FROM_CENTER, SceneConstants.CEILING_LIGHT_HEIGHT + heightOffset, 15f + 0.05f))
                .multiply(MatricesUtils.scale(0.011f, 0.08f, 0.011f))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS)));
        lamp.draw(projection.multiply(view).multiply(lamp.getModel()));

    }

    private void drawVases(Mat4 projection, Mat4 view) {

        vase.setTexture(ceramicBlue);
        vase.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(17, -8, 3))
                .multiply(MatricesUtils.scale(0.1f, 0.2f, 0.1f)));
        vase.draw(projection.multiply(view).multiply(vase.getModel()));

        vase.setTexture(ceramicWhite);
        vase.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(17, -8, 6))
                .multiply(MatricesUtils.scale(0.12f, 0.3f, 0.12f)));
        vase.draw(projection.multiply(view).multiply(vase.getModel()));

        vase.setTexture(ceramicBlue);
        vase.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(17, -8, 9))
                .multiply(MatricesUtils.scale(0.1f, 0.2f, 0.1f)));
        vase.draw(projection.multiply(view).multiply(vase.getModel()));

        vase.setTexture(ceramicWhite);
        vase.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(17, -8, 12))
                .multiply(MatricesUtils.scale(0.12f, 0.3f, 0.12f)));
        vase.draw(projection.multiply(view).multiply(vase.getModel()));

        vase.setTexture(ceramicBlue);
        vase.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(17, -8, 15))
                .multiply(MatricesUtils.scale(0.1f, 0.2f, 0.1f)));
        vase.draw(projection.multiply(view).multiply(vase.getModel()));
    }

    private void drawTeapots(Mat4 projection, Mat4 view) {

        float x = 18;
        float lvl1 = +2.4f;
        float lvl2 = +0.4f;
        float lvl3 = -1.75f;
        float lvl4 = -4.5f;
        float middle = -13f;

        //chrome from left to right

        teapot.setTexture(null);
        teapot.setMaterial(ChromeMaterial.getInstance());
        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl3, -20))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl1, middle))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl4, -8))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        //bronze
        teapot.setMaterial(BronzeMaterial.getInstance());

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl2, -18))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl4, middle + 0.2f))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl3, -7))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        //ruby
        teapot.setMaterial(RubyMaterial.getInstance());
        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl1, -23))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl2, middle - 0.2f))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl2, -5.5f))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        //ceramicBlue
        teapot.setTexture(ceramicBlue);
        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl4, -23))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl3, middle - 0.7f))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl1, -8))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        //ceramicWhite
        teapot.setTexture(ceramicWhite);
        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl1, -19))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

        teapot.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(x, lvl4, -3.5f))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS)));
        teapot.draw(projection.multiply(view).multiply(teapot.getModel()));

    }

    private void drawLibraries(Mat4 projection, Mat4 view) {
        library.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(18, -8, -20))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(0.12f, 0.07f, 0.12f)));
        library.draw(projection.multiply(view).multiply(library.getModel()));
        library.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(18, -8, -13))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(0.07f, 0.07f, 0.12f)));
        library.draw(projection.multiply(view).multiply(library.getModel()));
        library.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(18, -8, -6))
                .multiply(Matrices.rotate((float) Math.PI / -2, SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(0.12f, 0.07f, 0.12f)));
        library.draw(projection.multiply(view).multiply(library.getModel()));
    }

    private void drawWalls(Mat4 projection, Mat4 view) {

        float directionA = 30.0f;
        float directionB = 20.0f;

        wall.setTexture(wallTexture);
        wall.setMaterial(WallMaterial.getInstance());
        //back wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.Y_AXIS))
                .translate(new Vec3(directionA, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionB)));
        Mat4 mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //front wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float) (1.5 * Math.PI), SceneConstants.Y_AXIS))
                .translate(new Vec3(directionA, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionB)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(new ProceduralWallWhiteTexture());
        //left wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float) (1 * Math.PI), SceneConstants.Y_AXIS))
                .translate(new Vec3(directionB, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //right wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(directionB, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(null);
        //ceiling
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, SceneConstants.CEILING_POS, 0.0f))
                .multiply(MatricesUtils.scale(directionB, 0.1f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //floor
        wall.setTexture(floorTexture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, SceneConstants.FLOOR_POS, 0.0f))
                .multiply(MatricesUtils.scale(directionB, 0.1f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //carpet
        wall.setTexture(carpetTexture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, SceneConstants.FLOOR_POS + 0.01f, 0.0f))
                .multiply(MatricesUtils.scale(directionB / 2, 0.1f, directionA / 2)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

//        wall.setTexture();
//        //left picture
//        wall.setModel(Mat4.MAT4_IDENTITY
//                .multiply(Matrices.rotate((float) (1 * Math.PI), SceneConstants.Y_AXIS))
//                .translate(new Vec3(directionB - 0.15f, 0.0f, 0.0f))
//                .multiply(MatricesUtils.scale(0.1f, 3.0f, 6.0f)));
//        mvp = projection.multiply(view).multiply(wall.getModel());
//        wall.draw(mvp);

        //pictures on backs wall
        wall.setMaterial(ChromeMaterial.getInstance());
        wall.setTexture(personalPicture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0, 0.0f, -directionA + 0.1f))
                .multiply(MatricesUtils.scale(4f, 1.8f, 0.1f))
                .multiply(Matrices.rotate((float) (Math.PI / 2), SceneConstants.Y_AXIS)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(dogPicture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(+10, 0.0f, -directionA + 0.1f))
                .multiply(MatricesUtils.scale(4f, 1.8f, 0.1f))
                .multiply(Matrices.rotate((float) (Math.PI / 2), SceneConstants.Y_AXIS)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(civicPicture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(-10, 0.0f, -directionA + 0.1f))
                .multiply(MatricesUtils.scale(4f, 1.8f, 0.1f))
                .multiply(Matrices.rotate((float) (Math.PI / 2), SceneConstants.Y_AXIS)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);
    }

    private void drawCubes(Mat4 projection, Mat4 view) {
        float x = 12f;
        float y = -7f;
        float z = 28f;
        float diff = 2;

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x, y, z))
                .multiply(Matrices.rotate((float) ((0.25f + cubeRandomRotate.getRandom(0)) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        Mat4 mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + diff, y, z - diff))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(1) + 0.25f) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + 2 * diff, y, z - 2 * diff))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(2) + 0.75f) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + 3 * diff, y, z - 3 * diff))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(3) + 0.75f) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        //2nd level

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + 2.5f * diff, y + diff, z - 2.5f * diff))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(4) + 1.25f) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + 1.5f * diff, y + diff, z - 1.5f * diff))
                .multiply(Matrices.rotate((float) (1.25f * Math.PI), SceneConstants.Y_AXIS))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + 0.5f * diff, y + diff, z - 0.5f * diff))
                .multiply(Matrices.rotate((float) (1.25f * Math.PI), SceneConstants.Y_AXIS))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        //3rd level

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + 2f * diff, y + 2 * diff, z - 2f * diff))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(7) + 1.75f) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + diff, y + 2 * diff, z - diff))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(8) + 1.75f) * Math.PI), SceneConstants.Y_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        //top

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(x + 1.5f * diff, y + 3f * diff, z - 1.5f * diff))
                .multiply(Matrices.rotate((float) (0.25f * Math.PI), SceneConstants.Y_AXIS))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), SceneConstants.X_AXIS))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

    }

    private void drawClock(Mat4 projection, Mat4 view) {
        ClockUtils clockUtils = clock.getClockUtils();

        Mat4 mvp = projection.multiply(view).multiply(clock.getModel());

        clock.getClockHourHand().setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0, 1.68f, 29.3f))
                .multiply(Matrices.rotate(clockUtils.getHourHandRotation(), SceneConstants.Z_AXIS))
                .multiply(MatricesUtils.scale(0.1f, 0.1f, 0.1f))
                .translate(new Vec3(0, 4, 0)));
        Mat4 mvpHour = projection.multiply(view).multiply(clock.getClockHourHand().getModel());

        clock.getClockMinuteHand().setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0, 1.68f, 29.3f))
                .multiply(MatricesUtils.scale(0.1f, 0.1f, 0.1f))
                .multiply(Matrices.rotate(clockUtils.getMinuteHandRotation(), SceneConstants.Z_AXIS))
                .translate(new Vec3(0, 4, 0)));
        Mat4 mvpMinute = projection.multiply(view).multiply(clock.getClockMinuteHand().getModel());

        clock.getClockSecondHand().setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0, 1.68f, 29.3f))
                .multiply(MatricesUtils.scale(0.1f, 0.1f, 0.1f))
                .multiply(Matrices.rotate(clockUtils.getSecondsHandRotation(), SceneConstants.Z_AXIS))
                .translate(new Vec3(0, 4, 0)));
        Mat4 mvpSecond = projection.multiply(view).multiply(clock.getClockSecondHand().getModel());

        clock.draw(mvp, mvpHour, mvpMinute, mvpSecond);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();

        this.width = width;
        this.height = height;

        gl.glViewport(0, 0, width, height);
    }

    //METHOD FOR INTERACTION FROM MainWindow.java
    public void moreLight() {
        if (lightPower > 0.7) {
            lightPower -= 0.1f;
        }
    }

    public void lessLight() {
        if (lightPower < 3) {
            lightPower += 0.1f;
        }
    }

    public void interact() {
        for (Interactive interactive : interactiveList) {
            if (interactive.isInRange())
                interactive.interact();
        }
    }

    public void toggleLines() {
        mode = GL_LINE;
    }

    public void toggleFill() {
        mode = GL_FILL;
    }
}