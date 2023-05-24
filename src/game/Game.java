package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.ImageIcon;

/**
 * Main class dealing with running the game.
 * 
 * @author Nicholas Cuttle
 *
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -4584388369897487885L;

    /**
     * width and height of the window.
     */
    public static final int WIDTH = 1440, HEIGHT = 810;

    private Thread thread;
    private boolean running = false;

    public static Handler handler;

    private HUD hud;
    private Menu menu;
    public static String mode = "";
    public static int highScore;
    public static Player player;
    public static MysteryBox mysteryBox;
    private Image image;
    
    Scanner scanner;

    private Round round;

    /**
     * Different states in the game.
     */
    public enum STATE {
        Menu, Game;
    }

    public static STATE gameState = STATE.Menu;

    /**
     * new game object responsible for starting game.
     */
    public Game() {
        handler = new Handler();
        try {
            scanner = new Scanner(new File(
                "highscore.txt"));
            highScore = scanner.nextInt();
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        menu = new Menu(this);
        this.addMouseListener(new KeyInput());
        this.addMouseListener(menu);
        this.addKeyListener(new KeyInput());

        new Window(WIDTH, HEIGHT, "Zombies", this);

        hud = new HUD();

        round = new Round(hud);
        
        mysteryBox = new MysteryBox(600, -40);
        
        ImageIcon i = new ImageIcon("grass_background.png");
        image = i.getImage();

    }


    /**
     * starts everything up.
     */
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }


    /**
     * stops everything.
     */
    public synchronized void stop() {
        try {
            thread.join();
            running = false;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * games running clock to keep track of ticks
     * and updating.
     */
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }

        stop();
    }


    /**
     * updates the game.
     */
    private void tick() {
        handler.tick();
        if (gameState == STATE.Game) {
            hud.tick();
            round.tick();
        }
        else if (gameState == STATE.Menu) {
            menu.tick();
        }
    }


    /**
     * renders the game's graphics.
     */
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        //g.setColor(Color.black);
        //g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);

        handler.render(g);

        if (gameState == STATE.Game) {
            hud.render(g);
        }
        else if (gameState == STATE.Menu) {
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }


    /**
     * keeps the player from leaving the window.
     * 
     * @param current
     *            current position
     * @param min
     *            minimum position
     * @param max
     *            max position
     * @return
     *         True if in bounds, false if not.
     */
    public static int clamp(int current, int min, int max) {
        if (current >= max)
            return current = max;
        else if (current <= min)
            return current = min;
        else
            return current;
    }


    /**
     * starts the game by creating new game object.
     * 
     * @param args
     *            Does nothing.
     */
    public static void main(String[] args) {
        new Game();
    }
    
    public static boolean collide(GameObject ob1, GameObject obj2) {
        
        if (ob1.getBounds().intersects(obj2.getBounds())) {
            return true;
        }
        else {
            return false;
        }
        
    }
    
    public static void pause(long timeInMilliSeconds) {

        long timestamp = System.currentTimeMillis();


        do {

        } while (System.currentTimeMillis() < timestamp + timeInMilliSeconds);

    }
    
    
}
