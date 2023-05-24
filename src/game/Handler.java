package game;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * deals with handling all objects in the game.
 * 
 * @author Nicholas Cuttle
 *
 */
public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>();

    /**
     * for each object has them do a specified task.
     */
    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }


    /**
     * renders each object.
     * 
     * @param g
     *            graphic object for the game.
     */
    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }


    /**
     * adds an object to the game.
     * 
     * @param object
     *            object to add to game.
     */
    public void addObject(GameObject object) {
        this.object.add(object);
    }


    /**
     * remove object from the game.
     * 
     * @param object
     *            object to remove.
     */
    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
    

}
