package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class RubyMaterial extends Material {
    private static Material instance;

    public static Material getInstance(){
        if(instance == null){
            instance = new RubyMaterial();
        }
        return instance;
    }

    private RubyMaterial(){
        super(new Vec3(0.1745f,0.01175f,0.01175f),
                new Vec3(0.61424f,0.04136f,0.04136f),
                new Vec3(0.727811f,0.626959f,0.626959f),
                0.6f);
    }
}
