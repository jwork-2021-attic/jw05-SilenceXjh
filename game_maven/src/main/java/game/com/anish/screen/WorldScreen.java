package game.com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.asciiPanel.AsciiPanel;
import game.com.anish.calabashbros.*;

public class WorldScreen implements Screen, Serializable {

    private static final long serialVersionUID = 1L;
    private World world;
    private Calabash bro;
    private MonGenerator mongen;
    private boolean isPaused = false;

    public WorldScreen() {
        world = new World();

        bro = new Calabash(new Color(204, 0, 0), world);

        world.put(bro, 10, 11);

        mongen = new MonGenerator(world, bro);

        new Thread(bro).start();

        //new Thread(new MonGenerator(world, bro)).start();
        new Thread(mongen).start();

    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        //System.out.println("respond");

        //judgePause(key);

        bro.setKey(key);

        return this;
    }

    

}
