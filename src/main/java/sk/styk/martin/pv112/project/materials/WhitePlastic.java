package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class WhitePlastic extends Material {
    private static Material instance;

    public static Material getInstance(){
        if(instance == null){
            instance = new WhitePlastic();
        }
        return instance;
    }

    private WhitePlastic(){
        super(new Vec3(0.0f,0.0f,0.0f),
                new Vec3(0.55f,0.55f,0.55f),
                new Vec3(0.70f,0.70f,0.70f),
                0.25f);
    }
}
