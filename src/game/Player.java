package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * Class representing the playable characters.
 * 
 * @author Nicholas Cuttle
 *
 */
public class Player extends GameObject {

    private int kills, width;
    private Guns[] inventory;
    private int health, money;
    private Guns currentGun;
    private int gunIndex;
    private boolean colliding;
    public static Facing direction;
    private Hand hand;

    /**
     * creates a new player object
     * 
     * @param x
     *            x coord to spawn at
     * @param y
     *            y coord to spawn at
     * @param id
     *            id of the player
     * @param handler
     *            handler object to deal with all objects.
     */
    public Player(int x, int y) {
        super(x, y, ID.Player);
        kills = 0;
        width = 60;
        hand = new Hand(x, y);
        Game.handler.addObject(hand);

        ImageIcon i = new ImageIcon("player.gif");
        image = i.getImage();

        inventory = new Guns[2];
        //Glock startGun = new Glock(x, y);
        Raygun startGun = new Raygun(x, y);
        startGun.setGrabbed(true);
        inventory[0] = startGun;
        Game.handler.addObject(startGun);
        inventory[1] = null;
        health = 100;
        currentGun = inventory[0];
        gunIndex = 0;
        colliding = false;
        direction = Facing.RIGHT;
        money = 0;
        Dog pug = new Dog(x, y);
        Game.handler.addObject(pug);
        width = 45;
        height = 60;
    }

    public enum Facing {
        LEFT, RIGHT;
    }
    
    public void setMoney(int m) {
        money = m;
    }
    
    public int getMoney() {
        return money;
    }

    /**
     * rectangle following player dealing with collision detection
     * 
     * @return rectangle following player.
     */
    public Rectangle getBounds() {
        return new Rectangle(x - 20, y, width, height);
    }


    public Hand getHand() {
        return hand;
    }


    /**
     * deals with collision, setting velocities, and ensuring
     * player cannot leave the map.
     */
    public void tick() {
        int xC = this.x;
        int yC = this.y;
        

        x += velX;
        y += velY;
        
        x = Game.clamp(x, 0, Game.WIDTH - 44);
        y = Game.clamp(y, 0, Game.HEIGHT - 66);

        collision();
    }
    
    public boolean collideWithObject() {
        for (int i = 0; i < Game.handler.object.size(); i++) {
            GameObject temp = Game.handler.object.get(i);
            if (temp != this && !temp.canCollide) {
                if (getBounds().intersects(temp.getBounds())) {
                    return true;
                }
            }
            
        }
        return false;
    }
    
    public void fullCollision() {
        for (int i = 0; i < Game.handler.object.size(); i++) {
            GameObject temp = Game.handler.object.get(i);
            if (temp != this) {
                if (getBounds().intersects(temp.getBounds())) {
                    int x = temp.getX();
                    int y = temp.getY();
                    int pX = this.x - 20;
                    if ( pX + width > x && pX + width < x + temp.getWidth() ) {
                        velX = 0;
                    }
                    if (pX > x && pX < x + temp.getWidth()) {
                        velX = 0;
                    }
                    if (this.y < y + temp.getHeight() && this.y > y) {
                        velY = 0;
                    }
                    if (this.y + height > y && this.y + height < y + temp.getHeight()) {
                        velY = 0;
                    }
                    
                    
                    
                }
            }
        }
    }


    /**
     * detects when a zombie has collided with a player.
     */
    private void collision() {
        for (int i = 0; i < Game.handler.object.size(); i++) {
            GameObject tempObject = Game.handler.object.get(i);

            if (tempObject.getId() == ID.BasicZombie) {
                BasicZombie zombie = (BasicZombie)tempObject;
                if (zombie.getBeenShot() != true && this.getBounds().intersects(
                    tempObject.getBounds())) {
                    Game.handler.removeObject(tempObject);
                    this.health -= 34;
                    Music.playSound(new File("steve.wav"));
                }

            }

        }
    }
    
    /**
     * renders the graphics for both players.
     */
    public void render(Graphics g) {
        
        if (velX == 0 && velY == 0) {
            ImageIcon i = new ImageIcon("player_stop.gif");
            image = i.getImage();
        }
        else {
            ImageIcon i = new ImageIcon("player.gif");
            image = i.getImage();
        }
        
        g.drawImage(image, x - (flipPlayer() / 2), y, flipPlayer(), height, null);
        Color myColour = new Color(255, 100, 125, 125);
        g.setColor(myColour);

        //g.fillRect(x - 20, y, width, height);
    }


    public int flipPlayer() {
        int w = Math.abs(width);

        if (MouseInfo.getPointerInfo().getLocation().getX() - x < 0) {
            direction = Facing.LEFT;
            return -w;
        }
        else {
            direction = Facing.RIGHT;
            return w;
        }
    }


    /**
     * checks if the player is currently alive.
     * 
     * @return
     *         True if player is still alive.
     */

    /**
     * set the state whether a player is alive.
     * 
     * @param aLive
     *            state of what the player is.
     */

    public void setKills(int kills) {
        this.kills = kills;
    }


    public int getKills() {
        return kills;
    }


    public Guns[] getInventory() {
        return inventory;
    }


    public int getHealth() {
        return health;
    }


    public void setHealth(int h) {
        this.health = h;
    }


    public void setCurrentGun(int index) {
        if (index >= 0 && index <= 1) {
            currentGun = inventory[index];
        }
    }


    public Guns getCurrentGun() {
        return currentGun;
    }


    public void setGunIndex(int index) {
        gunIndex = index;
    }


    public int getGunIndex() {
        return gunIndex;
    }


    public void setColliding(boolean c1) {
        colliding = c1;
    }


    public int getWidth() {
        return width;
    }

    public class Hand extends GameObject {

        public Hand(int x, int y) {
            super(x, y, ID.Hand);
            canCollide = true;
        }
        
        @Override
        public void tick() {
            if (Game.player != null) {
                if (Game.player.direction == Facing.LEFT) {
                    x = Game.player.x - 10;
                }
                else {
                    x = Game.player.x + 10;
                }
                y = Game.player.y + 30;
            }
        }


        @Override
        public void render(Graphics g) {
            g.drawRect(x, y, 5, 5);
        }


        @Override
        public Rectangle getBounds() {
            return new Rectangle(x, y, 5, 5);
        }

    }

}
