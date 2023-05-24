package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import game.Game.STATE;

/**
 * Responsible for the HUD and the display.
 * 
 * @author Nicholas Cuttle
 *
 */
public class HUD {

    private int level = 1;
    private int fontSize = 25;
    private int xFont = 50;
    private int yFont = 700;
    private String levelString = "I";

    /**
     * creates a new HUD object.
     * 
     * @param handler
     *            handler object to be passed.
     */
    public HUD() {

    }


    /**
     * no current implementation.
     */
    public void tick() {

    }


    /**
     * renders both health bars and the round text.
     * 
     * @param g
     *            Graphics object for the game.
     */
    public void render(Graphics g) {
        if (Game.player != null && Game.gameState == Game.STATE.Game) {
            g.setColor(Color.WHITE);
            // player1
            g.fillRect(15, 15, 200, 32);
            // player2
            g.setColor(Color.RED);
            // player 1
            g.fillRect(15, 15, 2 * Game.player.getHealth(), 32);
            // player 2
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", 1, fontSize));
            g.drawString(levelString, xFont, yFont);
            g.drawString("Highscore: " + Game.highScore, 0, 100);

            if (Game.player != null) {
                g.setFont(new Font("Courier New", 1, 15));
                g.setColor(Color.black);
                g.drawString("Kills: " + Game.player.getKills(), 250, 50);
                g.setColor(Color.yellow);
                g.drawString("Money: $" + Game.player.getMoney(), 250, 100);

                if (Game.player.getCurrentGun() != null) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Courier New", 1, 30));
                    g.drawString(Game.player.getCurrentGun().getClip() + " : "
                        + Game.player.getCurrentGun().getAmmo(), 1250, 750);
                }
            }
        }

    }


    /**
     * gets the current level
     * 
     * @return
     *         the current level.
     * 
     */
    public int getLevel() {
        return level;
    }


    /**
     * sets the current level
     * 
     * @param level
     *            level to be set to.
     */
    public void setLevel(int level) {
        this.level = level;
    }


    /**
     * returns x location of round font.
     * 
     * @return
     *         x location of round font.
     */
    public int getXFont() {
        return xFont;
    }


    /**
     * returns y location of round font.
     * 
     * @return
     *         y location of round font.
     */
    public int getYFont() {
        return yFont;
    }


    /**
     * set x location of Font
     * 
     * @param font
     *            x location to be set to.
     */
    public void setXFont(int font) {
        this.xFont = font;
    }


    /**
     * set y location of round font.
     * 
     * @param font
     *            y location to be set to.
     */
    public void setYFont(int font) {
        this.yFont = font;
    }


    /**
     * returns the level string itself.
     * 
     * @return
     *         the level string.
     */
    public String getLevelString() {
        return this.levelString;
    }


    /**
     * change the level string
     * 
     * @param name
     *            what to set the level string to.
     */
    public void setLevelString(String name) {
        this.levelString = name;
    }


    /**
     * set the font size.
     * 
     * @param size
     *            size of the new font.
     */
    public void setFontSize(int size) {
        this.fontSize = size;
    }


    /**
     * return font size
     * 
     * @return
     *         returns the font size.
     */
    public int getFontSize() {
        return fontSize;
    }

}
