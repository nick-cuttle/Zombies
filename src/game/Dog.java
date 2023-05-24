package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Dog extends GameObject {

    private int size;
    private boolean grabbedPower;

    public Dog(int x, int y) {
        super(x, y, ID.Dog);
        ImageIcon i = new ImageIcon("pug.gif");
        image = i.getImage();
        grabbedPower = false;
        canCollide = true;
    }


    @Override
    public void tick() {
        moveCorrectly();
        x += velX;
        y += velY;
    }
    
    private void moveCorrectly() {
        GameObject nearest = locateNearestPower();
        if (nearest != null && getBounds().intersects(nearest.getBounds())) {
            grabbedPower = true;
            nearest.x = x;
            nearest.y = y;
        }
        else {
            grabbedPower = false;
        }
        
        if (!grabbedPower && nearest != null) {
            double distance = distanceToPower();
            double distX = Math.sqrt(Math.pow(nearest.x - this.x, 2));
            double angle = Math.acos(distX / distance);
            move(angle, nearest.x, nearest.y);
        }
        else {
            goToPlayer();
        }
    }


    @Override
    public void render(Graphics g) {
        GameObject nearest = locateNearestPower();
        int ab = 50;
        if (Game.player != null && nearest == null && x > Game.player.getX()) {
            ab = -50;
        }
        else if (!grabbedPower && nearest != null && nearest.getX() < x) {
            ab = -50;
        }
        else if (grabbedPower && nearest != null && x > Game.player.getX()) {
            ab = -50;
        }

        if (velX == 0 && velY == 0) {
            ImageIcon i = new ImageIcon("pug_stop.gif");
            image = i.getImage();
        }
        else {
            ImageIcon i = new ImageIcon("pug.gif");
            image = i.getImage();
        }
        g.drawImage(image, x - (ab / 2), y, ab, 50, null);
    }


    public GameObject locateNearestPower() {
        GameObject power = null;
        double distance = 9999999;
        for (int i = 0; i < Game.handler.object.size(); i++) {
            GameObject tempObject = Game.handler.object.get(i);

            if (tempObject.getId() == ID.MaxAmmo || tempObject
                .getId() == ID.InstaKill) {
                double part1 = Math.pow((this.x - tempObject.x), 2) + Math.pow(
                    (this.y - tempObject.y), 2);
                double thisDist = Math.sqrt(part1);
                if (thisDist < distance) {
                    power = tempObject;
                    distance = thisDist;
                }
            }

        }
        return power;
    }


    public double distanceToPower() {
        GameObject power = locateNearestPower();
        double part1 = Math.pow((this.x - power.x), 2) + Math.pow((this.y
            - power.y), 2);
        return Math.sqrt(part1);

    }


    public void goToPlayer() {
        // finding the closest player.

        int dX = this.getX() + 8;
        int dY = this.getY() + 8;

        double distance = 100000;

        if (Game.player != null) {
            double part1 = (Math.pow(dX - (Game.player.getX() + 16), 2)) + (Math
                .pow(dY - (Game.player.getY() + 16), 2));
            distance = Math.sqrt(part1);
        }

        if (!grabbedPower && distance < 100) {
            velX = 0;
            velY = 0;
        }

        // determining the distance and angle of player.
        if (distance >= 100 && Game.player != null) {
            double distX = Math.sqrt(Math.pow((Game.player.getX() + 16) - (this
                .getX() + 8), 2));
            double angle = Math.acos(distX / distance);
            move(angle, Game.player.getX(), Game.player.getY());
        }
    }


    public void move(double angle, int cx, int cy) {

        if (cx - this.getX() < 0) {
            this.velX = -(6 * Math.cos(angle));
        }
        else {
            this.velX = (6 * Math.cos(angle));
        }
        if (cy - this.getY() < 0) {
            this.velY = -(6 * Math.sin(angle));
        }
        else {
            this.velY = (6 * Math.sin(angle));
        }

    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }

}
