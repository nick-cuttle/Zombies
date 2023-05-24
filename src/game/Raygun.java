package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;

public class Raygun extends Guns {

    public Raygun(int x, int y) {
        super(x, y, ID.Raygun);
        ImageIcon i = new ImageIcon("raygun.png");
        image = i.getImage();
        ImageIcon i2 = new ImageIcon("raygun_bullet.gif");
        bulletImage = i2.getImage();
        bulletImage = bulletImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ammo = 180;
        ammoMax = ammo;
        damage = 100;
        clipSize = 20;
        clip = clipSize;
        fireRate = 20;
        reloadTime = 3000;
        width = 50;
        height = 30;

    }


    public void tick() {
        super.tick();
    }


    @Override
    public void render(Graphics g) {
        super.render(g);
    }


    public void playReloadSound() {
        Music.playSound(new File("raygun_reload.wav"));
    }


    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }


    public void shoot(int xTo, int yTo) {
        if (canShoot()) {
            fireRate = 20;
            clip--;
            Music.playSound(new File("raygun.wav"));
            Bullet b = new Bullet(this.getX(), this.getY(), xTo, yTo, this, 18,
                50);
            Game.handler.addObject(b);

        }
        else if (clip <= 0) {
            Music.playSound(new File("empty_clip.wav"));
        }
    }

}
