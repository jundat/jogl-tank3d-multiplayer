/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GL;
import myjogl.GameEngine;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Writer;

/**
 *
 * @author Jundat
 */
public class ChooseModeView implements GameView {

    Point pExit = new Point(820, 720 - 664 + 150);
    Point pAbout = new Point(134, 720 - 664 + 150);
    Point pPlay = new Point(477, 720 - 688);
    Point pTop = new Point(0, 720 - 251);
    Point pBottom = new Point(0, 0);
    Point pAboutBg = new Point(52, 720 - 584);
    float textScale = 0.85f * 720 / 640;
    //
    private MenuItem itPlay;
    private MenuItem itAbout;
    private MenuItem itExit;
    //
    Texture ttBgMenu;
    Texture ttTop;
    Texture ttBottom;
    Texture ttAbout;
    private boolean isAboutState = false;
    
    private int menuItemCounter = 1;
    private int MAX_MENU_ITEM_COUNTER = 2;

    public ChooseModeView() {
        //System.out.println("Go to menu view-------------------------------------");
    }
    
    private void ChooseAbout() {
        itAbout.setIsClick(false);
        preloadMainGame();
        GameEngine.getInst().attach(new LoadingView((GameView) new MainGameView2Offline()));
        GameEngine.getInst().detach(this);
    }
    
    private void ChoosePlay() {
        itPlay.setIsClick(true);
        preloadMainGame();
        GameEngine.getInst().attach(new LoadingView((GameView) new MainGameView1Player()));
        GameEngine.getInst().detach(this);
    }
    
    private void ChooseExit() {
        itExit.setIsClick(false);
        preloadMainGame();
        GameEngine.getInst().attach(new LoadingView((GameView) new MainGameView2Online()));
        GameEngine.getInst().detach(this);
    }
    
    private void ChooseEscape() {
        GameEngine.getInst().attach(new MenuView());
        GameEngine.getInst().detach(this);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SHIFT) {
            GameEngine.sClick.play();
            switch(menuItemCounter)
            {
                case 0:
                    ChooseAbout();
                    break;

                case 1:
                    ChoosePlay();
                    break;

                case 2:
                    ChooseExit();
                    break;
            }
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameEngine.sClick.play();
            ChooseEscape();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            GameEngine.sMouseMove.play(false);
            menuItemCounter--;
            menuItemCounter = (menuItemCounter < 0) ? 0 : menuItemCounter;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            GameEngine.sMouseMove.play(false);
            menuItemCounter++;
            menuItemCounter = (menuItemCounter > MAX_MENU_ITEM_COUNTER) ? MAX_MENU_ITEM_COUNTER : menuItemCounter;
        }
        
        itAbout.setIsOver(false);
        itPlay.setIsOver(false);
        itExit.setIsOver(false);
        
        switch(menuItemCounter)
        {
            case 0:
                itAbout.setIsOver(true);
                break;
                
            case 1:
                itPlay.setIsOver(true);
                break;
                
            case 2:
                itExit.setIsOver(true);
                break;
        }
    }

    public void pointerPressed(MouseEvent e) {
        if (itPlay.contains(e.getX(), e.getY())) {
            itPlay.setIsClick(true);
        }

        if (itAbout.contains(e.getX(), e.getY())) {
            itAbout.setIsClick(true);
        }

        if (itExit.contains(e.getX(), e.getY())) {
            itExit.setIsClick(true);
        }
    }

    public void pointerMoved(MouseEvent e) {
        if (itPlay.contains(e.getX(), e.getY())) {
            if (itPlay.isOver == false) {
                GameEngine.sMouseMove.play(false);
                itPlay.setIsOver(true);
            }
        } else {
            itPlay.setIsOver(false);
        }

        if (itAbout.contains(e.getX(), e.getY())) {
            if (itAbout.isOver == false) {
                GameEngine.sMouseMove.play(false);
                itAbout.setIsOver(true);
            }
        } else {
            itAbout.setIsOver(false);
        }

        if (itExit.contains(e.getX(), e.getY())) {
            if (itExit.isOver == false) {
                GameEngine.sMouseMove.play(false);
                itExit.setIsOver(true);
            }
        } else {
            itExit.setIsOver(false);
        }
    }

    public void pointerReleased(MouseEvent e) {
        if (itPlay.contains(e.getX(), e.getY())) {
            itPlay.setIsClick(false);
            ChoosePlay();
            GameEngine.sClick.play();
        }

        if (itAbout.contains(e.getX(), e.getY())) {
            itAbout.setIsClick(false);
            ChooseAbout();
            GameEngine.sClick.play();
        }

        if (itExit.contains(e.getX(), e.getY())) {
            itExit.setIsClick(false);
            ChooseExit();
            GameEngine.sClick.play();
        }
    }

    public void load() {
        ttBgMenu = ResourceManager.getInst().getTexture("data/menu/bg_menu.png");
        ttTop = ResourceManager.getInst().getTexture("data/menu/top.png");
        ttBottom = ResourceManager.getInst().getTexture("data/menu/bottom.png");
        ttAbout = ResourceManager.getInst().getTexture("data/menu/bg_about.png");

        itPlay = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn_play.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_play_press.png"));
        itAbout = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn_play.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_play_press.png"));
        itExit = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn_play.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_play_press.png"));

        itPlay.SetPosition(pPlay);
        itAbout.SetPosition(pAbout);
        itExit.SetPosition(pExit);
        
        itPlay.setIsOver(true);
        
        Writer writer = new Writer("data/font/Motorwerk_80.fnt");
    }

    public void unload() {
    }

    private void preloadMainGame() {
        //pre-load main game
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong0.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong1.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong2.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong3.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong4.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        //
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men1.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men2.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men3.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men4.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        
        //skybox
        ResourceManager.getInst().PreLoadTexture("data/skybox/top.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/bottom.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/left.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/right.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/front.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/back.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        Renderer.Render(ttBgMenu, 0, 0);

        if(isAboutState == true) {
            Renderer.Render(ttAbout, pAboutBg.x, pAboutBg.y);
        }
        
        //background
        Renderer.Render(ttTop, pTop.x, pTop.y);
        Renderer.Render(ttBottom, pBottom.x, pBottom.y);

        itPlay.Render();
        itAbout.Render();
        itExit.Render();

        //text
        GameEngine.writer.Render("2", pAbout.x + 140, pAbout.y + 90, textScale, textScale, 1.0f, 1.0f, 1.0f);
        GameEngine.writer.Render("OFFLINE", pAbout.x + 30, pAbout.y + 40, textScale, textScale, 1.0f, 1.0f, 1.0f);
        
        GameEngine.writer.Render("SINGLE", pPlay.x + 39, pPlay.y + 60, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        
        GameEngine.writer.Render("2", pExit.x + 140, pExit.y + 90, textScale, textScale, 1.0f, 1.0f, 1.0f);
        GameEngine.writer.Render("ONLINE", pExit.x + 50, pExit.y + 40, textScale, textScale, 1.0f, 1.0f, 1.0f);
    }
}
