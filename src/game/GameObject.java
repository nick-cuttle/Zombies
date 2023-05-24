package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * Abstract class designed to have basic methods
 * and definitions required for each object.
 * 
 * @author Nicholas Cuttle
 *
 */
public abstract class GameObject {

    protected int x, y;
    protected ID id;
    protected double velX, velY;
    protected Image image;
    protected boolean canCollide;
    protected int width;
    protected int height;

    /**
     * creates a new GameObject.
     * 
     * @param x
     *            x location of object.
     * @param y
     *            y location of object.
     * @param id
     *            id of the object.
     */
    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
        
    public Image getImage() {
        return image;
    }
    
    public GameObject clone(int x, int y, GameObject obj) {
        if (obj.getClass() == Shotgun.class) {
            return new Shotgun(x, y);
        }
        else if (obj.getClass() == Raygun.class) {
            return new Raygun(x, y);
        }
        else if (obj.getClass() == FlameThrower.class) {
            return new FlameThrower(x, y);
        }
        
        else if (obj.getClass() == Dog.class) {
            return new Dog(x, y);
        }
        
        
        return null;
    }


    /**
     * deals with how the object acts each tick.
     */
    public abstract void tick();


    /**
     * how an object will render on the screen.
     * 
     * @param g
     *            graphic object for the entire game.
     */
    public abstract void render(Graphics g);


    /**
     * deals with collision for each object.
     * 
     * @return
     *         Rectangle object dealing with collision.
     */
    public abstract Rectangle getBounds();


    /**
     * sets X coord for object.
     * 
     * @param x
     *            new x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }
    
    public int getWidth() {
        return width;
    }


    /**
     * sets y coord for object.
     * 
     * @param y
     *            new y coord.
     */
    public void setY(int y) {
        this.y = y;
    }
    
    public int getHeight() {
        return height;
    }


    /**
     * returns current x position.
     * 
     * @return
     *         the x position of object.
     */
    public int getX() {
        return x;
    }


    /**
     * returns current y position.
     * 
     * @return
     *         the y position of object.
     */
    public int getY() {
        return y;
    }


    /**
     * sets the id of the object.
     * 
     * @param id
     *            id to set to.
     */
    public void setId(ID id) {
        this.id = id;
    }


    /**
     * gets id of the object.
     * 
     * @return
     *         the id of object.
     */
    public ID getId() {
        return id;
    }


    /**
     * sets velocity in x direction for object.
     * 
     * @param vel
     *            new velocity in x.
     */
    public void setVelX(double vel) {
        this.velX = vel;
    }


    /**
     * sets velocity in y direction for object.
     * 
     * @param vel
     *            new velocity in y.
     */
    public void setVelY(double vel) {
        this.velY = vel;
    }


    /**
     * returns x velocity
     * 
     * @return
     *         the x velocity of object.
     */
    public double getVelX() {
        return velX;
    }


    /**
     * returns y velocity
     * 
     * @return
     *         the y velocity of object.
     */
    public double getVelY() {
        return velY;
    }

}
