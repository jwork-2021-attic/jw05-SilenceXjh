package game.com.anish.calabashbros;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Calabash extends Creature implements Serializable {

    private static final long serialVersionUID = 4L;

    private int score = 0;

    private int lives = 3;

    KeyEvent key = null;

    public Calabash(Color color, World world) {
        super(color, (char) 200, world);
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
                return;
            }
            System.out.print("");
            int l = getLevel();
            if(l <= 7) {
                glyph = (char)(200+l);
            }

            if (this.key != null) {
                // System.out.println("key");
                if (key.getKeyCode() == KeyEvent.VK_DOWN) {
                    // System.out.println("down");
                    if (getY() == World.HEIGHT - 1) {
                        key = null;
                        continue;
                    }
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
                    if (getY() == 2) {
                        key = null;
                        continue;
                    }
                        
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
                    if (getX() == 0) {
                        key = null;
                        continue;
                    }
                        
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
                    if (getX() == World.WIDTH - 1) {
                        key = null;
                        continue;
                    }
                        
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
