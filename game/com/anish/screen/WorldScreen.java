package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import asciiPanel.AsciiPanel;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;

public class WorldScreen implements Screen {

    private World world;
    private Calabash bro;
    private ExecutorService exec = Executors.newCachedThreadPool();

    public WorldScreen() {
        world = new World();

        bro = new Calabash(new Color(204, 0, 0), world, exec);

        world.put(bro, 10, 11);

        new Thread(bro).start();

        //new Thread(new MonGenerator(world, bro)).start();
        exec.execute(new MonGenerator(world, bro));

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

        bro.setKey(key);

        return this;
    }

}
