package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import game.Game.STATE;

/**
 * class dealing with the rounds and spawning mechanisms.
 * 
 * @author Nicholas Cuttle
 *
 */
public class Round {

    private HUD hud;
    private int zombiesPerRound = 5;
    private int counter = zombiesPerRound;
    private int spawnCounter = -300;
    private Random random;
    private double spawnInterval = 50;
    private int minSpawnDistance = 250;

    /**
     * new round object.
     * 
     * @param hud
     *            the hud object.
     * @param handler
     *            the handler object.
     */
    public Round(HUD hud) {
        this.hud = hud;
        random = new Random();
    }


    /**
     * deals with updating rounds.
     */
    public void tick() {
        if (Game.gameState == Game.STATE.Game) {
            spawnCounter++;

            // displays the round in middle of screen
            if (spawnCounter < 0 && spawnCounter > -75) {
                hud.setFontSize(25);
                hud.setXFont(50);
                hud.setYFont(700);
            }
            // tracks number of zombies and how often they should spawn.
            if (counter > 0 && spawnCounter > spawnInterval) {
                closestPlayer();
                spawnCounter = 0;
                counter--;
            }
            // sets new round,
            if (newRound()) {
                nextRound();
            }

            // processes when they die.
            if (dead()) {
                setHighScore();
                Game.handler.object.clear();
                Game.gameState = STATE.Menu;
                hud.setLevel(1);
                zombiesPerRound = 5;
                spawnInterval = 50;
                counter = zombiesPerRound;
                spawnCounter = -300;
                hud.setLevelString("I");
                
            }
        }

    }


    /**
     * checks if everyone has died.
     * 
     * @return
     *         true if everyone is dead.
     */
    private boolean dead() {
        if (Game.player != null && Game.player.getHealth() <= 0) {
            return true;
        }
        return false;
    }


    private void setHighScore() {
        try {
            Scanner scanner = new Scanner(new File("highscore.txt"));
            int highscore = scanner.nextInt();
            scanner.close();

            if (hud.getLevel() > highscore) {
                PrintWriter writer = new PrintWriter(new File("highscore.txt"));
                writer.write(hud.getLevel() + "");
                writer.close();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * spawns zombie at given x and y.
     * 
     * @param x
     *            x coordinate to spawn zombie at
     * @param y
     *            y coordinate to spawn zombie at.
     */
    public void spawn(int x, int y) {
        Game.handler.addObject(new BasicZombie(x, y, ID.BasicZombie));
    }


    /**
     * checks whether it should be a new round.
     * 
     * @return
     *         True if a new round should begin.
     */
    public boolean newRound() {
        int numOfZombies = 0;
        for (int i = 0; i < Game.handler.object.size(); i++) {
            GameObject tempObject = Game.handler.object.get(i);
            if (tempObject.getId() == ID.BasicZombie)
                numOfZombies++;
        }
        return numOfZombies == 0 && counter == 0;
    }


    /**
     * handles with setting the new round accurately.
     */
    public void nextRound() {
        if (!dead()) {
            Game.player.setHealth(100);
        }
        zombiesPerRound += 5;
        BasicZombie.zombieHealth += 10;
        counter = zombiesPerRound;
        hud.setLevel(hud.getLevel() + 1);
        spawnCounter = -400;
        Music.playSound(new File("round_start.wav"));
        setText();
        if (hud.getLevel() == 5) {
            spawnInterval = spawnInterval / 2;
        }
        else if (hud.getLevel() == 10)
            spawnInterval = spawnInterval / 2;

    }


    /**
     * sets the text in the middle of the screen.
     */
    private void setText() {
        hud.setFontSize(50);
        hud.setXFont(Game.WIDTH / 2);
        hud.setYFont(Game.HEIGHT / 2);
        if (hud.getLevel() >= 5)
            hud.setLevelString("" + hud.getLevel());
        else
            hud.setLevelString(hud.getLevelString() + "I");

    }


    /**
     * determines the closest player and spawns zombie within radius.
     */
    private void closestPlayer() {
        if (Game.player != null) {
            int ranX = random.nextInt(Game.WIDTH - 15);
            int ranY = random.nextInt(Game.HEIGHT - 15);

            double part1 = (Math.pow(Game.player.getY() - ranY, 2)) + (Math.pow(
                Game.player.getX() - ranX, 2));
            double distance = Math.sqrt(part1);

            if (distance < minSpawnDistance)
                closestPlayer();
            else
                spawn(ranX, ranY);
        }
    }

}
