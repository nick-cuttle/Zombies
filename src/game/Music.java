package game;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Music {

    public static void playSound(File file) {

        try {

            if (file.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                    file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

                // JOptionPane.showMessageDialog(null, null);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
