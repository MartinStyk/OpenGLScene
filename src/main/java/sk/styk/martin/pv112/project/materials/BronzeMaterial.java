package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class BronzeMaterial extends Material {
    private static Material instance;

    public static Material getInstance(){
        if(instance == null){
            instance = new BronzeMaterial();
        }
        return instance;
    }

    private BronzeMaterial(){
        super(new Vec3(0.2125f,0.1275f,0.054f),
                new Vec3(0.714f,0.4284f,0.18144f),
                new Vec3(0.393548f,0.271906f,0.166721f),
                0.20f);
    }
}
