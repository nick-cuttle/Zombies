package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import javax.swing.ImageIcon;

public class Shotgun extends Guns {

    public Shotgun(int x, int y) {
        super(x, y, ID.Shotgun);
        ImageIcon i = new ImageIcon("shotgun.png");
        image = i.getImage();
        ImageIcon i2 = new ImageIcon("shotgun_bullet.png");
        bulletImage = i2.getImage();
        bulletImage = bulletImage.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
        ammo = 52;
        ammoMax = ammo;
        damage = 30;
        clipSize = 8;
        clip = clipSize;
        reloadTime = 2000;
        fireRate = 10;
        width = 60;
        height = 25;
        
    }


    @Override
    public void tick() {
        super.tick();
    }


    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }


    @Override
    public void render(Graphics g) {
        super.render(g);
    }


    public void shoot(int xTo, int yTo) {
        if (canShoot()) {
            fireRate = 10;
            clip--;
            Music.playSound(new File("shotgun.wav"));
            Game.handler.addObject(new Bullet(this.getX(), this.getY(), xTo,
                yTo, this, 18, 10));

            Game.handler.addObject(new Bullet(this.getX(), this.getY(), xTo, yTo
                - 100, this, 18, 10));

            Game.handler.addObject(new Bullet(this.getX(), this.getY(), xTo, yTo
                + 100, this, 18, 10));
        }
        else if (clip <= 0) {
            Music.playSound(new File("empty_clip.wav"));
        }
    }


    public void playReloadSound() {
        Music.playSound(new File("shotgun_reload.wav"));
    }

}
