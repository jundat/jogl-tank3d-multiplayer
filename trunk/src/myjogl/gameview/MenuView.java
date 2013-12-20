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

    Point pExit = new Point(712, 640 - 590);
    Point pAbout = new Point(81, 640 - 590);
    Point pPlay = new Point(382, 640 - 611);
    Point pTop = new Point(0, 640 - 223);
    Point pBottom = new Point(0, 0);
    Point pAboutBg = new Point(42, 640-514);
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

    //
    public MenuView() {
        System.out.println("Go to menu view-------------------------------------");
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gotoMainGame();
        }
    }

    public void keyReleased(KeyEvent e) {
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
            GameEngine.writer.Render("ABOUT", pAbout.x + 16, pAbout.y + 12, 0.85f, 0.85f, 1.0f, 1.0f, 1.0f);
        } else {
            GameEngine.writer.Render("MENU", pAbout.x + 25, pAbout.y + 12, 0.85f, 0.85f, 1.0f, 1.0f, 1.0f);
        }

        GameEngine.writer.Render("PLAY", pPlay.x + 24, pPlay.y + 42, 1.2f, 1.2f, 1.0f, 1.0f, 1.0f);
        GameEngine.writer.Render("EXIT", pExit.x + 46, pExit.y + 12, 0.85f, 0.85f, 1.0f, 1.0f, 1.0f);
    }
}
