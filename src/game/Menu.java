package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import game.Game.STATE;

/**
 * Deals with setting up the menu
 * 
 * @author Nicholas Cuttle
 *
 */
public class Menu extends MouseAdapter {

    private Game game;

    /**
     * creates new menu object.
     * 
     * @param game
     *            the game object.
     * @param handler
     *            the handler object.
     */
    public Menu(Game game) {
        this.game = game;
    }


    /**
     * renders the menu
     * 
     * @param g
     *            the graphics object for game.
     */
    public void render(Graphics g) {
        Font font = new Font("arial", 1, 50);
        Font font2 = new Font("arial", 1, 30);

        g.setFont(font);
        g.setColor(Color.green.brighter());
        g.drawString("Zombies!", 580, 75);

        g.setColor(Color.white);
        g.setFont(font2);
        g.drawRect(525, 150, 350, 80);
        g.drawString("Singleplayer", 600, 200);

        g.drawRect(525, 350, 350, 80);
        g.drawString("Multiplayer", 625, 400);

        g.drawRect(525, 550, 350, 80);
        g.drawString("Quit", 665, 600);
    }


    /**
     * unimplemented.
     */
    public void tick() {

    }


    /**
     * determines if mouse is over button
     * 
     * @param mx
     *            x mouse position
     * @param my
     *            y mouse position
     * @param x
     *            x coord of button
     * @param y
     *            y coord of button
     * @param width
     *            width of button
     * @param height
     *            height of button
     * @return
     *         True if mouse is within a button.
     */
    private boolean mouseOver(
        int mx,
        int my,
        int x,
        int y,
        int width,
        int height) {
        if (mx > x && mx < x + width)
            if (my > y && my < y + height) {
                return true;
            }
        return false;
    }


    /**
     * deals with mouse clicking detection and functionality
     * of buttons.
     */
    public void mousePressed(MouseEvent m) {
        int mx = m.getX();
        int my = m.getY();
        // singleplayer button detection.
        if (Game.gameState == STATE.Menu && mouseOver(mx, my, 525, 150, 350,
            80)) {
            singleplayer();
        }
        // quit button detection.
        else if (Game.gameState == STATE.Menu && mouseOver(mx, my, 525, 550,
            350, 80)) {
            System.exit(0);
        }
    }


    /**
     * sets up singleplayer game status.
     */
    private void singleplayer() {
        Game.player = new Player(200, 100);
        Game.handler.addObject(Game.player);
        Music.playSound(new File("round_start.wav"));
        Game.mode = "Singleplayer";
        Game.gameState = STATE.Game;
        Game.handler.addObject(Game.mysteryBox);
    }


    /**
     * unimplemented.
     */
    public void mouseReleased(MouseEvent m) {

    }
}
