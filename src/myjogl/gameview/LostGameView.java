/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import myjogl.GameEngine;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Sound;

/**
 *
 * @author Jundat
 */
public class LostGameView implements GameView {

    Point pBg = new Point(230, 132);
    Point pGame = new Point(230 + 291, 132 + 252);
    Point pOver = new Point(230 + 300, 132 + 165);
    Rectangle rectMenu = new Rectangle(230 + 30, 130, 202, 54);
    
    MenuItem itMenu;
    
    MainGameView2Offline mainGameView;
    Texture ttBg;
    
    public static long TIME_ANIMATION = 500;
    long time = 0;
    boolean isSliding = true;
    
    public LostGameView(MainGameView2Offline mainGameView) {
        this.mainGameView = mainGameView;
        mainGameView.isPause = true;
        
        time = 0;
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SHIFT) {
            GameEngine.sClick.play();
            itMenu.setIsClick(true);
            GameEngine.getInstance().attach(new MenuView());
            GameEngine.getInstance().detach(mainGameView);
            GameEngine.getInstance().detach(this);
        }

        itMenu.setIsOver(false);
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
        if (itMenu.contains(e.getX(), e.getY())) {
            if (itMenu.isOver == false) {
                itMenu.setIsOver(true);
                GameEngine.sMouseMove.play(false);
            }
        } else {
            itMenu.setIsOver(false);
        }
    }

    public void pointerReleased(MouseEvent e) {
        if (isSliding == false) {
            return;
        }
        
        if (itMenu.contains(e.getX(), e.getY())) { //menu
            itMenu.setIsClick(true);
            GameEngine.sClick.play();
            //
            GameEngine.getInstance().attach(new MenuView());
            GameEngine.getInstance().detach(mainGameView);
            GameEngine.getInstance().detach(this);
        }
    }

    public void load() {
        ttBg = ResourceManager.getInst().getTexture("data/common/bg_dialog.png");
        itMenu = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_press.png"));
        
        itMenu.SetPosition(rectMenu.x, rectMenu.y);
        itMenu.setIsOver(true);

        GameEngine.getInstance().saveHighscore();

        GameEngine.sGameOver.play();
    }

    public void unload() {
        //ResourceManager.getInst().deleteTexture("data/common/bg_dialog.png");
    }

    public void update(long elapsedTime) {
        time += elapsedTime;

        while (mainGameView.sBackground.getVolume() >= Sound.MIN_VOLUME + 1.0f) {
            mainGameView.sBackground.setVolume(mainGameView.sBackground.getVolume() - 1.0f);
        }
    }

    public void display() {
        float delta = 1.0f;
        if (time <= TIME_ANIMATION) {
            delta = (float) time / (float) TIME_ANIMATION;
            if (delta > TIME_ANIMATION) {
                isSliding = false;
            }
        }

        Renderer.Render(ttBg, pBg.x, pBg.y * delta);
        //
        itMenu.SetPosition(rectMenu.x, (int) (rectMenu.y * delta));
        itMenu.Render();
        
        GameEngine.writer.Render("Opponent", pGame.x - 30, pGame.y * delta, 0.9f, 0.9f);
        GameEngine.writer.Render("left game...", pOver.x - 90, pOver.y * delta, 0.9f, 0.9f);
        
        GameEngine.writer.Render("MENU", rectMenu.x + 53, (rectMenu.y + 16) * delta, 0.85f, 0.85f);
    }
}
