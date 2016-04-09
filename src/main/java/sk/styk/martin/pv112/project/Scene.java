package sk.styk.martin.pv112.project;

import com.hackoeur.jglm.*;
import com.jogamp.opengl.GL3;
import static com.jogamp.opengl.GL3.*;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLDebugMessage;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;
import sk.styk.martin.pv112.project.Lights.AttenuationLight;
import sk.styk.martin.pv112.project.Lights.Light;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.materials.*;
import sk.styk.martin.pv112.project.objects.Cube;
import sk.styk.martin.pv112.project.objects.Dice;
import sk.styk.martin.pv112.project.objects.Sofa;
import sk.styk.martin.pv112.project.objects.Teapot;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.*;

/**
 *
 * @author Adam Jurcik <xjurc@fi.muni.cz>
 */
public class Scene implements GLEventListener {

    public static final int CEILING_POS = 5;
    public static final int FLOOR_POS = -5;
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
    private Sofa sofa;
    private Teapot teapot;
    private Teapot teapot2;
    private Cube cube;
    private Cube cube2;
    
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
        
    // our GLSL resources (model)
  
    
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

        sofa = new Sofa(basicProgram, ChromeMaterial.getInstance(), new WoodTexture(gl));
        sofa.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(5.0f, 5.0f, 0.0f)));

        teapot = new Teapot(basicProgram, ChromeMaterial.getInstance(), new RockTexture(gl));
        teapot.setModel(Mat4.MAT4_IDENTITY);

        teapot2 = new Teapot(basicProgram, GoldMaterial.getInstance());
        teapot2.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(5.0f, 0.0f, 0.0f)));

        cube = new Dice(basicProgram);
        cube.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-5.0f, 0.0f, 0.0f)));

        cube2 = new Cube(basicProgram, ChromeMaterial.getInstance(), new RockTexture(gl,GL_MIRRORED_REPEAT,GL_MIRRORED_REPEAT,GL_MIRRORED_REPEAT,3,-1));
        cube2.setModel(Mat4.MAT4_IDENTITY.translate(new Vec3(-5.0f, -5.0f, 0.0f)));

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
        carpetTexture = new CarpetTexture(gl,GL_MIRRORED_REPEAT, GL_MIRRORED_REPEAT, GL_REPEAT, 15,-1);
        wallTexture = new WallTexture(gl,GL_REPEAT, GL_REPEAT, GL_REPEAT, 6,-1);
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

        // teapot
        Mat4 mvp = projection.multiply(view).multiply(teapot.getModel());
        teapot.draw(mvp);

        //sofa
        mvp = projection.multiply(view).multiply(sofa.getModel());
        sofa.draw(mvp);

        // teapot2
        mvp = projection.multiply(view).multiply(teapot2.getModel());
        teapot2.draw(mvp);

        // cube
        mvp = projection.multiply(view).multiply(cube.getModel());
        cube.draw(mvp);

        // cube2
        mvp = projection.multiply(view).multiply(cube2.getModel());
        cube2.draw(mvp);

                
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
        wall.setTexture(null);
        //back wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float)(0.5f * Math.PI), new Vec3(0,1,0)))
                .translate(new Vec3(15.0f, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 5.0f, 10.0f)));
        Mat4 mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(null);
        //front wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float)(1.5 * Math.PI), new Vec3(0,1,0)))
                .translate(new Vec3(15.0f, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 5.0f, 10.0f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(wallCovering);
        //left wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .multiply(Matrices.rotate((float)( 1 *  Math.PI), new Vec3(0,1,0)))
                .translate(new Vec3(10.0f, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 5.0f, 15.0f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        wall.setTexture(null);
        //right wall
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(10.0f, 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 5.0f, 15.0f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //ceiling
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, CEILING_POS, 0.0f))
                .multiply(MatricesUtils.scale(10.0f, 0.1f, 15.0f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //floor
        wall.setTexture(floorTexture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, FLOOR_POS, 0.0f))
                .multiply(MatricesUtils.scale(10.0f, 0.1f, 15.0f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //carpet
        wall.setTexture(carpetTexture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(0.0f, FLOOR_POS + 0.01f, 0.0f))
                .multiply(MatricesUtils.scale(6.0f, 0.1f, 10.0f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);

        //picture on right wall wall
        wall.setTexture(personalPicture);
        wall.setModel(Mat4.MAT4_IDENTITY
                .translate(new Vec3(10.0f - 0.01f , 0.0f, 0.0f))
                .multiply(MatricesUtils.scale(0.1f, 1.0f, 1.5f)));
        mvp = projection.multiply(view).multiply(wall.getModel());
        wall.draw(mvp);
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