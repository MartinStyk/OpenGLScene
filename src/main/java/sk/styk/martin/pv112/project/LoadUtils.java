/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project;

import static com.jogamp.opengl.GL.GL_FALSE;
import static com.jogamp.opengl.GL2ES2.GL_COMPILE_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_INFO_LOG_LENGTH;
import static com.jogamp.opengl.GL2ES2.GL_LINK_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Styk
 */
public class LoadUtils {
   
 public static int loadShader(GL3 gl, String filename, int shaderType) throws IOException {
        String source = readAllFromResource(filename);
        int shader = gl.glCreateShader(shaderType);
        
        // create and compile GLSL shader
        gl.glShaderSource(shader, 1, new String[] { source }, new int[] { source.length() }, 0);
        gl.glCompileShader(shader);
        
        // check GLSL shader compile status
        int[] status = new int[1];
        gl.glGetShaderiv(shader, GL_COMPILE_STATUS, status, 0);
        if (status[0] == GL_FALSE) {
            int[] length = new int[1];
            gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, length, 0);
            
            byte[] log = new byte[length[0]];
            gl.glGetShaderInfoLog(shader, length[0], length, 0, log, 0);
            
            String error = new String(log, 0, length[0]);
            System.err.println(error);
        }
        
        return shader;
    }
    
    public static int loadProgram(GL3 gl, String vertexShaderFile, String fragmentShaderFile){
       
        try{// load vertex and fragment shaders (GLSL)
	int vs = loadShader(gl, vertexShaderFile, GL_VERTEX_SHADER);
	int fs = loadShader(gl, fragmentShaderFile, GL_FRAGMENT_SHADER);
        
	// create GLSL program, attach shaders and compile it
	int program = gl.glCreateProgram();
	gl.glAttachShader(program, vs);
	gl.glAttachShader(program, fs);
	gl.glLinkProgram(program);
        
        int[] linkStatus = new int[1];
        gl.glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == GL_FALSE) {
            int[] length = new int[1];
            gl.glGetProgramiv(program, GL_INFO_LOG_LENGTH, length, 0);
            
            byte[] log = new byte[length[0]];
            gl.glGetProgramInfoLog(program, length[0], length, 0, log, 0);
            
            String error = new String(log, 0, length[0]);
            System.err.println(error);
        }
        
        return program;
        }catch (IOException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return -1;
    }
    
    public static String readAllFromResource(String resource) throws IOException {

        InputStream is = Scene.class.getResourceAsStream(resource);
        if (is == null) {
            throw new IOException("Resource not found: " + resource);
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        
        int c;
        while ((c = reader.read()) != -1) {
            sb.append((char) c);
        }
        
        return sb.toString();
    }
    
    public static Geometry loadGeometry(GL3 gl, int program, String path){
        //ObjLoader model = new ObjLoader("/resources/models/teapot-high.obj");
        ObjLoader model = new ObjLoader(path);
        try {
            model.load();
        } catch (IOException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        // get cube program attributes
        int positionAttribLoc = gl.glGetAttribLocation(program, "position");
        int normalAttribLoc = gl.glGetAttribLocation(program, "normal");
        int textureAttribLoc = gl.glGetAttribLocation(program, "tex_coord");
        // create geometry
        return Geometry.create(gl, model, positionAttribLoc, normalAttribLoc,textureAttribLoc);
    }

    public static Texture loadTexture(GL3 gl, String path, String suffix) {
        URL url = Scene.class.getResource(path);
        try {
            TextureData data = TextureIO.newTextureData(gl.getGLProfile(), url, false, suffix);
            Texture texture = new Texture(gl, data);
            return texture;
        } catch (IOException ex) {
            System.err.println("File not found");
        }
        return null;
    }
    
}
