package sk.styk.martin.pv112.project;

import com.hackoeur.jglm.*;
import com.jogamp.opengl.GL3;
import static com.jogamp.opengl.GL3.*;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLDebugMessage;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;
import sk.styk.martin.pv112.project.lights.AttenuationLight;
import sk.styk.martin.pv112.project.lights.Light;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.materials.*;
import sk.styk.martin.pv112.project.objects.*;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.*;
import sk.styk.martin.pv112.project.tooling.CubeRandomRotate;

/**
 *
 * @author Adam Jurcik <xjurc@fi.muni.cz>
 */
public class Scene implements GLEventListener {

    public static final int CEILING_POS = 8;
    public static final int FLOOR_POS = -8;
    public static final float CEILING_LIGHT_HEIGHT = CEILING_POS - 0.2f;

    private FPSAnimator animator;
    private Camera camera;
    private int mode = GL_FILL;

    public static float lightPower = 1;

    // window size
    private int width;
    private int height;

    //reusable textures
    private ConfigurableTexture floorTexture;
    private ConfigurableTexture carpetTexture;
    private ConfigurableTexture wallTexture;
    private ConfigurableTexture wallCovering;
    private ConfigurableTexture personalPicture;

    // programs
    private Program basicProgram;

    // models
    private Cube wall;
    private Table table;
    private Vase vase;
    private Sofa sofa;
    private Teapot teapot;
    private Teapot teapot2;
    private Cube dice;
    private Cube rubic;
    
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
    
    // JOGL resouces
    private int joglArray; // JOGL uses own vertex array for updating GLJPanel

    // helper classes
    private CubeRandomRotate cubeRandomRotate = new CubeRandomRotate(10);


    public Scene(FPSAnimator animator, Camera camera) {
        this.animator = animator;
        this.camera = camera;
    }
    
    public void toggleLines() {
        mode = GL_LINE;
    }
    
