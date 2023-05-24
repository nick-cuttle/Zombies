package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;

public class FlameThrower extends Guns {

    public FlameThrower(int x, int y) {
        super(x, y, ID.FlameThrower);
        ImageIcon i = new ImageIcon("flamethrower.png");
        image = i.getImage();
        ImageIcon i2 = new ImageIcon("flame.gif");
        bulletImage = i2.getImage();
        bulletImage = bulletImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ammo = 999999999;
        ammoMax = ammo;
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
            Bullet flame = new Bullet(this.getX(), this.getY(), xTo, yTo, this,
                0, 100);
            flame.setLifeTime(25);
            Game.handler.addObject(flame);

        }
        else if (clip <= 0) {
            Music.playSound(new File("empty_clip.wav"));
        }
    }


    public void playReloadSound() {
        Music.playSound(new File("shotgun_reload.wav"));
    }
}
