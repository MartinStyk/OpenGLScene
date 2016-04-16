/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.styk.martin.pv112.project.programs;

import com.jogamp.opengl.GL3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Styk
 */
public class UniformManager {

    private GL3 gl;
    private int program;

    private Map<String, Integer> data;

    public UniformManager(Program program) {
        if (program == null) {
            throw new IllegalArgumentException("program is null");
        }
        this.gl = program.getGL();
        this.program = program.getID();
        this.data = new HashMap<String, Integer>();
    }

    public int getUniformLocation(String name) {
        return data.get(name);
    }

    public void addUniformLocation(String name) {
        Object result = data.put(name, gl.glGetUniformLocation(program, name));
        if (result != null) {
            throw new RuntimeException("Uniform already defined. Name: " + name + "Program ID: " + program);
        }
    }

}
