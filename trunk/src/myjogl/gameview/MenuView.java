/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import myjogl.GameEngine;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Sound;
import myjogl.utils.Writer;

/**
 *
 * @author Jundat
 */
public class MenuView implements GameView {

    Point pExit = new Point(890, 720 - 664);
    Point pAbout = new Point(101, 720 - 664);
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

    //
    public MenuView() {
        //System.out.println("Go to menu view-------------------------------------");
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SHIFT) {
            GameEngine.sClick.play();
            switch(menuItemCounter)
            {
                case 0:
                    itAbout.setIsClick(false);
                    isAboutState = !isAboutState;
                    break;

                case 1:
                    gotoMainGame();
                    break;

                case 2:
                    itExit.setIsClick(false);
                    GameEngine.getInst().exit();
                    break;
            }
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameEngine.sClick.play();
            itExit.setIsOver(true);
            GameEngine.getInst().exit();
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
            gotoMainGame();

            //sound
            GameEngine.sClick.play();
        }

        if (itAbout.contains(e.getX(), e.getY())) {
            itAbout.setIsClick(false);
            isAboutState = !isAboutState;
            
            //sound
            GameEngine.sClick.play();
        }

        if (itExit.contains(e.getX(), e.getY())) {
            itExit.setIsClick(false);
            GameEngine.getInst().exit();

            //sound
            GameEngine.sClick.play();
        }
    }

    public void load() {
        ttBgMenu = ResourceManager.getInst().getTexture("data/menu/bg_menu.png");
        ttTop = ResourceManager.getInst().getTexture("data/menu/top.png");
        ttBottom = ResourceManager.getInst().getTexture("data/menu/bottom.png");
        ttAbout = ResourceManager.getInst().getTexture("data/menu/bg_about.png");

        itPlay = new MenuItem(null,
                ResourceManager.getInst().getTexture("data/menu/btn_play_press.png"));
        itAbout = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_press.png"));
        itExit = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_press.png"));

        itPlay.SetPosition(pPlay);
        itAbout.SetPosition(pAbout);
        itExit.SetPosition(pExit);
        
        itPlay.setIsOver(true);
        
        Writer writer = new Writer("data/font/Motorwerk_80.fnt");
    }

    public void unload() {
    }

    private void gotoMainGame() {
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

        GameEngine.getInst().attach(new LoadingView(new MainGameView()));
        GameEngine.getInst().detach(this);
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
        if (isAboutState == false) {
            GameEngine.writer.Render("ABOUT", pAbout.x + 36, pAbout.y + 12, textScale, textScale, 1.0f, 1.0f, 1.0f);
        } else {
            GameEngine.writer.Render("MENU", pAbout.x + 40, pAbout.y + 12, textScale, textScale, 1.0f, 1.0f, 1.0f);
        }

        GameEngine.writer.Render("PLAY", pPlay.x + 54, pPlay.y + 42, 1.2f, 1.2f, 1.0f, 1.0f, 1.0f);
        GameEngine.writer.Render("EXIT", pExit.x + 68, pExit.y + 12, textScale, textScale, 1.0f, 1.0f, 1.0f);
    }
}
