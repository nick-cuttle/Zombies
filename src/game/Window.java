package game;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * Class to set up the displayable window for game.
 * 
 * @author Nicholas Cuttle
 *
 *
 */
public class Window extends Canvas {

    /**
     * sets up the window.
     * 
     * @param width
     *            width of window.
     * @param height
     *            height of window.
     * @param title
     *            title of the window.
     * @param game
     *            game object.
     */
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

}
