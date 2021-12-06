package com.anish.calabashbros;

import java.awt.Color;

public class Monster extends Creature {

    private final int level;
    private final boolean left;
    private boolean exist = true;
    private Calabash bro;
    
    public Monster(Color color, World world, int level, boolean left, Calabash bro) {
        super(color, (char) 1, world);
        this.level = level;
        this.left = left;
        this.bro = bro;
    }

    public int getLevel() {
        return level;
    }

    public boolean getDirection() {
        return left;
    }

    public void die() {
        exist = false;
    }

    public void swap(Monster another) {
        int x = this.getX();
        int y = this.getY();
        this.moveTo(another.getX(), another.getY());
        another.moveTo(x, y);
    }

    @Override
    public void run() {
        while(true) {
            if(!exist) {
                if(tile.getThing() == this) {
                    world.clear(getX(), getY());
                    //System.out.println("clear");
                }
                return;
            }
            if(bro.isDead() || bro.getScore() >= 2000) {
                return;
            }
            try {
                Thread.sleep(500);
            }
            catch(InterruptedException e) {

            }
            if(left) {
                if(getX() == 0) {
                    world.clear(getX(), getY());
                    return;
                }
                synchronized(world.get(getX()-1, getY())) {
                    Thing t = world.get(getX()-1, getY());
                    if(t.getClass() == Calabash.class) {
                        Calabash c = (Calabash)t;
                        if(c.getLevel() >= level) {
                            c.addScore(20 * level + 20);
                            world.clear(getX(), getY());
                            return;
                        }
                        else {
                            c.getHurt();
                        }
                    }
                    else if(t.getClass() == Monster.class) {
                        Monster m = (Monster)t;
                        if(m.getDirection()) 
                            continue;
                        else {
                            swap(m);
                        }
                    }
                    else {
                        if(world.get(getX(), getY()).getClass() != Calabash.class)
                            world.clear(getX(), getY());
                        moveTo(getX()-1, getY());
                    }
                }
            }
            else {
                if(getX() == World.WIDTH - 1) {
                    world.clear(getX(), getY());
                    return;
                }
                synchronized(world.get(getX()+1, getY())) {
                    Thing t = world.get(getX()+1, getY());
                    if(t.getClass() == Calabash.class) {
                        Calabash c = (Calabash)t;
                        if(c.getLevel() >= level) {
                            c.addScore(20 * level + 20);
                            world.clear(getX(), getY());
                            return;
                        }
                        else {
                            c.getHurt();
                        }
                    }
                    else if(t.getClass() == Monster.class) {
                        Monster m = (Monster)t;
                        if(!m.getDirection()) 
                            continue;
                        else {
                            swap(m);
                        }
                    }
                    else {
                        if(world.get(getX(), getY()).getClass() != Calabash.class)
                            world.clear(getX(), getY());
                        moveTo(getX()+1, getY());
                    }
                }
            }
        }
    }
}