    public void toggleFill() {
        mode = GL_FILL;
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

        // enable depth test
        gl.glEnable(GL_DEPTH_TEST);

        // load GLSL program (vertex and fragment shaders)
        basicProgram = new BasicProgram(gl);

	    // get JOGL vertex array
        int binding[] = new int[1];
        gl.glGetIntegerv(GL_VERTEX_ARRAY_BINDING, binding, 0);
        joglArray = binding[0];

        // clear current Vertex Array Object state
        gl.glBindVertexArray(joglArray);

        // create geometry
        wall = new Cube(basicProgram, WallMaterial.getInstance());

        table = new Table(basicProgram, ChromeMaterial.getInstance(), new WoodTexture(gl,GL_MIRRORED_REPEAT, GL_MIRRORED_REPEAT, GL_REPEAT, 12,-1));
        table.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-14,-8,+26)).multiply(MatricesUtils.scale(0.1f,0.08f,0.1f)));

        vase = new Vase(basicProgram,PewterMaterial.getInstance());
        vase.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(14,-8,16)).multiply(MatricesUtils.scale(0.1f,0.2f,0.1f)));

        sofa = new Sofa(basicProgram, ChromeMaterial.getInstance(), new WoodTexture(gl));
        sofa.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(5.0f, 5.0f, 0.0f)));

        teapot = new Teapot(basicProgram, ChromeMaterial.getInstance(), new RockTexture(gl));
        teapot.setModel(Mat4.MAT4_IDENTITY);

        teapot2 = new Teapot(basicProgram, GoldMaterial.getInstance());
        teapot2.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(5.0f, 0.0f, 0.0f)));

        dice = new Dice(basicProgram);
        dice.setModel(Mat4.MAT4_IDENTITY);

        rubic = new RubicCube(basicProgram);
        rubic.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-5.0f, -5.0f, 0.0f)));

        light1 = new AttenuationLight(new Vec4(5.0f, CEILING_LIGHT_HEIGHT, -10.0f, 1.0f));
        light2 = new AttenuationLight(new Vec4(3.0f, CEILING_LIGHT_HEIGHT, -5.0f, 1.0f));
        light3 = new AttenuationLight(new Vec4(5.0f, CEILING_LIGHT_HEIGHT, 0.0f, 1.0f));
        light4 = new AttenuationLight(new Vec4(3.0f, CEILING_LIGHT_HEIGHT, 5.0f, 1.0f));
        light5 = new AttenuationLight(new Vec4(5.0f, CEILING_LIGHT_HEIGHT, 10.0f, 1.0f));
        light6 = new AttenuationLight(new Vec4(-3.0f, CEILING_LIGHT_HEIGHT, -10.0f, 1.0f));
        light7 = new AttenuationLight(new Vec4(-5.0f, CEILING_LIGHT_HEIGHT, -5.0f, 1.0f));
        light8 = new AttenuationLight(new Vec4(-3.0f, CEILING_LIGHT_HEIGHT, 0.0f, 1.0f));
        light9 = new AttenuationLight(new Vec4(-5.0f, CEILING_LIGHT_HEIGHT, 5.0f, 1.0f));
        light10 = new AttenuationLight(new Vec4(-3.0f, CEILING_LIGHT_HEIGHT, 10.0f, 1.0f));

        //texture load
        floorTexture = new ParquetTexture(gl,GL_MIRRORED_REPEAT, GL_REPEAT, GL_MIRRORED_REPEAT, 5,0);
        carpetTexture = new CarpetTexture(gl,GL_MIRRORED_REPEAT, GL_MIRRORED_REPEAT, GL_REPEAT, 12,-1);
        wallTexture = new WallTexture(gl,GL_REPEAT, GL_REPEAT, GL_REPEAT, 3,-1);
        wallCovering = new WallCovering(gl, GL_REPEAT, GL_REPEAT, GL_REPEAT,1,0);
        personalPicture = new PersonPictureTexture(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // not used
    }
    
    private float t = 0;
    
    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        Mat4 mvp;
        // animate variables
        if (animator.isAnimating()) {
            t += 0.02f;
        }
        
        // set perspective projection
        Mat4 projection = Matrices.perspective(60.0f, (float) width / (float) height, 1.0f, 500.0f);
        
        // set view transform based on camera position and orientation
        Vec3 yAxis = new Vec3(0.0f, 1.0f, 0.0f);
        Mat4 view = Matrices.lookAt(camera.getEyePosition(), camera.getCenterPosition(), yAxis);
        
        // get projection * view (VP) matrix
