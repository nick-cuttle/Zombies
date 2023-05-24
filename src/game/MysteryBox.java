package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;

public class MysteryBox extends GameObject {

    private int size;
    private GameObject[] items;
    private int index, cost;
    private Image gunDisplay;
    private boolean opened;

    public MysteryBox(int x, int y) {
        super(x, y, ID.MysteryBox);
        ImageIcon i = new ImageIcon("MysteryBox.png");
        index = 0;
        image = i.getImage();
        items = new GameObject[4];
        initBox();
        gunDisplay = null;
        opened = false;
        cost = 150;
        width = x + 30;
        height = y + 50;

    }


    private void initBox() {
        items[0] = new Shotgun(x, y);
        items[1] = new Raygun(x, y);
        items[2] = new FlameThrower(x, y);
        items[3] = new Dog(x, y);
    }


    @Override
    public void tick() {
        collide();
        
    }
    
    public boolean playerCollideWithBox() {
        if (getBounds().intersects(Game.player.getBounds())) {
            return true;
        }
        return false;
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, 200, 100, null);
        // hitbox
        g.drawRect(x + 30, y + 50, 140, 50);
        g.drawRect(x + 30, y + 100, 140, 30);
        g.drawImage(gunDisplay, x + 60, y + 50, 75, 25, null);

    }


    public void open() {
        if (Game.player.getMoney() >= cost && !opened && Game.player.getBounds().intersects(this.openingBounds())) {
            opened = true;
            Game.player.setMoney(Game.player.getMoney() - cost);
            Music.playSound(new File("mystery_box_opening.wav"));
            Timer timer = new Timer();
            Random random = new Random();
            int numSpins = random.nextInt(20) + 20;
            SpinBox spins = new SpinBox(this, timer, numSpins);
            timer.schedule(spins, 150, 150);

        }
    }


    private void spin() {
        
        // for (int i = 0; i < count; i++) {
        index = (index + 1) % items.length;
        gunDisplay = items[index].getImage();
        // }
        // gunDisplay = null;
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(width, height, 140, 50);
    }


    public Rectangle openingBounds() {
        return new Rectangle(x + 30, y + 100, 140, 30);
    }


    public void collide() {
        if (Game.player != null && this.getBounds().intersects(Game.player
            .getBounds())) {
            Game.player.setColliding(true);
        }
        else if (Game.player != null) {
            Game.player.setColliding(false);
        }
    }
    
    public void setGunDisplay(Image i) {
        gunDisplay = i;
    }
    
    public int getIndex() {
        return index;
    }
    
    public GameObject[] getItems() {
        return items;
    }

    private class SpinBox extends TimerTask {

        private MysteryBox box;
        private Timer timer;
        private int count;

        public SpinBox(MysteryBox box, Timer timer, int count) {
            this.box = box;
            this.timer = timer;
            this.count = count;
        }


        public void run() {
            box.spin();
            count--;
            if (count <= 0) {
                opened = false;
                Music.playSound(new File("mystery_box_reveal.wav"));
                GameObject copy = box.getItems()[box.getIndex()];
                Game.handler.addObject(copy.clone(x + 60, y + 50, copy));
                box.setGunDisplay(null);
                timer.cancel();
            }
        }

    }

}
