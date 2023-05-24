package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class KeyInput extends KeyAdapter implements MouseListener {

    private Menu menu;

    private static boolean[] keyDown1 = new boolean[4];

    public KeyInput() {
        keyDown1[0] = false;
        keyDown1[1] = false;
        keyDown1[2] = false;
        keyDown1[3] = false;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (Game.player != null) {

            if (key == KeyEvent.VK_W) {
                Game.player.setVelY(-5);
                keyDown1[0] = true;
            }
            if (key == KeyEvent.VK_S) {
                Game.player.setVelY(5);
                keyDown1[1] = true;
            }
            if (key == KeyEvent.VK_A) {
                Game.player.setVelX(-5);
                keyDown1[2] = true;
            }
            if (key == KeyEvent.VK_D) {
                Game.player.setVelX(5);
                keyDown1[3] = true;
            }
            if (key == KeyEvent.VK_R) {
                if (Game.player.getCurrentGun() != null) {
                    Game.player.getCurrentGun().reloadTime();
                }
            }

            if (key == KeyEvent.VK_1) {
                Timer timer = new Timer();
                timer.schedule(new swapHand(1, timer), 1000);
            }
            if (key == KeyEvent.VK_2) {
                Timer timer = new Timer();
                timer.schedule(new swapHand(2, timer), 1000);
            }
            if (key == KeyEvent.VK_E) {
                Game.mysteryBox.open();
            }
        }

    }


    public void keyReleased(KeyEvent e) {
        if (Game.player != null) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W)
                keyDown1[0] = false;
            if (key == KeyEvent.VK_S)
                keyDown1[1] = false;
            if (key == KeyEvent.VK_A)
                keyDown1[2] = false;
            if (key == KeyEvent.VK_D)
                keyDown1[3] = false;

            // vertical movement
            if (!keyDown1[0] && !keyDown1[1]) {
                Game.player.setVelY(0);
            }
            // horizontal movement
            if (!keyDown1[2] && !keyDown1[3]) {
                Game.player.setVelX(0);
            }
        }

    }


    @Override
    public void mouseClicked(MouseEvent m) {

    }


    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mousePressed(MouseEvent m) {
        if (Game.player != null && m.getButton() == MouseEvent.BUTTON1
            && Game.gameState == Game.STATE.Game) {

            int x = m.getX();
            int y = m.getY();
            Guns[] inv = Game.player.getInventory();
            int p2x = Game.player.getX();
            int p2y = Game.player.getY();

            if (Game.player.getCurrentGun() != null) {
                Game.player.getCurrentGun().shoot(x, y);
            }

        }

    }
    
    private class swapHand extends TimerTask {
        
        private int swapTo;
        private Timer timer;
        
        public swapHand(int swap, Timer t) {
            swapTo = swap;
            timer = t;
        }

        @Override
        public void run() {
            
            if (swapTo == 1) {
                Game.player.setGunIndex(0);
                Game.player.setCurrentGun(Game.player.getGunIndex());
            }
            else if (swapTo == 2) {
                Game.player.setGunIndex(1);
                Game.player.setCurrentGun(Game.player.getGunIndex());
            }
            timer.cancel();
            
        }
        
        
        
    }

}
