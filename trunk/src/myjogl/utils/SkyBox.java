/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class SkyBox {

    private float m_size;
    //texture
    private Texture ttTop;
    private Texture ttBottom;
    private Texture ttFront;
    private Texture ttBack;
    private Texture ttLeft;
    private Texture ttRight;

    public SkyBox() {
    }

    public void Initialize(float size) {
        this.m_size = size;
    }

    public void LoadTextures(Texture top, Texture bottom, Texture front, Texture back, Texture left, Texture right) {
        ttTop = top;
        ttBottom = bottom;
        ttFront = front;
        ttBack = back;
        ttLeft = left;
        ttRight = right;
    }

    public void LoadTextures(String top, String bottom, String front, String back, String left, String right) {
        this.ttTop = ResourceManager.getInst().getTexture(top, false, GL.GL_CLAMP_TO_EDGE);
        this.ttBottom = ResourceManager.getInst().getTexture(bottom, false, GL.GL_CLAMP_TO_EDGE);
        this.ttFront = ResourceManager.getInst().getTexture(front, false, GL.GL_CLAMP_TO_EDGE);
        this.ttBack = ResourceManager.getInst().getTexture(back, false, GL.GL_CLAMP_TO_EDGE);
        this.ttLeft = ResourceManager.getInst().getTexture(left, false, GL.GL_CLAMP_TO_EDGE);
        this.ttRight = ResourceManager.getInst().getTexture(right, false, GL.GL_CLAMP_TO_EDGE);
    }

    public void Render(float cameraX, float cameraY, float cameraZ) {
        GL gl = Global.drawable.getGL();

        gl.glPushMatrix();

        // Move the skybox so that it's centered on the camera.
        gl.glTranslatef(cameraX, cameraY, cameraZ);

        gl.glPushAttrib(GL.GL_FOG_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_LIGHTING_BIT);
        gl.glDisable(GL.GL_DEPTH_TEST);

        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);

        Global.getGL().glColor3f(1, 1, 1);
        
        //Top
        ttTop.enable();
        ttTop.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, m_size, -m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, m_size, -m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, m_size);
        gl.glEnd();
        ttTop.disable();
//		Bottom
        ttBottom.enable();
        ttBottom.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, -m_size, -m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, -m_size, -m_size);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, m_size);
        gl.glEnd();
        ttBottom.disable();
//		Front
        ttFront.enable();
        ttFront.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, -m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, -m_size);
        gl.glEnd();
        ttFront.disable();
//		Back
        ttBack.enable();
        ttBack.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, m_size);
        gl.glEnd();
        ttBack.disable();
//		Left
        ttLeft.enable();
        ttLeft.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, -m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, m_size);
        gl.glEnd();
        ttLeft.disable();
        
//		Right
        ttRight.enable();
        ttRight.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, -m_size);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, -m_size);
        gl.glEnd();
        ttRight.disable();
        
        Global.getGL().glColor3f(1, 1, 1);

        gl.glPopAttrib();
        gl.glEndList();
        gl.glPopMatrix();
    }

    public void Release() {
        ttTop.dispose();
        ttBottom.dispose();
        ttFront.dispose();
        ttBack.dispose();
        ttLeft.dispose();
        ttRight.dispose();
    }

    public enum POSITION {
        SKY_UP,
        SKY_DOWN,
        SKY_FRONT,
        SKY_BACK,
        SKY_LEFT,
        SKY_RIGHT
    };
};
