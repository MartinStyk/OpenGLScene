package sk.styk.martin.pv112.project;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Martin Styk on 12.04.2016.
 */
public class CubeRandomRotate {

    private Map<Integer, Float> map;

    public CubeRandomRotate(int number) {
        map = new HashMap<Integer, Float>();
        for (int i = 0; i < number; i++) {
            map.put(i, createRandom());
        }
    }

    public Float getRandom(int i){
        return map.get(i);
    }

    private float createRandom() {
        Random rand = new Random();
        float result = rand.nextFloat() * 0.2f;
        if (System.currentTimeMillis() % 2 == 0) {
            return result;
        } else {
            return -1 * result;
        }
    }
}
