package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class WallMaterial extends Material {
    private static Material instance;

    public static Material getInstance(){
        if(instance == null){
            instance = new WallMaterial();
        }
        return instance;
    }

    private WallMaterial(){
        super(new Vec3(0.1f,0.1f,0.1f),
                new Vec3(0.5f,0.5f,0.5f),
                new Vec3(0.10f,0.10f,0.10f),
                1f);
    }
}
