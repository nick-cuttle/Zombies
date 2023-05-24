package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;

public class Knife extends Guns {

    public Knife(int x, int y) {
        super(x, y, ID.Knife);
        damage = 10;
        ammo = 0;
        clipSize = 0;
        clip = clipSize;
        fireRate = 20;
        reloadTime = 1000;
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
