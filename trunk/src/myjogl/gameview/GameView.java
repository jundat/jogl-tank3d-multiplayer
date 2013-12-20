package myjogl.gameview;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GLAutoDrawable;

/**
 * GameView class provides an interface for the game objects views.
 */

public interface GameView {
    
    public void keyPressed(KeyEvent e);
    
    public void keyReleased(KeyEvent e);

    public void pointerPressed(MouseEvent e);

    public void pointerMoved(MouseEvent e);

    public void pointerReleased(MouseEvent e);
    
    public void load();
    
    public void unload();

    public void update(long elapsedTime);

    public void display();
}
