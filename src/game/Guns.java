package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;
import game.Player.Facing;

public abstract class Guns extends GameObject {

    protected int fireRate, width, height;
    protected boolean grabbed, reloading;
    protected int ammo, clipSize, clip, reloadTime, damage, ammoMax;
    protected double bob;
    protected Image bulletImage;

    public Guns(int x, int y, ID id) {
        super(x, y, id);
        grabbed = false;
        bob = 0;
        reloading = false;
        canCollide = true;
    }


    @Override
    public void tick() {
        fireRate--;
        collide();
        bob();
    }


    public boolean canShoot() {
        if (!reloading && clip > 0 && grabbed && fireRate < 0) {
            return true;
        }
        return false;
    }


    public void shoot(int xTo, int yTo) {
        if (!reloading) {
            this.shoot(xTo, yTo);
        }
        // determineGun(xTo, yTo);
    }


    private void determineGun(int xTo, int yTo) {

        if (this.getId() == ID.Shotgun) {
            Shotgun shotgun = (Shotgun)this;
            shotgun.shoot(xTo, yTo);
        }
        else if (this.getId() == ID.Raygun) {
            Raygun raygun = (Raygun)this;
            raygun.shoot(xTo, yTo);
        }

    }


    @Override
    public void render(Graphics g) {
        if (determineRender()) {
            g.drawImage(image, x, y, flip(), height, null);
        }
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


    public int getFireRate() {
        return fireRate;
    }


    public void setFireRate(int rate) {
        fireRate = rate;
    }


    public boolean grabbed() {
        return this.grabbed;
    }


    public void setGrabbed(boolean grabbed) {
        this.grabbed = grabbed;
    }

    public int getClip() {
        return clip;
    }


    public int getAmmo() {
        return ammo;
    }


    public void reload() {
        if (ammo >= clipSize) {
            int numberOfBullets = clipSize - clip;
            ammo = ammo - numberOfBullets;
            clip = clipSize;
        }
        else if (ammo > 0) {
            clip = ammo;
            ammo = 0;
        }
        reloading = false;
    }


    public void playReloadSound() {
        this.playReloadSound();
    }


    public void reloadTime() {
        if (!reloading) {
            reloading = true;
            Timer timer = new Timer();
            playReloadSound();
            timer.schedule(new Reload(this, timer), reloadTime);
        }
    }


    public void bob() {
        if (grabbed == false) {

            y += Math.round(Math.sin(bob));
            bob += .1;
        }

    }


    public void pickup() {
        Guns[] i = Game.player.getInventory();
        i[Game.player.getGunIndex()] = this;
        Game.player.setCurrentGun(Game.player.getGunIndex());
        grabbed = true;

    }


    public void collide() {
        if (Game.player != null && Game.player.getHand() != null && grabbed == true) {
            this.setX(Game.player.getHand().getX());
            this.setY(Game.player.getHand().getY() - (height / 2));
        }
        else if (Game.player != null && this.getBounds().intersects(Game.player
            .getBounds())) {
            pickup();

        }

    }


    public boolean determineRender() {
        if (Game.player != null) {
            if (grabbed == false || Game.player.getInventory()[Game.player
                .getGunIndex()] == this) {
                return true;
            }
            return false;
        }
        return false;
    }


    public int flip() {
        int w = Math.abs(width);
        if (grabbed && Game.player.direction == Facing.LEFT) {
            return -w;
        }
        else {
            return w;
        }
    }

    /**
     * reloads weapon after delay;
     * 
     * @author angry
     *         Nick Cuttle
     *
     */
    private class Reload extends TimerTask {

        private Guns gun;
        private Timer timer;

        public Reload(Guns g, Timer t) {
            gun = g;
            timer = t;
        }


        @Override
        public void run() {
            gun.reload();
            timer.cancel();
        }

    }

}
