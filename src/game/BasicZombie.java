package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Basic Zombie enemy for game
 * 
 * @author Nicholas Cuttle
 *
 */
public class BasicZombie extends GameObject {
    private double velocity;
    private Random random = new Random();
    private Image image2;
    private boolean beenShot;
    private int timeSinceDeath;
    private int directionOfDeath, health;
    public static int zombieHealth = 10;

    /**
     * creates new zombie object
     * 
     * @param x
     *            x coord to spawn at
     * @param y
     *            y coord to spawn at.
     * @param id
     *            id of the zombie
     * @param handler
     *            handler object for the game.
     */
    public BasicZombie(int x, int y, ID id) {
        super(x, y, id);
        velocity = random.nextDouble() + 3;
        beenShot = false;
        timeSinceDeath = 0;
        ImageIcon i = new ImageIcon("zombie.gif");
        image = i.getImage();
        width = 60;
        height = 40;
        ImageIcon icon = new ImageIcon("z_death2.gif");
        image2 = icon.getImage();

        health = zombieHealth;
    }


    /**
     * updates zombie each game tick.
     */
    public void tick() {
        findClosestPlayer();
        if (beenShot) {
            timeSinceDeath++;
            directionOfDeath(timeSinceDeath);
            velX = 0;
            velY = 0;
        }
        x += velX;
        y += velY;
        
        if (beenShot || timeSinceDeath > 45) {
            Game.handler.removeObject(this);
        }
    }


    /**
     * rectangle dealing with collision.
     */
    public Rectangle getBounds() {
        return new Rectangle(x - 8, y + 20, Math.abs(width) - 45, height);
    }


    public void setHealth(int h) {
        health = h;
    }


    public int getHealth() {
        return health;
    }


    /**
     * NOT FINISHED.
     */
    private boolean collision() {
        for (int i = 0; i < Game.handler.object.size(); i++) {
            GameObject tempObject = Game.handler.object.get(i);

            if (tempObject.getId() == ID.BasicZombie) {
                BasicZombie zombie = (BasicZombie)tempObject;
                if (this != zombie && this.getBounds().intersects(zombie
                    .getBounds())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines how the zombie will move.
     * 
     * @param angle
     *            angle to travel at.
     * @param cx
     *            closest player's x.
     * @param cy
     *            closest player's y.
     */
    public void move(double angle, int cx, int cy) {

        if (cx - this.getX() < 0) {
            this.velX = -(this.velocity * Math.cos(angle));
        }
        else {
            this.velX = (this.velocity * Math.cos(angle));
        }
        if (cy - this.getY() < 0) {
            this.velY = -(this.velocity * Math.sin(angle));
        }
        else {
            this.velY = (this.velocity * Math.sin(angle));
        }

    }


    /**
     * finds the closest player and calculates the distance and
     * angle between
     * 
     * @return
     *         The closest player.
     */
    public void findClosestPlayer() {
        // finding the closest player.

        int zX = this.getX() + 8;
        int zY = this.getY() + 8;

        double distance = 100000;

        double part1 = (Math.pow(zX - (Game.player.getX() + 16), 2)) + (Math
            .pow(zY - (Game.player.getY() + 16), 2));
        distance = Math.sqrt(part1);

        // determining the distance and angle of player.
        double distX = Math.sqrt(Math.pow((Game.player.getX() + 16) - (this
            .getX() + 8), 2));
        double angle = Math.acos(distX / distance);
        move(angle, Game.player.getX(), Game.player.getY());
    }


    /**
     * renders the zombie object.
     */
    public void render(Graphics g) {
        int width = this.width;
        if (!beenShot && this.getVelX() <= 0) {
            width = -width;
        }
        if (!beenShot) {
            g.drawImage(image, x - (width / 2), y, width, height + 20, null);
        }
        else {

            g.drawImage(image2, x - (width / 2), y, directionOfDeath * width,
                height + 20, null);
        }
        g.setColor(Color.WHITE);
        g.drawRect(x - 15, y + 10, 30, 5);
        g.setColor(Color.RED);
        g.fillRect(x - 15, y + 10, boxSize(), 5);
        //g.drawRect(x - 8, y + 20, Math.abs(width) - 45, height);

    }


    private int boxSize() {
        double d = (double)health / zombieHealth;
        return (int)(30 * d);
    }


    public void setImage(Image i) {
        this.image = i;
    }


    public void setBeenShot(boolean shot) {
        this.beenShot = shot;
    }


    public boolean getBeenShot() {
        return this.beenShot;
    }


    public void setWidth(int w) {
        this.width = w;
    }


    public void directionOfDeath(int time) {
        if (time == 1 & velX > 0) {
            directionOfDeath = 1;
        }
        else if (time == 1) {
            directionOfDeath = -1;
        }
    }

}
