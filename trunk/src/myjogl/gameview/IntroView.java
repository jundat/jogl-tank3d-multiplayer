/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GL;
import myjogl.*;
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
public class IntroView implements GameView {

    float scaleWind = Global.wndWidth / 800.0f;
    float scaleLogo = 0.9965f;
    Sound sound;
    float x = 0, y = 0;
    float w, h;
    float xl, yl, wl, hl;
    long startLight = 3810000;
    long endLight1 = 7059000;
    long endLight2 = 7600000;
    Texture ttLogo;
    Texture ttLight;
    //
    Rectangle rectContinue = new Rectangle(700, 0, 324, 30);

    public IntroView() {
        System.out.println("Go to intro view------------------------------------");
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Sound Frame: " + sound.clip.getFramePosition());
            System.out.println("Sound Microsecond: " + sound.clip.getMicrosecondPosition());
        }
    }

    public void keyReleased(KeyEvent e) {
        if (sound.clip.getMicrosecondPosition() >= endLight1) {
            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
        if (rectContinue.contains(e.getX(), e.getY())) {
            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void load() {
        ttLogo = ResourceManager.getInst().getTexture("data/intro/logo.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ttLight = ResourceManager.getInst().getTexture("data/intro/light.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);

        wl = ttLight.getWidth();
        hl = 2 * ttLight.getHeight();
        xl = (Global.wndWidth - wl) / 2;
        yl = (Global.wndHeight - hl) / 2;

        w = 1.5f * Global.wndWidth;
        h = ttLogo.getHeight() * w / ttLogo.getWidth();
        x = (Global.wndWidth - w) / 2;
        y = (Global.wndHeight - h) / 2;

        sound = ResourceManager.getInst().getSound("sound/intro.wav", false);
        sound.play();
    }

    public void unload() {
        ResourceManager.getInst().deleteTexture("data/intro/light.png");
        ResourceManager.getInst().deleteTexture("data/intro/logo.png");
        //ResourceManager.getInst().deleteSound(sound); //gây l?i
    }

    public void update(long elapsedTime) {
        //light
        if (sound.clip.getMicrosecondPosition() >= startLight && sound.clip.getMicrosecondPosition() <= endLight1) {
            wl += 30.0f * scaleWind;
            hl += 2.0f * scaleWind;
            xl = (Global.wndWidth - wl) / 2;
            yl = (Global.wndHeight - hl) / 2;
        } else if (sound.clip.getMicrosecondPosition() > endLight1 && sound.clip.getMicrosecondPosition() <= endLight2) {
            wl -= 330.0f * scaleWind;
            hl -= 20.0f * scaleWind;
            xl = (Global.wndWidth - wl) / 2;
            yl = (Global.wndHeight - hl) / 2;
        }

        //logo
        if (w > ttLogo.getWidth() * scaleWind && h > ttLogo.getHeight() * scaleWind) {
            float tempw = w;
            float temph = h;
            
            w *= scaleLogo;
            h *= scaleLogo;
            
            x += (tempw - w) / 2;
            y += (temph - h) / 2;
        }
    }

    public void display() {
        //&& s.clip.getMicrosecondPosition() <= endLight
        if (sound.clip.getMicrosecondPosition() >= startLight && sound.clip.getMicrosecondPosition() <= endLight2) {
            Renderer.Render(ttLight, xl, yl, wl, hl);
        }

        Renderer.Render(ttLogo, x, y, w, h);

        if (sound.clip.getMicrosecondPosition() >= endLight1) {
            GameEngine.writer.Render("click here to continue...", 700, 10, 0.35f, 0.35f, 0.6f, 0.5f, 0.5f);
        }
    }
}
