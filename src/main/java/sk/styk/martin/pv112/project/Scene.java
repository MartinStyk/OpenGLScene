package sk.styk.martin.pv112.project;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.jogamp.opengl.GL3;
import static com.jogamp.opengl.GL3.*;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLDebugMessage;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import sk.styk.martin.pv112.project.materials.ChromeMaterial;
import sk.styk.martin.pv112.project.materials.GoldMaterial;
import sk.styk.martin.pv112.project.materials.PewterMaterial;
import sk.styk.martin.pv112.project.objects.Cube;
import sk.styk.martin.pv112.project.objects.Teapot;
import sk.styk.martin.pv112.project.programs.BasicProgram;
import sk.styk.martin.pv112.project.programs.BasicTextureProgram;
import sk.styk.martin.pv112.project.programs.Program;
import sk.styk.martin.pv112.project.textures.RockTexture;
import sk.styk.martin.pv112.project.textures.WoodTexture;

/**
 *
 * @author Adam Jurcik <xjurc@fi.muni.cz>
 */
public class Scene implements GLEventListener {
   
    private FPSAnimator animator;
    private Camera camera;
    private int mode = GL_FILL;

    
    // window size
    private int width;
    private int height;
    
    // programs
    private Program basicProgram;

    // models
    private Teapot teapot;
    private Teapot teapot2;
    private Cube cube;
    private Cube cube2;
    
    //lights
    private Light light1;
    private Light light2;
    
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

        Texture wood = WoodTexture.get(gl);
        // create geometry
        teapot = new Teapot(basicProgram, ChromeMaterial.getInstance());
        teapot2 = new Teapot(basicProgram, GoldMaterial.getInstance());
        cube = new Cube(basicProgram, PewterMaterial.getInstance(),WoodTexture.get(gl));
        cube2 = new Cube(basicProgram, PewterMaterial.getInstance());

        light1 = new Light(new Vec4(3.0f, 0.0f, 0.0f, 1.0f));
        light2 = new Light(new Vec4(-7.0f, -7.0f, 0.0f, 1.0f));
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
        Mat4 view = Matrices.lookAt(camera.getEyePosition(), Vec3.VEC3_ZERO, yAxis);
        
        // get projection * view (VP) matrix
//        Mat4 vp = Mat4.MAT4_IDENTITY;
//        vp = vp.multiply(projection);
//        vp = vp.multiply(view);
//               
	
        basicProgram.use();
        basicProgram.bindLight(1, light1);
        basicProgram.bindLight(2, light2);
        //LIGHTS
        //light1.animateLight(t/100);
     
        //CAMERA   
        basicProgram.bindCamera(camera);
        
        gl.glUseProgram(0);
     
        // draw filled polygons or lines
        gl.glPolygonMode(GL_FRONT_AND_BACK, mode);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                
        // teapot
        Mat4 model = Mat4.MAT4_IDENTITY;
        Mat4 mvp = projection.multiply(view).multiply(model);

        teapot.draw(model, mvp);
        
        // teapot2
        model = Mat4.MAT4_IDENTITY.translate(new Vec3(5.0f, 0.0f, 0.0f));
        mvp = projection.multiply(view).multiply(model);
        teapot2.draw(model,mvp);

        // cube
        model = Mat4.MAT4_IDENTITY.translate(new Vec3(-5.0f, 0.0f, 0.0f));
        mvp = projection.multiply(view).multiply(model);
        cube.draw(model,mvp);

        // cube2
        model = Mat4.MAT4_IDENTITY.translate(new Vec3(-5.0f, -5.0f, 0.0f));
        mvp = projection.multiply(view).multiply(model);
        cube2.draw(model,mvp);

                
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }
    
//    private void drawModel(GL3 gl, Mat4 model, Mat4 mvp, Material material) {
//        gl.glUseProgram(modelProgram);
//
//        Mat3 n = MatricesUtils.inverse(getMat3(model).transpose());
//        gl.glUniformMatrix3fv(nLoc, 1, false, n.getBuffer());
//        
//        gl.glUniformMatrix4fv(modelLoc, 1, false, model.getBuffer());
//        
//        material.bindUniforms(gl, materialAmbientColorLoc, materialDiffuseColorLoc, materialSpecularColorLoc, materialShininessLoc);
//        
//        gl.glUniformMatrix4fv(mvpLoc, 1, false, mvp.getBuffer());
//        
//        gl.glUniform3f(colorLoc, 1f, 1f, 0.2f);
//        
//        teapot.draw(gl);
//        
//	gl.glUseProgram(0);
//    }
//    
//     private void drawModel(Drawable drawable) {
//        GL3 gl = drawable.getGl(); 
//        gl.glUseProgram(drawable.getProgram());
//
//        Mat3 n = MatricesUtils.inverse(getMat3(drawable.getModel()).transpose());
//        gl.glUniformMatrix3fv(nLoc, 1, false, n.getBuffer());
//        
//        gl.glUniformMatrix4fv(modelLoc, 1, false, drawable.getModel().getBuffer());
//        
//        drawable.getMaterial().bindUniforms(gl, materialAmbientColorLoc, materialDiffuseColorLoc, materialSpecularColorLoc, materialShininessLoc);
//        
//        gl.glUniformMatrix4fv(mvpLoc, 1, false, drawable.getMvp().getBuffer());
//        
//        gl.glUniform3f(colorLoc, 1f, 1f, 0.2f);
//        
//        drawable.getGeometry().draw(gl);
//        
//	gl.glUseProgram(0);
//    }
    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        
        this.width = width;
	this.height = height;
        
	gl.glViewport(0, 0, width, height);
    }
    
    
}