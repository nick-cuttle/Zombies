package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

public class InstaKill extends GameObject {

    private boolean grabbed;
    private int size, lifeTime, origDamage1, origDamage2, countdown;
    private Guns[] inventory;
    public static boolean isInstaKill = false;

    public InstaKill(int x, int y) {
        super(x, y, ID.InstaKill);
        grabbed = false;
        inventory = Game.player.getInventory();
        ImageIcon i = new ImageIcon("insta_kill.gif");
        image = i.getImage();
        size = 40;
        lifeTime = 700;
        if (!isInstaKill) {
            origDamage1 = inventory[0].damage;
            if (inventory[1] != null) {
                origDamage2 = inventory[1].damage;
            }
            else {
                origDamage2 = 0;
            }
        }
        countdown = 1040;
        canCollide = true;

    }


    @Override
    public void tick() {
        collide();
        lifeTime--;
        if (!grabbed && lifeTime <= 0) {
            Game.handler.removeObject(this);
        }
        if (grabbed) {
            countdown--;
        }
    }


    public void collide() {
        if (!grabbed && Game.player != null && getBounds().intersects(
            Game.player.getBounds())) {
            isInstaKill = true;
            grabbed = true;
            image = null;

            inventory[0].damage = 99999999;
            if (inventory[1] != null) {
                inventory[1].damage = 9999999;
            }
            Timer timer = new Timer();
            timer.schedule(new endInsta(timer, this), 20 * 1000);
            Music.playSound(new File("max_ammo.wav"));
            // Game.handler.removeObject(this);

        }
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, size, size, null);
        if (image != null) {
            g.setColor(Color.WHITE);
            g.drawRect(x - 15, y - 5, 70, 5);
            g.setColor(Color.YELLOW);
            g.fillRect(x - 15, y - 5, lifeTime / 10, 5);
        }
        else {
            g.setColor(Color.YELLOW);
            g.fillRect(350, 25, countdown / 2, 10);
        }

    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    private class endInsta extends TimerTask {

        private Timer timer;
        private GameObject o;

        public endInsta(Timer t, GameObject obj) {
            timer = t;
            o = obj;
        }


        @Override
        public void run() {
            inventory[0].damage = origDamage1;
            if (inventory[1] != null) {
                inventory[1].damage = origDamage2;
            }
            System.out.println("This is how many ticks: " + countdown);
            Game.handler.removeObject(o);
            timer.cancel();
            isInstaKill = false;
        }

    }

}
