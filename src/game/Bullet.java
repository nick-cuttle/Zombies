package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Deals with the handling of bullet objects.
 * 
 * @author Nicholas Cuttle
 *
 */
public class Bullet extends GameObject {

    private int lifeTime = 85;
    private int xTo;
    private int yTo;
    private Random random;
    private double angle;
    private int velocity;
    private int size, hitCount;
    private Guns shotFrom;

    /**
     * 
     * Creates a new bullet object.
     * 
     * @param x
     *            x coord to spawn at.
     * @param y
     *            y coord to spawn at.
     * @param id
     *            id of the bullet.
     * @param handler
     *            handler object for game.
     * @param xTo
     *            x to travel to
     * @param yTo
     *            y to travel to.
     */
    public Bullet(int x, int y, int xTo, int yTo, Guns obj, int vel, int size) {
        super(x, y, ID.Bullet);
        this.xTo = xTo;
        this.yTo = yTo;
        velocity = vel;
        shoot(xTo, yTo);
        random = new Random();
        shotFrom = obj;
        image = obj.bulletImage;
        this.size = size;
        hitCount = 0;
        canCollide = true;

    }


    /**
     * deals with how the bullet moves and removes
     * it eventually.
     */
    public void tick() {
        x += velX;
        y += velY;
        collide();
        lifeTime--;
        if (lifeTime <= 0) {
            Game.handler.removeObject(this);
        }
    }


    /**
     * renders bullet on screen
     */
    public void render(Graphics g) {
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(angle);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(image, at, null);
    }


    /**
     * deals with collision with bullet.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }


    /**
     * how the bullet is shot.
     * 
     * @param xTo
     *            x coord to travel to.
     * @param yTo
     *            y coord to travel to.
     */
    public void shoot(int xTo, int yTo) {

        double xDiff = Math.pow(xTo - x, 2);
        double yDiff = Math.pow(yTo - y, 2);
        double distance = Math.sqrt(xDiff + yDiff);
        double angle = Math.acos((Math.abs(xTo - x)) / distance);

        this.angle = angle;

        if (xTo - x >= 0 && yTo - y <= 0) {
            this.angle = -this.angle;
        }

        else if (xTo - x <= 0 && yTo - y >= 0) {
            double d = (60) * Math.asin((Math.abs(xTo - x)) / distance);
            System.out.println(d);
            this.angle = Math.toRadians(90 + d);
        }

        else if (xTo - x <= 0 && yTo - y <= 0) {
            double d = (60) * Math.asin((Math.abs(xTo - x)) / distance);
            this.angle = Math.toRadians(-90 - d);

        }

        if (xTo - x < 0) {
            this.velX = -(velocity * Math.cos(angle));
        }
        else {
            this.velX = (velocity * Math.cos(angle));
        }
        if (yTo - y < 0) {
            this.velY = -(velocity * Math.sin(angle));
        }
        else {
            this.velY = (velocity * Math.sin(angle));
        }

    }


    /**
     * detects collision with zombie and kills if so.
     */
    private boolean collide() {
        for (int i = 0; i < Game.handler.object.size(); i++) {
            GameObject tempObject = Game.handler.object.get(i);

            if (tempObject.getId() == ID.BasicZombie) {
                BasicZombie zombie = (BasicZombie)tempObject;
                if (zombie.getBeenShot() != true && getBounds().intersects(
                    tempObject.getBounds())) {

                    zombie.setHealth(zombie.getHealth() - Game.player
                        .getCurrentGun().damage);
                    hitCount++;

                    if (Game.player != null && zombie.getHealth() <= 0) {
                        Game.player.setMoney(Game.player.getMoney() + 10);
                        Game.player.setKills(Game.player.getKills() + 1);
                        ImageIcon icon = new ImageIcon("zomb_death.gif");
                        Image img = icon.getImage();
                        zombie.setImage(img);
                        zombie.setBeenShot(true);
                        zombie.setWidth(70);
                        determineDrop(tempObject);
                    }

                    if (hitCount >= 3 && shotFrom.getClass() == Raygun.class) {
                        Game.handler.removeObject(this);
                    }
                    else if (hitCount == 1 && shotFrom.getClass() != Raygun.class)
                        Game.handler.removeObject(this);

                    return true;
                }
            }

        }
        return false;
    }


    private void determineDrop(GameObject obj) {

        int chance = random.nextInt(100) + 1;
        if (chance <= 1) {
            int x = obj.getX();
            int y = obj.getY();
            Game.handler.addObject(new MaxAmmo(x, y));
            Music.playSound(new File("loot_appears.wav"));
        }
        else if (chance > 1 & chance <= 2) {
            Game.handler.addObject(new InstaKill(x, y));
            Music.playSound(new File("loot_appears.wav"));
        }
    }


    public void setVelocity(int vel) {
        velocity = vel;
    }


    public void setSize(int s) {
        size = s;
        image = image.getScaledInstance(size, size, Image.SCALE_DEFAULT);
    }


    public int getSize() {
        return size;
    }


    public void setLifeTime(int life) {
        lifeTime = life;
    }

}
