package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;

public class MaxAmmo extends GameObject {

    private boolean grabbed;
    private int size, lifeTime;

    public MaxAmmo(int x, int y) {
        super(x, y, ID.MaxAmmo);
        grabbed = false;
        ImageIcon i = new ImageIcon("max_ammo.gif");
        image = i.getImage();
        size = 40;
        lifeTime = 700;
        canCollide = true;
    }


    @Override
    public void tick() {
        collide();
        lifeTime--;
        if (lifeTime <= 0) {
            Game.handler.removeObject(this);
        }
    }


    public void collide() {
        if (Game.player != null && getBounds().intersects(Game.player
            .getBounds())) {
            grabbed = true;
            Guns[] i = Game.player.getInventory();
            
            i[0].ammo = i[0].ammoMax;
            i[0].clip = i[0].clipSize;

            if (i[1] != null) {
                i[1].ammo = i[1].ammoMax;
                i[1].clip = i[1].clipSize;
            }
            Music.playSound(new File("max_ammo.wav"));
            Game.handler.removeObject(this);

        }
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, size, size, null);
        g.setColor(Color.WHITE);
        g.drawRect(x - 15, y - 5, 70, 5);
        g.setColor(Color.YELLOW);
        g.fillRect(x - 15, y - 5, lifeTime / 10, 5);

    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

}