//        Mat4 vp = Mat4.MAT4_IDENTITY;
//        vp = vp.multiply(projection);
//        vp = vp.multiply(view);

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
        basicProgram.setGlobalAmbientLight(new Vec3(0.01f,0.01f,0.01f));
        //LIGHTS
        //light1.animateLight(t/100);
     
        //CAMERA   
        basicProgram.bindCamera(camera);
        
        gl.glUseProgram(0);
     
        // draw filled polygons or lines
        gl.glPolygonMode(GL_FRONT_AND_BACK, mode);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //walls
        drawWalls(projection,view);

        //cubes, rubic and dice
        drawCubes(projection,view);

        // teapot
        mvp = projection.multiply(view).multiply(teapot.getModel());
        teapot.draw(mvp);

        //table
        mvp = projection.multiply(view).multiply(table.getModel());
        table.draw(mvp);

        //vase
        mvp = projection.multiply(view.multiply(vase.getModel()));
        vase.draw(mvp);

        //sofa
        mvp = projection.multiply(view).multiply(sofa.getModel());
        sofa.draw(mvp);

        // teapot2
        mvp = projection.multiply(view).multiply(teapot2.getModel());
        teapot2.draw(mvp);

        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        
        this.width = width;
	this.height = height;
        
	gl.glViewport(0, 0, width, height);
    }

    private void drawWalls(Mat4 projection, Mat4 view){

        float directionA = 30.0f;
        float directionB = 20.0f;

        wall.setTexture(null);
        //back wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float)(0.5f * Math.PI), new Vec3(0,1,0)))
                .translate(new Vec3(directionA, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionB)));
        Mat4 mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(null);
        //front wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float)(1.5 * Math.PI), new Vec3(0,1,0)))
                .translate(new Vec3(directionA, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionB)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(wallCovering);
        //left wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float)( 1 *  Math.PI), new Vec3(0,1,0)))
                .translate(new Vec3(directionB, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(null);
        //right wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(directionB, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 8.0f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //ceiling
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, CEILING_POS, 0.0f))
                .multiply(MatricesUtils.scale(directionB, 0.1f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //floor
        wall.setTexture(floorTexture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, FLOOR_POS, 0.0f))
                .multiply(MatricesUtils.scale(directionB, 0.1f, directionA)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //carpet
        wall.setTexture(carpetTexture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, FLOOR_POS + 0.01f, 0.0f))
                .multiply(MatricesUtils.scale(directionB/2, 0.1f, directionA/2)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //picture on right wall wall
        wall.setTexture(personalPicture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(directionB - 0.01f , 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 1.0f, 1.5f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);
    }

    private void drawCubes(Mat4 projection, Mat4 view) {
        float x = 12f;
        float y = -7f;
        float z = 28f;
        float diff = 2;

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x, y, z))
                .multiply(Matrices.rotate((float) ( (0.25f+cubeRandomRotate.getRandom(0)) * Math.PI ), new Vec3(0, 1, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        Mat4 mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + diff, y, z - diff))
                .multiply(Matrices.rotate((float) ( (cubeRandomRotate.getRandom(1) + 0.25f) * Math.PI ), new Vec3(0, 1, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + 2*diff, y, z - 2*diff))
                .multiply(Matrices.rotate((float) ( (cubeRandomRotate.getRandom(2) + 0.75f) * Math.PI ), new Vec3(0, 1, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + 3*diff, y, z - 3*diff))
                .multiply(Matrices.rotate((float) ( (cubeRandomRotate.getRandom(3) + 0.75f) * Math.PI ), new Vec3(0, 1, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        //2nd level

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + 2.5f*diff, y + diff, z - 2.5f*diff))
                .multiply(Matrices.rotate((float) ( (cubeRandomRotate.getRandom(4) + 1.25f) * Math.PI ), new Vec3(0, 1, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + 1.5f*diff, y + diff, z - 1.5f*diff))
                .multiply(Matrices.rotate((float) (1.25f * Math.PI), new Vec3(0, 1, 0)))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), new Vec3(1, 0, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + 0.5f*diff, y + diff, z - 0.5f*diff))
                .multiply(Matrices.rotate((float) (1.25f * Math.PI), new Vec3(0, 1, 0)))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), new Vec3(1, 0, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        //3rd level

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + 2f*diff, y + 2*diff, z - 2f*diff))
                .multiply(Matrices.rotate((float) ( (cubeRandomRotate.getRandom(7) + 1.75f) * Math.PI ), new Vec3(0, 1, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

        rubic.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + diff, y + 2*diff, z - diff))
                .multiply(Matrices.rotate((float) ((cubeRandomRotate.getRandom(8) + 1.75f) * Math.PI ), new Vec3(0, 1, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(rubic.getModel());
        rubic.draw(mvp);

        //top

        dice.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3( x + 1.5f*diff, y + 3f*diff, z - 1.5f*diff))
                .multiply(Matrices.rotate((float) (0.25f * Math.PI), new Vec3(0, 1, 0)))
                .multiply(Matrices.rotate((float) (0.5f * Math.PI), new Vec3(1, 0, 0)))
                .multiply(MatricesUtils.scale(1, 1, 1)));
        mvp = projection.multiply(view).multiply(dice.getModel());
        dice.draw(mvp);

    }
    public void moreLight(){
        if(lightPower > 0.7){
            lightPower -= 0.1f;
        }
    }
    public void lessLight(){
        if(lightPower < 5){
            lightPower += 0.1f;
        }
    }
}