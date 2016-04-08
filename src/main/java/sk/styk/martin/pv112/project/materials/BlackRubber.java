package sk.styk.martin.pv112.project.materials;

import com.hackoeur.jglm.Vec3;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class BlackRubber extends Material {
    private static Material instance;

    public static Material getInstance(){
        if(instance == null){
            instance = new BlackRubber();
        }
        return instance;
    }

    private BlackRubber(){
        super(new Vec3(0.02f,0.02f,0.02f),
                new Vec3(0.01f,0.01f,0.01f),
                new Vec3(0.40f,0.40f,0.40f),
                0.78215f);
    }
}
