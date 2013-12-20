/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Jundat
 */
public class Sound {

    public static float MIN_VOLUME = -80.0f;
    public static float MAX_VOLUME = 6.0f;
    //
    String fileName;
    boolean isPause = false;
    boolean isLoop = false;
    int pausePosition = 0;
    public Clip clip;
    
    public Sound(String fileName, boolean isLoop) {
        this.fileName = fileName;
        this.isPause = false;
        this.isLoop = isLoop;

        AudioInputStream audioIn = null;

        try {
            audioIn = AudioSystem.getAudioInputStream(ResourceManager.class.getResource(fileName));
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            System.out.println("+ Sound: " + fileName);
        } catch (Exception ex) {
            System.out.println("Can not open sound: " + fileName);
        } finally {
            try {
                audioIn.close();
                audioIn = null;
            } catch (IOException ex) {
                System.out.println("Can not close sound: " + fileName);
            }
        }
    }
    
    public Sound clone() {
        return new Sound(this.fileName, this.isLoop);
    }

    /**
     * release data
     */
    public void dispose() {
        clip.close();
        clip = null;
        System.out.println("- Sound: " + fileName);
    }

    public void play() {
        this.play(true);
    }

    public void play(boolean startAgain) {
        if (isLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        if (startAgain) {
            clip.setMicrosecondPosition(0);
        }

        if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
            clip.setMicrosecondPosition(0);
        }
        
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void pause() {
        pausePosition = clip.getFramePosition();
        clip.stop();

        isPause = true;
    }

    public void resume() {
        if (isPause) {
            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.setFramePosition(pausePosition);
            clip.start();

            isPause = false;
        }
    }

    public float getVolume() {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return volume.getValue();
    }
    
    public void setVolume(float gainAmount) {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(gainAmount);
    }
}
