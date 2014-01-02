/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Jundat
 */
public class KeyboardState implements KeyListener {
    
    private boolean[] keys = new boolean[1024];
    
    private static KeyboardState state;
    
    private KeyboardState() {
        for (int i = 0; i < 256; i++) {
            keys[i] = false;
        }
    }
    
    public static KeyboardState getState() {
        if(state == null) {
            state = new KeyboardState();
        }
        
        return state;
    }
    
    /**
     * Be called in object
     * @param key
     * @return 
     */
    public boolean isDown(int key) {
        return keys[key];
    }
    
    public boolean isUp(int key) {
        return ! keys[key];
    }

    public void keyTyped(KeyEvent ke) {
        
    }

    public void keyPressed(KeyEvent ke) {
        keys[ke.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
    }
}
