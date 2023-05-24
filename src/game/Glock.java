package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;

public class Glock extends Guns {

    public Glock(int x, int y) {
        super(x, y, ID.Glock);
        ImageIcon i = new ImageIcon("glock.png");
        image = i.getImage();
        ImageIcon i2 = new ImageIcon("bullet.png");
        bulletImage = i2.getImage();
        bulletImage = bulletImage.getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        damage = 10;
        ammo = 48;
        ammoMax = ammo;
        clipSize = 8;
        clip = clipSize;
        reloadTime = 2000;
        fireRate = 10;
        width = 35;
        height = 25;
    }


    public void tick() {
        super.tick();
    }


    public Rectangle getBounds() {
        return super.getBounds();
    }


    public void render(Graphics g) {
        super.render(g);
    }


    public void shoot(int xTo, int yTo) {
        if (canShoot()) {
            fireRate = 10;
            clip--;
            Music.playSound(new File("gun-gunshot-02.wav"));
            Game.handler.addObject(new Bullet(this.getX(), this.getY(), xTo,
                yTo, this, 18, 10));

        }
        else if (clip <= 0) {
            Music.playSound(new File("empty_clip.wav"));
        }
    }


    public void playReloadSound() {
        Music.playSound(new File("glock_reload.wav"));
    }

}
