package sk.styk.martin.pv112.project.textures;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.Texture;
import sk.styk.martin.pv112.project.tooling.LoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Styk on 08.04.2016.
 */
public class TexturesFactory {

    private static TexturesFactory instance;

    private GL3 gl;

    public enum Types {
        WOOD, ROCKS, DICE1, DICE2, DICE3, DICE4, DICE5, DICE6, WALL, CARPET, WALL_COVERING, PHOTO_PERSONAL, PARQUET, RUBIC1, RUBIC2, RUBIC3, RUBIC4, RUBIC5, RUBIC6,
        CLOCK_HAND, CLOCK
    }

    private Texture wood;
    private Texture rocks;
    private List<Texture> dice;
    private List<Texture> rubic;
    private Texture wall;
    private Texture carpet;
    private Texture wallCovering;
    private Texture photoPersonal;
    private Texture parquet;
    private Texture clock;
    private Texture clockHand;

    protected TexturesFactory(GL3 gl) {
        this.gl = gl;
    }

    public static TexturesFactory getInstance(GL3 gl) {
        if (instance == null) {
            instance = new TexturesFactory(gl);
        }
        instance.gl = gl;
        return instance;
    }

    public Texture get(Types type) {
        Texture res1 = checkDice(type);
        Texture res2 = checkRubic(type);
        if (res1 != null) return res1;
        if (res2 != null) return res2;

        switch (type) {
            case WOOD:
                if (wood == null) {
                    wood = LoadUtils.loadTexture(gl, WoodTexture.getPath(), WoodTexture.getType());
                }
                return wood;
            case ROCKS:
                if (rocks == null) {
                    rocks = LoadUtils.loadTexture(gl, RockTexture.getPath(), RockTexture.getType());
                }
                return rocks;
            case WALL:
                if (wall == null) {
                    wall = LoadUtils.loadTexture(gl, WallTexture.getPath(), WallTexture.getType());
                }
                return wall;
            case CARPET:
                if (carpet == null) {
                    carpet = LoadUtils.loadTexture(gl, CarpetTexture.getPath(), CarpetTexture.getType());
                }
                return carpet;
            case WALL_COVERING:
                if (wallCovering == null) {
                    wallCovering = LoadUtils.loadTexture(gl, WallCovering.getPath(), WallCovering.getType());
                }
                return wallCovering;
            case PHOTO_PERSONAL:
                if (photoPersonal == null) {
                    photoPersonal = LoadUtils.loadTexture(gl, PersonPictureTexture.getPath(), PersonPictureTexture.getType());
                }
                return photoPersonal;
            case PARQUET:
                if (parquet == null) {
                    parquet = LoadUtils.loadTexture(gl, ParquetTexture.getPath(), ParquetTexture.getType());
                }
                return parquet;
            case CLOCK:
                if (clock == null) {
                    clock = LoadUtils.loadTexture(gl, ClockTexture.getPath(), ClockTexture.getType());
                }
                return clock;
            case CLOCK_HAND:
                if (clockHand == null) {
                    clockHand = LoadUtils.loadTexture(gl, ClockHandTexture.getPath(), ClockHandTexture.getType());
                }
                return clockHand;
            default:
                throw new IllegalArgumentException("Texture type does not exist");
        }
    }

    private Texture checkDice(Types type) {
        if (!type.toString().toLowerCase().contains("dice")) {
            return null;
        }
        if (dice == null || dice.isEmpty()) {
            dice = new ArrayList<>();
            for (int i = 1; i < 7; i++) {
                dice.add(LoadUtils.loadTexture(gl, DiceTexture.getPath(i), DiceTexture.getType()));
            }
        }
        String last = type.toString().substring(4);
        int i = Integer.parseInt(last) - 1;
        return dice.get(i);
    }

    private Texture checkRubic(Types type) {
        if (!type.toString().toLowerCase().contains("rubic")) {
            return null;
        }
        if (rubic == null || rubic.isEmpty()) {
            rubic = new ArrayList<>();
            for (int i = 1; i < 7; i++) {
                rubic.add(LoadUtils.loadTexture(gl, RubicTexture.getPath(i), RubicTexture.getType()));
            }
        }
        String last = type.toString().substring(5);
        int i = Integer.parseInt(last) - 1;
        return rubic.get(i);
    }

}
