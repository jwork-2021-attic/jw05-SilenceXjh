package com.anish.calabashbros;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;

public class Calabash extends Creature {

    private int score = 0;

    private int lives = 3;

    KeyEvent key = null;

    ExecutorService exec;

    public Calabash(Color color, World world, ExecutorService exec) {
        super(color, (char) 2, world);
        this.exec = exec;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    @Override
    public void run() {
        while (true) {

            if (lives <= 0) {
                world.clear(getX(), getY());
                world.lose();
                System.out.println("Dead");
                //exec.shutdownNow();
                return;
            }
            if(score >= 2000) {
                world.win();
                exec.shutdown();
                return;
            }
            System.out.print("");
            int l = getLevel();
            if(l == 1) {
                color = new Color(255, 165, 0);
            } 
            else if(l == 2) {
                color = new Color(252, 233, 79);
            }
            else if(l == 3) {
                color = new Color(78, 154, 6);
            }
            else if(l == 4) {
                color = new Color(50, 175, 255);
            }
            else if(l == 5) {
                color = new Color(114, 159, 207);
            }
            else if(l >= 6) {
                color = new Color(173, 127, 168);
            }

            if (this.key != null) {
                // System.out.println("key");
                if (key.getKeyCode() == KeyEvent.VK_DOWN) {
                    // System.out.println("down");
                    if (getY() == World.HEIGHT - 1)
                        continue;
                    synchronized (world.get(getX(), getY() + 1)) {
                        Thing t = world.get(getX(), getY() + 1);
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                score += 20 * mon.getLevel() + 20;
                                world.setScore(score);
                                world.clear(getX(), getY());
                                world.clear(getX(), getY()+1);
                                moveTo(getX(), getY() + 1);
                                mon.die();
                            } else {
                                lives--;
                                world.put(new Wall(world), lives, 0);
                            }
                        } else {
                            world.clear(getX(), getY());
                            moveTo(getX(), getY() + 1);
                        }
                    }
                } else if (key.getKeyCode() == KeyEvent.VK_UP) {
                    if (getY() == 2)
                        continue;
                    synchronized (world.get(getX(), getY() - 1)) {
                        Thing t = world.get(getX(), getY() - 1);
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                score += 20 * mon.getLevel() + 20;
                                world.setScore(score);
                                mon.die();
                                world.clear(getX(), getY());
                                world.clear(getX(), getY()-1);
                                moveTo(getX(), getY() - 1);
                            } else {
                                lives--;
                                world.put(new Wall(world), lives, 0);
                            }
                        } else {
                            world.clear(getX(), getY());
                            moveTo(getX(), getY() - 1);
                        }
                    }
                } else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (getX() == 0)
                        continue;
                    synchronized (world.get(getX() - 1, getY())) {
                        Thing t = world.get(getX() - 1, getY());
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                score += 20 * mon.getLevel() + 20;
                                world.setScore(score);
                                mon.die();
                                world.clear(getX(), getY());
                                world.clear(getX()-1, getY());
                                moveTo(getX() - 1, getY());
                            } else {
                                lives--;
                                world.put(new Wall(world), lives, 0);
                            }
                        } else {
                            world.clear(getX(), getY());
                            moveTo(getX() - 1, getY());
                        }
                    }
                } else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (getX() == World.WIDTH - 1)
                        continue;
                    synchronized (world.get(getX() + 1, getY())) {
                        Thing t = world.get(getX() + 1, getY());
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                score += 20 * mon.getLevel() + 20;
                                world.setScore(score);
                                mon.die();
                                world.clear(getX(), getY());
                                world.clear(getX()+1, getY());
                                moveTo(getX() + 1, getY());
                            } else {
                                lives--;
                                world.put(new Wall(world), lives, 0);
                            }
                        } else {
                            world.clear(getX(), getY());
                            moveTo(getX() + 1, getY());
                        }
                    }
                }
                key = null;
            }

        }
    }

    public void setKey(KeyEvent key) {

        this.key = key;
        // System.out.println("set key");
        // System.out.println(key != null);
    }

    public int getLevel() {
        return score / 200;
    }

    public int getScore() {
        return score;
    }

    public void getHurt() {
        lives--;
        world.put(new Wall(world), lives, 0);
    }

    public void addScore(int score) {
        this.score += score;
        world.setScore(this.score);
    }
}
