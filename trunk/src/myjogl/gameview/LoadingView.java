/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GLAutoDrawable;
import myjogl.GameEngine;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Writer;

/**
 * Ch?a dùng ???c!!!
 *
 * @author Jundat
 */
public class LoadingView implements GameView {

    private GameView loadView;
    private boolean isCompleted = false;
    private int beforeLoad = 0;
    private int currentLoad = 0;
    private float rotate = 0.0f;
    private Texture ttLoadingCircle;

    public LoadingView(GameView loadView) {
        System.out.println("Go to loading view ---------------------------------");
        this.loadView = loadView;
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
    }

    public void load() {
        ttLoadingCircle = ResourceManager.getInst().getTexture("data/loading/loading_circle2.png");

        beforeLoad = ResourceManager.getInst().GetNumberPreload();
        currentLoad = beforeLoad;
        isCompleted = false;
    }

    public void unload() {
        ResourceManager.getInst().deleteTexture("data/loading/loading_circle2.png");
    }

    public void update(long elapsedTime) {
        rotate += 15.0f;
        
        currentLoad = ResourceManager.getInst().GetNumberPreload();
        
        if (currentLoad == 0 && isCompleted == false) {
            isCompleted = true;
            GameEngine.getInst().attach(loadView);
            GameEngine.getInst().detach(this);
        }
    }

    public void display() {
        Renderer.Render(ttLoadingCircle, 
                Global.wndWidth / 2 - ttLoadingCircle.getWidth() /2, 
                Global.wndHeight / 2 - ttLoadingCircle.getHeight() /2, 
                ttLoadingCircle.getWidth(), 
                ttLoadingCircle.getHeight(), 
                rotate);

        currentLoad = ResourceManager.getInst().GetNumberPreload();
        float percent = (float) (beforeLoad - currentLoad) / (float)beforeLoad;
        
        GameEngine.writer.Render("...loading... " + (int) (100 * percent) + " %", 10, 10, 0.5f, 0.5f, 0.8f, 0.1f, 0.1f);
    }
}
