package game.com.anish.calabashbros;

import java.io.Serializable;

public class World implements Serializable {

    private static final long serialVersionUID = 2L;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private Tile<Thing>[][] tiles;

    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < World.WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
            }
        }

        for(int i = 0; i < World.WIDTH; ++i) {
            for(int j = 0; j < 2; ++j) {
                tiles[i][j].setThing(new Wall(this));
            }
        }

        for(int i = 0; i < World.WIDTH; ++i) {
            for(int j = 2; j < World.HEIGHT; ++j) {
                tiles[i][j].setThing(new Floor(this));
            }
        }

        for(int i = 0; i < 3; ++i) {
            tiles[i][0].setThing(new Heart(this));
        }

        setScore(0);
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public void clear(int x, int y) {
        this.tiles[x][y].setThing(new Floor(this));
    }

    public void setScore(int score) {
        for(int i = 0; i < 4; ++i) {
            int d = score % 10;
            score = score / 10;
            put(new Digit(this, (char)(d+'0')), World.WIDTH - 1 - i, 0);
        }
    }

    public void win() {
        put(new Digit(this, 'Y'), 6, 1);
        put(new Digit(this, 'O'), 7, 1);
        put(new Digit(this, 'U'), 8, 1);
        put(new Digit(this, 'W'), 10, 1);
        put(new Digit(this, 'I'), 11, 1);
        put(new Digit(this, 'N'), 12, 1);
        put(new Digit(this, '!'), 13, 1);
    }

    public void lose() {
        put(new Digit(this, 'Y'), 6, 1);
        put(new Digit(this, 'O'), 7, 1);
        put(new Digit(this, 'U'), 8, 1);
        put(new Digit(this, 'L'), 10, 1);
        put(new Digit(this, 'O'), 11, 1);
        put(new Digit(this, 'S'), 12, 1);
        put(new Digit(this, 'E'), 13, 1);
        put(new Digit(this, '!'), 14, 1);
    }

}
